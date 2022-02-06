package org.legion.util;

import com.sun.mail.smtp.SMTPTransport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.legion.ui_beans.ParentBean;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Getter
@Setter
public class Utilities extends ParentBean implements Serializable {

    private String SMTP_SERVER = "smtp.gmail.com";
    private String USERNAME = "shini.ops@gmail.com";
    private String PASSWORD = "Bane@4994";

    private String EMAIL_FROM = "shini.ops@gmail.com";

    public Utilities() {
    }

    public void sendMailWithAttachments(String to, String subject, String body, List<String> files, String fileName) throws Exception {

        Properties prop = System.getProperties();
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop, null);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(EMAIL_FROM));

        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to, false));

        Multipart multipart = new MimeMultipart();
        msg.setSubject(subject, "UTF-8");
        for (String filePath : files) {
            BodyPart mailAttachment = new MimeBodyPart();
            DataSource s = new FileDataSource(filePath);
            mailAttachment.setDataHandler(new DataHandler(s));
            mailAttachment.setFileName(fileName);
            mailAttachment.setDescription(fileName);
            multipart.addBodyPart(mailAttachment);
        }
        BodyPart mailBody = new MimeBodyPart();
        mailBody.setContent(body, "text/html;Charset=UTF-8");
        multipart.addBodyPart(mailBody);
        msg.setContent(multipart);
        SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

        try {
            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();
        } catch (Exception e) {
            //try again in 2 seconds
            Thread.sleep(2000);
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);
            // send
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Response: " + t.getLastServerResponse());
            t.close();
        }
    }

    public void sendMail(String to, String message, String subject) {
        try {
            Properties prop = System.getProperties();
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true"); //TLS

            Session session = Session.getInstance(prop, null);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to, false));

            Multipart multipart = new MimeMultipart();
            msg.setSubject(subject, "UTF-8");
            BodyPart mailBody = new MimeBodyPart();
            mailBody.setContent(message, "text/html;Charset=UTF-8");
            multipart.addBodyPart(mailBody);
            msg.setContent(multipart);
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            try {
                // connect
                t.connect(SMTP_SERVER, USERNAME, PASSWORD);

                // send
                t.sendMessage(msg, msg.getAllRecipients());

                System.out.println("Response: " + t.getLastServerResponse());

                t.close();
            } catch (Exception e) {
                //try again in 2 seconds
                Thread.sleep(2000);
                t.connect(SMTP_SERVER, USERNAME, PASSWORD);
                // send
                t.sendMessage(msg, msg.getAllRecipients());
                System.out.println("Response: " + t.getLastServerResponse());
                t.close();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }


    public InputStream loadResource(String resourceName) {
        return this.getClass().getClassLoader().getResourceAsStream(resourceName);
    }

    public String generateRowID() {
        String val = "";
        val = "" + System.nanoTime();
        return val;
    }

    public String getUploadPath() throws Exception {
        Properties props = new Properties();
        props.load(loadResource("config.properties"));
        return props.getProperty("uploadPath");
    }

    public String getUploadVirtualDirectory() throws Exception {
        Properties props = new Properties();
        props.load(loadResource("config.properties"));
        return props.getProperty("uploadVirtualPath");
    }

    public String getUploadVirtualDirectory(String key) throws Exception {
        Properties props = new Properties();
        props.load(loadResource("config.properties"));
        return props.getProperty(key);
    }

    public String getConfigProperty(String key) throws Exception {
        Properties props = new Properties();
        props.load(loadResource("config.properties"));
        return props.getProperty(key);
    }

    public String getProperty(String key, String prpertyName) throws Exception {
        Properties props = new Properties();
        props.load(loadResource(prpertyName));
        return props.getProperty(key);
    }

    public String generateToken() {
        String token;
        token = UUID.randomUUID().toString();
        return token;
    }


    public String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public String formatDouble(Double value) {
        if (value == null) {
            return "";
        }
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        return myFormat.format(value);
    }

    public String formatDouble(BigDecimal value) {
        if (value == null) {
            return "";
        }
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        return myFormat.format(value.doubleValue());
    }

    public String formatDoubleOneDecimals(Double value) {
        if (value == null) {
            return "";
        }
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        myFormat.setMaximumFractionDigits(1);
        return myFormat.format(value);
    }

    public static Double formatDoubleGlobalWithParse(Double value) {
        try {
            if (value == null) {
                return 0.0;
            }
            DecimalFormat df = new DecimalFormat("#.###");
            return Double.parseDouble(df.format(value));

        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public Double formatDoubleTwoDecimals(Double value) {
        try {
            if (value == null) {
                return 0.0;
            }
            DecimalFormat df = new DecimalFormat("#.##");
            return Double.parseDouble(df.format(value));

        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public Double formatDoubleFourDecimals(Double value) {
        try {
            if (value == null) {
                return 0.0;
            }
            DecimalFormat df = new DecimalFormat("#.####");
            return Double.parseDouble(df.format(value));

        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public String formatPercentage(Double value) {
        NumberFormat defaultFormat = NumberFormat.getPercentInstance();
        defaultFormat.setMinimumFractionDigits(1);
        return defaultFormat.format(value);
    }

    public static String formatPercentageGlobal(Double value) {
        NumberFormat defaultFormat = NumberFormat.getPercentInstance();
        defaultFormat.setMinimumFractionDigits(1);
        return defaultFormat.format(value);
    }

}
