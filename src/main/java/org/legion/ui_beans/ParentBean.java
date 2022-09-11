package org.legion.ui_beans;

import lombok.extern.slf4j.Slf4j;
import org.legion.model.entity.UserAccount;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Slf4j
public class ParentBean implements Serializable {



    public String getCurrentView() {
        ExternalContext cxt = FacesContext.getCurrentInstance().getExternalContext();
        String url = cxt.getRequestServletPath().replaceAll("/views", "");
        return url;
    }

    public String getServerPath() {
        ExternalContext cxt = FacesContext.getCurrentInstance().getExternalContext();
        String url = cxt.getRequestScheme() + "://" + cxt.getRequestServerName() + ":" +
                cxt.getRequestServerPort();
        return url;
    }

    public String getContextPath() {
        ExternalContext cxt = FacesContext.getCurrentInstance().getExternalContext();
        String url = cxt.getRequestScheme() + "://" + cxt.getRequestServerName() + ":" +
                cxt.getRequestServerPort() + cxt.getRequestContextPath();
        return url;
    }

    public UserAccount getLoggedInUser() {
        return (UserAccount) returnValueFromSession("loggedInUser");
    }

    public String user() {
        if (returnValueFromSession("loggedInUser") == null)
            return null;
        return ((UserAccount) returnValueFromSession("loggedInUser")).getRowId();
    }

    public Object returnValueFromSession(String attributeName) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(attributeName);
    }

    public String getDayName(int day) {
        switch (day) {
            case 0:
                return "Saturday";
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }
        return "";
    }


    public void putValueToSession(String attributeName, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(attributeName, value);
    }

    public void executeJS(String script) {
        PrimeFaces.current().executeScript(script);
    }

    public void showDialog(String dialogName) {
        PrimeFaces.current().executeScript("PF('" + dialogName + "').show()");
    }

    public void hideDialog(String dialogName) {
        PrimeFaces.current().executeScript("PF('" + dialogName + "').hide()");
    }

    public void showLoading() {
        PrimeFaces.current().ajax().update("@(.loadingTextC)");
        PrimeFaces.current().executeScript("showLoading()");
    }

    public void hideLoading() {
        PrimeFaces.current().executeScript("hideLoading()");
    }

    public void showInfoMessage(String title, String body) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, title, body);
        FacesContext.getCurrentInstance().addMessage("mainNotificationFor", fm);
    }

    public void showWarningMessage(String title, String body) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, title, body);
        FacesContext.getCurrentInstance().addMessage("mainNotificationFor", fm);
    }

    public void showTargetMessage(String title, String body) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, title, body);
        FacesContext.getCurrentInstance().addMessage("mainNotificationFor", fm);
    }

    public void showErrorMessage(String title, String body) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, body);
        FacesContext.getCurrentInstance().addMessage("mainNotificationFor", fm);
    }

    public void showInfoMessage(String title) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, title, "");
        FacesContext.getCurrentInstance().addMessage("mainNotificationFor", fm);
    }

    public void showWarningMessage(String title) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, title, "");
        FacesContext.getCurrentInstance().addMessage("mainNotificationFor", fm);
    }

    public void showTargetMessage(String title) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, title, "");
        FacesContext.getCurrentInstance().addMessage("mainNotificationFor", fm);
    }

    public void showErrorMessage(String title) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, "");
        FacesContext.getCurrentInstance().addMessage("mainNotificationFor", fm);
        hideLoading();
    }

    public void showFatalMessage(Exception exception) {
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "Oops, Something went wrong!", exception.getMessage());
        PrimeFaces.current().dialog().showMessageDynamic(fm);
        log.error(exception.getMessage(), exception);
        exception.printStackTrace();
    }

    public String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");
        return sdf.format(new Date());
    }

    public String getCurrentDateTimeMinutes() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy / HH:mm");
        return sdf.format(new Date());
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }

    public String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public String formatDateSQL_Convention(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public String getCurrentDateForMySQLQuery() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public String formatDateForMySQLQuery(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public String formatDateTime(long time) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy / HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    public String formatDate(long time) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(cal.getTime());
    }

    public String formatDouble(Double value) {
        if (value == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("0.00");
//        NumberFormat myFormat = NumberFormat.getInstance();
//        myFormat.setGroupingUsed(true);
        return df.format(value);
    }

    public String formatBigDecimal(BigDecimal value) {
        if (value == null) {
            return "";
        }
        NumberFormat myFormat = NumberFormat.getInstance();
        myFormat.setGroupingUsed(true);
        return myFormat.format(value);
    }

    public BigDecimal formatBigDecimal_3DecimalPlaces(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }

        DecimalFormat df = new DecimalFormat("#.###");
        return BigDecimal.valueOf(Double.parseDouble(df.format(value)));
    }

    public String generateRandomColor(double alpha) {
        String randomRed = (new Random().nextInt(230) + 1) + "";
        String randomGreen = (new Random().nextInt(230) + 1) + "";
        String randomBlue = (new Random().nextInt(230) + 1) + "";

        String color = "rgba(" + randomRed + "," + randomGreen + "," + randomBlue + "," + alpha + ")";
        return color;
    }


    public LocalDate toLocalDate(Date time) {
        Calendar cal = Calendar.getInstance();
        if (time != null) {
            cal.setTime(time);
        }
        LocalDate localDate = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
        return localDate;
    }

    public LocalDateTime toLocalDateTime(Date time) {
        Calendar cal = Calendar.getInstance();
        if (time != null) {
            cal.setTime(time);
        }
        LocalDateTime localDate = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
        return localDate;
    }

    public LocalDateTime toLocalDateTime(Calendar cal) {
        if (cal == null) {
            cal = Calendar.getInstance();
        }
        LocalDateTime localDate = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId());
        return localDate;
    }

    public Long now() {
        return System.currentTimeMillis();
    }

    public Timestamp nowTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public byte[] scale(byte[] fileData, int width, int height) {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
            BufferedImage img = ImageIO.read(in);
            if (height == 0) {
                height = (width * img.getHeight()) / img.getWidth();
            }
            if (width == 0) {
                width = (height * img.getWidth()) / img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            return buffer.toByteArray();
        } catch (Exception e) {
            showFatalMessage(e);
        }

        return new byte[]{};
    }

    public void throwException() throws Exception {
        throw new Exception("Exceptionized!");
    }

    public void generateExcelFile(){}
    public void throwException(String message) throws Exception {
        throw new Exception(message);
    }
}
