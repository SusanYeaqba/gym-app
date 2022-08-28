package org.legion.util;


import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Generator {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Generator.class);
    private static String MSSQL_INSTANCE = "";


    private static String fix_column_name(String old_col_name) {
        String column_name = old_col_name.toLowerCase();
        String[] column_name_parts = column_name.split("_");
        column_name = "";
        for (String str : column_name_parts) {
            column_name += str.substring(0, 1).toUpperCase();
            column_name += str.substring(1);
        }
        return column_name;
    }

    public static void main(String[] args) {
        try (Connection con = MainDataSource.getConnection()) {
            ArrayList<String> tableNames = new ArrayList<>();
            ArrayList<String> objectNames = new ArrayList<>();

            DatabaseMetaData md = con.getMetaData();
            ResultSet rss = md.getTables(null, "dbo", "%", null);
            while (rss.next()) {
                String ObjectName = "";
                String tmpObjectName = rss.getString(3);
                if (tmpObjectName.contains("CX")) {
                    continue;
                }
                String[] objNameBefore = tmpObjectName.split("_");
                for (int i = 0; i < objNameBefore.length; i++) {
                    ObjectName += Character.toUpperCase(objNameBefore[i].charAt(0)) + objNameBefore[i].substring(1);
                }
                tableNames.add(rss.getString(3));
                objectNames.add(ObjectName);

            }
            File projectFolder = new File("D:\\Work\\Legion\\gym-app\\src\\main\\java\\org\\legion\\model");
            if (!projectFolder.isDirectory()) {
                projectFolder.mkdir();
            }
            String BASEDAOS_FILE = projectFolder.getAbsolutePath() + "/baseDAO";
            String DAOS_FILE = projectFolder.getAbsolutePath() + "/dao";
            String BASE_Entities_FILE = projectFolder.getAbsolutePath() + "/baseEntity";
            String Entities_FILE = projectFolder.getAbsolutePath() + "/entity";

            //generating Base entity starts
            for (int j = 0; j < tableNames.size(); j++) {
                logger.info("Found -> " + tableNames.get(j));
                File file = new File(BASE_Entities_FILE);
                if (!file.isDirectory()) {
                    file.mkdir();
                }
                file = new File(BASE_Entities_FILE + "/" + objectNames.get(j) + "Base.java");
                String query = "select top 1 * from " + MSSQL_INSTANCE + tableNames.get(j) + "";//table name
                String ClassVariables = "";
                String ClassImports = "package org.legion.model.baseEntity;\n"
                        + "import java.util.ArrayList;\n"
                        + "import java.util.Date;\n"
                        + "import java.sql.Timestamp;\n" +
                        "import org.slf4j.Logger;\n" +
                        "import org.slf4j.LoggerFactory;\n"
                        + "import java.math.*;\n"
                        + "import java.util.HashSet;\n"
                        + "import java.util.List;\n"
                        + "import java.util.Set;\n"
                        + "\n"
                        + "public class " + objectNames.get(j) + "Base implements java.io.Serializable {\n " +
                        "private static final Logger logger = LoggerFactory.getLogger(" + objectNames.get(j) + "Base.class);\n";

                PreparedStatement stmt = null;
                stmt = con.prepareStatement(query);
                ResultSet rs = null;
                rs = stmt.executeQuery();
                ResultSetMetaData rsmd = null;
                rsmd = rs.getMetaData();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    // ENTITY BASE FIELD DEFINITION
                    String column_name = fix_column_name(rsmd.getColumnName(i + 1).toLowerCase());

                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARCHAR") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NVARCHAR")) {
                        ClassVariables += "private String " + column_name + "; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BLOB")) {
                        ClassVariables += "private byte[] " + column_name + "; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATE")) {
                        ClassVariables += "private Date " + column_name + "; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATETIME")) {
                        ClassVariables += "private Timestamp " + column_name + "; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("INT")) {
                        ClassVariables += "private Integer " + column_name + " = 0; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIGINT")) {
                        ClassVariables += "private Long " + column_name + " = new Long(0); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIT") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("SMALLINT")) {
                        ClassVariables += "private boolean " + column_name + "; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NUMERIC") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DECIMAL")) {
                        ClassVariables += "private BigDecimal " + column_name + " = BigDecimal.ZERO; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("LONGBLOB")) {
                        ClassVariables += "private byte[] " + column_name + "; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("MEDIUMBLOB")) {
                        ClassVariables += "private byte[] " + column_name + "; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARBINARY")) {
                        ClassVariables += "private byte[] " + column_name + "; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("FLOAT")) {
                        ClassVariables += "private float " + column_name + " = 0.0f; \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DOUBLE")) {
                        ClassVariables += "private Double " + column_name + " = 0.0; \n";
                    }
                }
                ClassVariables += "\n"
                        + "    public " + objectNames.get(j) + "Base() {\n"
                        + "    }\n";

                String gettersAndSetters = "";

                for (int i = 0; i < rsmd.getColumnCount(); i++) {

                    // ENTITY BASE FIELD GETTER AND SETTER

                    String column_name = fix_column_name(rsmd.getColumnName(i + 1));

                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BLOB")) {
                        gettersAndSetters += " public byte[] get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n\n\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(byte[] " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARCHAR") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NVARCHAR")) {
                        gettersAndSetters += " public String get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n\n\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(String " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATE")) {
                        gettersAndSetters += " public Date get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n\n\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(Date " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATETIME")) {
                        gettersAndSetters += " public Timestamp get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n\n\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(Timestamp " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("INT")) {

                        gettersAndSetters += " public Integer get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n\n\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(Integer " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIGINT")) {

                        gettersAndSetters += " public Long get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n\n\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(Long " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIT") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("SMALLINT")) {

                        gettersAndSetters += " public boolean get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n\n\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(boolean " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NUMERIC") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DECIMAL")) {
                        gettersAndSetters += " public BigDecimal get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(BigDecimal " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("LONGBLOB")) {
                        gettersAndSetters += " public byte[] get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(byte[] " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARBINARY")) {
                        gettersAndSetters += " public byte[] get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(byte[] " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("MEDIUMBLOB")) {
                        gettersAndSetters += " public byte[] get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(byte[] " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("FLOAT")) {
                        gettersAndSetters += " public float get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(float " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DOUBLE")) {
                        gettersAndSetters += " public Double get" + column_name + "() {\n"
                                + "return this." + column_name + ";}\n"
                                + "\n\n"
                                + "  public void set" + column_name + "(Double " + column_name + ") {\n\n\n"
                                + "this." + column_name + " = " + column_name + ";}\n\n\n";
                    }

                }
                gettersAndSetters += " \n\n}";
                String wholeFile = ClassImports + "\n" + "\n//-----------------Variables ---------------\n" + ClassVariables
                        + "\n//--------- Getters and setters --------\n" + gettersAndSetters;
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(wholeFile);

                writer.close();
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();

            }
            //generating Base entity ends

            //generating entity starts
            for (int j = 0; j < tableNames.size(); j++) {
                File file = new File(Entities_FILE);

                if (!file.isDirectory()) {
                    file.mkdir();
                }
                file = new File(Entities_FILE + "/" + objectNames.get(j) + ".java");
                if (file.exists()) {
                    continue;
                }
                String ClassImports = "package org.legion.model.entity;\n"
                        + "import java.util.ArrayList;\n"
                        + "import java.util.Date;\n"
                        + "import java.util.HashSet;\n"
                        + "import org.legion.model.baseEntity." + objectNames.get(j) + "Base;\n"
                        + "import java.util.List;\n"
                        + "import java.util.Set;\n"
                        + "public class " + objectNames.get(j) + " extends " + objectNames.get(j) + "Base  {\n \n"
                        + ""
                        + "\n}";

                String wholeFile = ClassImports + "\n";
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(wholeFile);
                writer.close();
            }
            //generating entity ends

            //generating Base dao starts
            for (int j = 0; j < tableNames.size(); j++) {
                BufferedWriter writer = null;
                File file = new File(BASEDAOS_FILE);
                if (!file.isDirectory()) {
                    file.mkdir();
                }
                file = new File(BASEDAOS_FILE + "/" + objectNames.get(j) + "DAOBase.java");
                String query = "select top 1 * from " + MSSQL_INSTANCE + tableNames.get(j) + "";//table name
                String ClassImports = "package org.legion.model.baseDAO;\n"
                        + "\n"
                        + "import org.legion.model.entity." + objectNames.get(j) + ";\n"
                        + "import java.sql.Connection;\n"
                        + "import java.sql.Date;\n"
                        + "import org.slf4j.Logger;\n"
                        + "import org.slf4j.LoggerFactory;\n"
                        + "import java.sql.PreparedStatement;\n"
                        + "import java.sql.ResultSet;\n"
                        + "import java.sql.SQLException;\n"
                        + "import java.sql.Statement;\n"
                        + "import java.util.ArrayList;\n"
                        + "import java.math.*;\n"
                        + "import org.legion.util.*;\n"
                        + "import java.util.List;\n"
                        + "import java.io.Serializable;";
                String ClassName = "public class " + objectNames.get(j) + "DAOBase" + " implements Serializable{\n" +
                        "private static final Logger logger = LoggerFactory.getLogger(" + objectNames.get(j) + "DAOBase.class);";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();

                String updateStatement = "  public void updateRecord(" + objectNames.get(j) + " record) {"
                        + " Connection con = null;\n" +
                        " PreparedStatement ps = null;\n" +
                        " try { con = org.legion.util.MainDataSource.getConnection();\n"
                        + " ps = con.prepareStatement(\"UPDATE " + MSSQL_INSTANCE + tableNames.get(j) + " SET";
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    updateStatement += " " + rsmd.getColumnName(i + 1) + " = ?";
                    if (i != rsmd.getColumnCount() - 1) {
                        updateStatement += ",";
                    }
                }
                updateStatement += " WHERE " + rsmd.getColumnName(1) + "=? \");\n";
                String Rs = "";
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    String column_name = fix_column_name(rsmd.getColumnName(i + 1).toLowerCase());

                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BLOB")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getBytes(\"" + rsmd.getColumnName(i + 1) + "\")); \n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARCHAR") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NVARCHAR")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getString(\"" + rsmd.getColumnName(i + 1) + "\")); \n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATE")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getDate(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATETIME")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getTimestamp(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("INT")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getInt(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIGINT")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getLong(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIT") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("SMALLINT")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getBoolean(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NUMERIC") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DECIMAL")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getBigDecimal(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("LONGBLOB")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getBytes(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARBINARY")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getBytes(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("MEDIUMBLOB")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getBytes(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("FLOAT")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getFloat(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DOUBLE")) {
                        Rs += ("entity_record.set" + column_name + "(rs.getDouble(\"" + rsmd.getColumnName(i + 1) + "\"));\n");
                    }
                }
                String getRecord = " public " + objectNames.get(j) + " getRecord(String rowID) throws Exception {\n" +
                        "" + objectNames.get(j) + " entity_record  = null;\n"
                        + "        Connection con =null;\n" +
                        "          PreparedStatement stmt = null;\n" +
                        "          ResultSet rs = null;\n" +
                        "          try { con = org.legion.util.MainDataSource.getConnection();\n"
                        + "        String query = \"select * from " + MSSQL_INSTANCE + tableNames.get(j) + " where " + rsmd.getColumnName(1) + " = '\" + rowID + \"'\";\n"
                        + "        stmt = con.prepareStatement(query);\n"
                        + "        rs = stmt.executeQuery();\n"
                        + "        while (rs.next()) {\n"
                        + "entity_record = new " + objectNames.get(j) + "();\n"
                        + Rs
                        + "        }\n" +
                        "       }catch(Exception e){\n" +
                        "        logger.error(\"Error\", e); throw e;" +
                        "       }\n" +
                        "       finally{\n" +
                        "       try{if(rs != null )rs.close();\n"
                        + "     if(stmt != null )stmt.close();\n" +
                        "     if(con != null ) con.close();}\ncatch(SQLException sqlex){logger.error(\"SQL Error\", sqlex); throw sqlex;}" +
                        "       }\n"
                        + "            \n"
                        + "            return entity_record;\n}";
                String ListWithMyQuery = "public List<" + objectNames.get(j) + "> getList(String myQuery) throws Exception {\n";
                ListWithMyQuery +=
                        "Connection con = null;\n" +
                                "PreparedStatement stmt = null;\n" +
                                "ResultSet rs = null;\n" +
                                "List<" + objectNames.get(j) + "> listOfRecords = new ArrayList<>();\n"
                                + "        try {  \n" +
                                "           con = org.legion.util.MainDataSource.getConnection();\n"
                                + "        String query = \"select * from " + MSSQL_INSTANCE + tableNames.get(j) + " where deleted_by is null and \" + myQuery;\n"
                                + "        logger.info(query);\n"
                                + "        stmt = con.prepareStatement(query);\n"
                                + "        rs = stmt.executeQuery();\n"
                                + "        while (rs.next()) {\n";
                ListWithMyQuery += objectNames.get(j) + " entity_record = new " + objectNames.get(j) + "();\n"
                        + Rs
                        + "        listOfRecords.add(entity_record);\n }\n"
                        + "   }catch(Exception e){logger.error(\"Error\", e); throw e;}\n"
                        + "   finally{\n try{"
                        + "       if(rs != null )rs.close();\n"
                        + "        if(stmt != null )stmt.close();\n"
                        + "        if(con != null ) con.close();\n} catch(SQLException sqlex){logger.error(\"SQL Error\", sqlex); throw sqlex;}"
                        + "        }\n"
                        + "        return listOfRecords;\n}\n\n\n }";
                String ListWithoutMyQuery = "public List<" + objectNames.get(j) + "> getList() {\n";
                ListWithoutMyQuery +=
                        "Connection con = null;\n" +
                                "PreparedStatement stmt = null;\n" +
                                "ResultSet rs = null;\n" +
                                "List<" + objectNames.get(j) + "> listOfRecords = new ArrayList<>();\n"
                                + "         try {con = org.legion.util.MainDataSource.getConnection();\n"
                                + "        String query = \"select * from " + MSSQL_INSTANCE + tableNames.get(j) + " where deleted_by is null\";\n"
                                + "        logger.info(query);\n"
                                + "         stmt = con.prepareStatement(query);\n"
                                + "         rs = stmt.executeQuery();\n"
                                + "        while (rs.next()) {\n";
                ListWithoutMyQuery += objectNames.get(j) + " entity_record = new " + objectNames.get(j) + "();\n"
                        + Rs
                        + "        listOfRecords.add(entity_record);\n }\n"
                        + "   }catch(Exception e){logger.error(\"Error\", e);}\n"
                        + "   finally{\n try{"
                        + "       if(rs != null )rs.close();\n"
                        + "        if(stmt != null )stmt.close();\n"
                        + "        if(con != null ) con.close();\n} catch(SQLException sqlex){logger.error(\"SQL Error\", sqlex);}"
                        + "        }\n"
                        + "        return listOfRecords;\n}";
                String insert = "  public void saveRecord(" + objectNames.get(j) + " record)  {"
                        + " \n if(record.getRowId() != null && !record.getRowId().isEmpty()){updateRecord(record);return;}"
                        + "record.set" + fix_column_name(rsmd.getColumnName(1)) + "(new Utilities().generateRowID());\n"
                        + "Connection con = null;\n" +
                        "  PreparedStatement ps = null;\n" +
                        " try{ con = org.legion.util.MainDataSource.getConnection(); \n" +
                        " ps = con.prepareStatement(\"INSERT INTO " + MSSQL_INSTANCE + tableNames.get(j) + " VALUES";
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    if (i == 0) {
                        insert += "(";
                    }

                    insert += "?";
                    if (i != rsmd.getColumnCount() - 1) {
                        insert += ",";
                    }
                }
                insert += ")\");\n";
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    String column_name = fix_column_name(rsmd.getColumnName(i + 1));
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BLOB")) {
                        insert += "ps.setBytes(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARCHAR") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NVARCHAR")) {
                        insert += "ps.setString(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATE")) {
                        insert += ""
                                + "if(record." + "get" + column_name + "() !=null){"
                                + "ps.setDate(" + (i + 1) + ", new java.sql.Date(record." + "get" + column_name + "().getTime()));}"
                                + " \n else{"
                                + "ps.setDate(" + (i + 1) + ",new java.sql.Date(new java.util.Date().getTime()));"
                                + "}\n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATETIME")) {
                        insert += "ps.setTimestamp(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("INT")) {
                        insert += "ps.setInt(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIGINT")) {
                        insert += "ps.setLong(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIT") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("SMALLINT")) {
                        insert += "ps.setBoolean(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NUMERIC") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DECIMAL")) {
                        insert += "ps.setBigDecimal(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("LONGBLOB")) {
                        insert += "ps.setBytes(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARBINARY")) {
                        insert += "ps.setBytes(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("MEDIUMBLOB")) {
                        insert += "ps.setBytes(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("FLOAT")) {
                        insert += "ps.setFloat(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DOUBLE")) {
                        insert += "ps.setDouble(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                    }
                }
                int last = 0;
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    String column_name = fix_column_name(rsmd.getColumnName(i + 1));
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BLOB")) {
                        updateStatement += "ps.setBytes(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARCHAR") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NVARCHAR")) {
                        updateStatement += "ps.setString(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }

                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATE")) {
                        updateStatement += ""
                                + "if (record." + "get" + column_name + "() != null) {"
                                + "ps.setDate(" + (i + 1) + ",new java.sql.Date(record." + "get" + column_name + "().getTime()));}"
                                + "\n"
                                + "else{"
                                + "ps.setDate(" + (i + 1) + ",new java.sql.Date(new java.util.Date().getTime()));"
                                + "}"
                                + " \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DATETIME")) {
                        updateStatement += "ps.setTimestamp(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("INT")) {
                        updateStatement += "ps.setInt(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIGINT")) {
                        updateStatement += "ps.setLong(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("BIT") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("SMALLINT")) {
                        updateStatement += "ps.setBoolean(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("NUMERIC") || rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DECIMAL")) {
                        updateStatement += "ps.setBigDecimal(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("LONGBLOB")) {
                        updateStatement += "ps.setBytes(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("VARBINARY")) {
                        updateStatement += "ps.setBytes(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("MEDIUMBLOB")) {
                        updateStatement += "ps.setBytes(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("FLOAT")) {
                        updateStatement += "ps.setFloat(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                    if (rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("DOUBLE")) {
                        updateStatement += "ps.setDouble(" + (i + 1) + ",record." + "get" + column_name + "()); \n";
                        last = i + 1;
                    }
                }
                updateStatement += "ps.setString(" + (last + 1) + ",record." + "get" + fix_column_name(rsmd.getColumnName(1)) + "()); \n" +
                        "   int i = ps.executeUpdate();\n" +
                        " } catch(Exception e){\n" +
                        "   logger.error(\"Error\", e);}\n" +
                        "   finally{\n" +
                        "       try{\n" +
                        "           if(ps != null ) ps.close();\n" +
                        "           if(con != null ) con.close();\n" +
                        "       }\n" +
                        "       catch(SQLException sqlex){\n" +
                        "           logger.error(\"SQL Error\", sqlex);\n" +
                        "       }\n" +
                        "}"
                        + "            \n}";
                String Delete = " public int deleteRecord(String record_ID)  {\n" +
                        "   Connection con = null;" +
                        "   Statement stmt = null;int i=0;"
                        + "   try{ con = org.legion.util.MainDataSource.getConnection();\n"
                        + "            stmt = con.createStatement();\n"
                        + "\n"
                        + "            i = stmt.executeUpdate(\"DELETE FROM " + MSSQL_INSTANCE + tableNames.get(j) + " WHERE " + rsmd.getColumnName(1) + "='\" + record_ID+\"'\");\n"
                        + "            }catch(Exception e){logger.error(\"Error\", e);}" +
                        "   finally{\n" +
                        "   try { if(stmt != null )stmt.close();\n" +
                        "   if(con != null ) con.close(); } catch(SQLException sqlex){logger.error(\"SQL Error\", sqlex);}\n" +
                        "}"
                        + "            \n"
                        + "    return i;    }";
                insert += "\n" + " int i = ps.executeUpdate();} catch(Exception e){logger.error(\"Error\", e);}\n"
                        + "        finally{ \n" +
                        "               try {" +
                        "                       if(ps != null ) ps.close();\n" +
                        "                       if(con != null ) con.close();\n" +
                        "                   }catch(SQLException sqlex){logger.error(\"SQL Error\", sqlex);}\n" +
                        "       }\n "
                        + "        \n}";

                String wholeFile = ClassImports + "\n" + ClassName + "\n //-----GET RECORD METHOD----------- \n" + getRecord
                        + "\n//-----------------INSERT METHOD ---------------\n" + insert + "\n//--------- DELETE METHOD--------\n" + Delete
                        + "\n//--------- UPDATE METHOD--------\n" + updateStatement
                        + "\n//--------- LIST METHOD ALL RECORDS--------\n" + ListWithoutMyQuery
                        + "\n//--------- LIST METHOD WHERE STATEMENT--------\n" + ListWithMyQuery;
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(wholeFile);
                writer.close();
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();

            } //generating Base dao ends

            //generating dao starts
            for (int j = 0; j < tableNames.size(); j++) {
                File file = new File(DAOS_FILE);

                if (!file.isDirectory()) {
                    file.mkdir();
                }
                file = new File(DAOS_FILE + "/" + objectNames.get(j) + "DAO.java");
                if (file.exists()) {
                    continue;
                }
                String ClassImports = "package org.legion.model.dao;\n"
                        + "import java.util.ArrayList;\n"
                        + "import java.util.Date;\n"
                        + "import java.util.HashSet;\n"
                        + "import org.legion.model.baseDAO." + objectNames.get(j) + "DAOBase;\n"
                        + "import java.util.List;\n"
                        + "import java.util.Set;\n"
                        + "public class " + objectNames.get(j) + "DAO extends " + objectNames.get(j) + "DAOBase  {\n \n"
                        + ""
                        + "\n}";

                String wholeFile = ClassImports + "\n";
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(wholeFile);
                writer.close();
            }
            //generating dao ends
        } catch (Exception ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
