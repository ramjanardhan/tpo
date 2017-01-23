package com.mss.tpo.util;

import com.mss.tpo.ajax.AjaxHandlerAction;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailManager {

    private static final String SMTP_AUTH_USER = "mscvp_alerts@miraclesoft.com";
    private static final String SMTP_AUTH_PWD = "Miracle@123";
    private static final String SMTP_HOST = "smtp.miraclesoft.com";
    private static final String PORT = "587";

    private static class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }

    public static String sendUserIdPwd(String loginId, String email, String password) throws ServiceLocatorException {
        /**
         * The to is used for storing the user mail id to send details.
         */
        String to = email;
        /**
         * The from is used for storing the from address.
         */
        String from = "mscvp_alerts@miraclesoft.com";
        String userName = DataSourceDataProvider.getInstance().getuserNameByUserId(loginId);
        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
        /**
         * The host is used for storing the IP address of mail
         */
        /**
         * The props is instance variabel to <code>Properties</code> class
         */
        Properties props = new Properties();
        /**
         * Here set smtp protocal to props
         */
        props.setProperty("mail.transport.protocol", "smtp");
        //**Here set the address of the host to props */ 
        props.setProperty("mail.host", SMTP_HOST);
        props.put("mail.smtp.starttls.enable", "true");
        /**
         * Here set the authentication for the host *
         */
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", PORT);
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        mailSession.setDebug(true);
        Transport transport;
        try {
            transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Miracle Trading Partner Onboarding Details");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress("skunibilli@miraclesoft.com"));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("rpulleboina@miraclesoft.com"));

            // This HTML mail have to 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            StringBuilder htmlText = new StringBuilder();

            htmlText.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
            htmlText.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
            htmlText.append("<head>");
            htmlText.append("  <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
            htmlText.append("  <title>Your password has been reset successfully&#33;&#33;</title>");
            htmlText.append("  <style type='text/css'>");

            htmlText.append(" body {");
            htmlText.append("  padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   margin:0 !important;");
            htmlText.append("  width: 100% !important;");
            htmlText.append("  -webkit-text-size-adjust: 100% !important;");
            htmlText.append(" -ms-text-size-adjust: 100% !important;;");
            htmlText.append(" -webkit-font-smoothing: antialiased !important;");
            htmlText.append(" }");
            htmlText.append(" .tableContent img {");
            htmlText.append("   border: 0 !important;");
            htmlText.append("  display: block !important;");
            htmlText.append("   outline: none !important;");
            htmlText.append(" }");

            htmlText.append("a{");
            htmlText.append("color:#382F2E;");
            htmlText.append("}");

            htmlText.append("p, h1,h2,ul,ol,li,div{");
            htmlText.append("margin:0;");
            htmlText.append("padding:0;");
            htmlText.append("}");

            htmlText.append("h1,h2{");
            htmlText.append("font-weight: normal;");
            htmlText.append("  background:transparent !important;");
            htmlText.append("border:none !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.big,.contentEditable h1.big{");
            htmlText.append("  font-size: 26px !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.bigger,.contentEditable h1.bigger{");
            htmlText.append("font-size: 37px !important;");
            htmlText.append("}");

            htmlText.append("td,table{");
            htmlText.append("vertical-align: top;");
            htmlText.append("}");

            htmlText.append("td.middle{");
            htmlText.append("vertical-align: middle;");
            htmlText.append("}");

            htmlText.append(" a.link1{");
            htmlText.append("font-size:13px;");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("text-decoration:none;");
            htmlText.append("}");

            htmlText.append("a{");
            htmlText.append("text-decoration: none;");
            htmlText.append("}");

            htmlText.append(".link2{");
            htmlText.append("color:#fc3f3f;");
            htmlText.append("border-top:0px solid #fc3f3f;");
            htmlText.append("border-bottom:0px solid #fc3f3f;");
            htmlText.append("border-left:10px solid #fc3f3f;");
            htmlText.append("border-right:10px solid #fc3f3f;");
            htmlText.append("border-radius:1px;");
            htmlText.append("-moz-border-radius:5px;");
            htmlText.append("-webkit-border-radius:5px;");
            htmlText.append("background:#fc3f3f;");
            htmlText.append("}");

            htmlText.append(".link3{");
            htmlText.append("color:#555555;");
            htmlText.append("border:1px solid #cccccc;");
            htmlText.append("padding:10px 18px;");
            htmlText.append("border-radius:3px;");
            htmlText.append("-moz-border-radius:3px;");
            htmlText.append("-webkit-border-radius:3px;");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".link4{");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("}");

            htmlText.append("h2,h1{");
            htmlText.append("line-height: 20px;");
            htmlText.append("}");

            htmlText.append("p{");
            htmlText.append("font-size: 14px;");
            htmlText.append("line-height: 21px;");
            htmlText.append(" color:#AAAAAA;");
            htmlText.append("}");

            htmlText.append(".contentEditable li{");
            htmlText.append("}");

            htmlText.append(".appart p{");
            htmlText.append("}");

            htmlText.append(".bgItem{");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".bgBody{");
            htmlText.append("background: #0d416b;");
            htmlText.append("}");

            htmlText.append("img {");
            htmlText.append("outline:none;");
            htmlText.append("text-decoration:none;");
            htmlText.append("-ms-interpolation-mode: bicubic;");
            htmlText.append("width: auto;");
            htmlText.append("max-width: 100%;");
            htmlText.append("clear: both;");
            htmlText.append("display: block;");
            htmlText.append("float: none;");
            htmlText.append("}");
            htmlText.append("</style>");

            htmlText.append("<script type='colorScheme' class='swatch active'>");
            htmlText.append("{");
            htmlText.append("'name':'Default',");
            htmlText.append("'bgBody':'ffffff',");
            htmlText.append("'link':'27A1E5',");
            htmlText.append("'color':'AAAAAA',");
            htmlText.append("'bgItem':'ffffff',");
            htmlText.append("'title':'444444'");
            htmlText.append("}");

            htmlText.append("</script>");

            htmlText.append("</head>");
            htmlText.append("<body paddingwidth='0' paddingheight='0' bgcolor='#d1d3d4' style='padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;' offset='0' toppadding='0' leftpadding='0' data-gr-c-s-loaded='true'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='tableContent bgBody' align='center' style='font-family:Helvetica, sans-serif;'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='bgItem' align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='movableContentContainer' align='center'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable'>");
            htmlText.append("<a href='http://www.miraclesoft.com/index.php' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/logo.png' alt='Logo' height='45' data-default='placeholder' data-max-width='200'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;'>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;' width='150'>");
            htmlText.append("<br>");
            htmlText.append("<table width='300' border='0' cellpadding='0' cellspacing='0' align='right' style='text-align: right; font-size: 13px; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='fullCenter'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='55' valign='middle' width='100%' style='font-family: Open Sans; color:#232527;'>");
            htmlText.append("<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'>");
            htmlText.append("Company");
            htmlText.append("</a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com/careers/' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'> Careers </a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='580' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='border: 0px solid #ffffff; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#00aae7; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:px'>");
            htmlText.append("<table width='630' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='15'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: left;'>");
            htmlText.append("<h2 style='font-size: 25px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("<b>User Account Created</b>");
            htmlText.append("</font>");
            htmlText.append("</h2>");
            htmlText.append("<br>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("<p>Hello " + userName + "</p>");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("Welcome to Miracle's Trading Partner On-boarding Portal! Your account has been created with the following credentials. Please login and change your password within 48 hours of this email. ");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");

            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' style='padding: 5px 0 5px 0; border-top: 1px #2368a0; border-bottom: 1px #2368a0; font-size: 14px; line-height: 25px; font-family: Open Sans; color: #232527;' class='padding-copy'>");
            htmlText.append("<b style='font-size: 14px; color: #ef4048;'>");
            htmlText.append("Login Id: " + loginId + "</b>");
            htmlText.append("<br>");
            htmlText.append("<b style='font-size: 14px; color: #ef4048;'>");
            htmlText.append("Password: " + password + "</b>");
            htmlText.append("<br>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr><td align='center' valign='middle' style='color:#FFFFFF; font-family:Helvetica, Arial, sans-serif; font-size:16px; font-weight:bold; letter-spacing:-.5px; line-height:150%; padding-top:5px; padding-right:20px; padding-bottom:5px; padding-left:20px;'>");
            htmlText.append("<a href='http://192.168.1.179:8084/tpo' target='_blank' style='color:#FFFFFF; text-decoration:none;'>Click here to login</a></td></tr></table><br><br>");
            htmlText.append("<tr>");
            htmlText.append("<td style='padding-top: 0px;' align='left' valign='top'>");
            htmlText.append("<table class='textbutton' style='margin: 0;' align='left' border='0' cellpadding='0' cellspacing='0'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' valign='top' style='margin: 0; padding-top: 5px; font-size:14px ; font-weight: normal; color:#000000; font-family: 'Open Sans'; line-height: 180%;  mso-line-height-rule: exactly;'>");

            htmlText.append("<p style='text-align: justify; font-size: 14px;'><font color='#000000' face='trebuchet ms'>");
            htmlText.append("<b>With Thank you,</b><br/>");
            htmlText.append("<b>Miracle TP On-boarding Team,</b><br/>");
            htmlText.append("Miracle Software Systems, Inc.<br/>");
            htmlText.append("<b> Email: </b>");
            htmlText.append("<a href='mailto:mscvp_alerts@miraclesoft.com '>");
            htmlText.append("mscvp_alerts@miraclesoft.com </a>");
            htmlText.append("<br/>");
            htmlText.append("<b>Phone: </b>");
            htmlText.append("(+1)248-232-0224");
            htmlText.append("</p>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='left' style='padding: 5px 0 5px 0; font-size: 14px; line-height: 22px; font-family: Open Sans; color: #ef4048; font-style: italic;' class='padding-copy'>");
            htmlText.append("* Note: Please do not reply to this email as this is an automated notification");
            htmlText.append("</td>");
            htmlText.append("</tr>");

            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='655' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("<td width='660' align='center' style='text-align: center;'>");
            htmlText.append("<table width='660' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<p style='text-align: center; font-size: 14px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append(" ©Copyright 2016 Miracle Software Systems, Inc.<br>");
            htmlText.append("45625 Grand River Avenue<br>");
            htmlText.append("Novi, MI - USA");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("<td width='190' align='center' style='text-align: center;'>");
            htmlText.append("<table width='190' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentFacebookEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.facebook.com/miracle45625' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/fb.png' alt='facebook' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentTwitterEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://twitter.com/team_mss' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/tweet.png' alt='twitter' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://plus.google.com/+Team_MSS/posts' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/googleplus.png' alt='Pinterest' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.linkedin.com/company/miracle-software-systems-inc' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/linkedin.png' alt='Social media' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='40'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");

            htmlText.append("<span class='gr__tooltip'>");
            htmlText.append("<span class='gr__tooltip-content'>");
            htmlText.append("</span>");
            htmlText.append("<i class='gr__tooltip-logo'>");
            htmlText.append("</i>");
            htmlText.append("<span class='gr__triangle'>");
            htmlText.append("</span>");
            htmlText.append("</span>");
            htmlText.append("</body>");
            htmlText.append("</html>");

//            htmlText.append("<html><head><title>Mail From Miracle Suuply Chain Visibility Portal</title>");
//            htmlText.append("</head><body><font color='blue' size='2' face='Arial'>");
//            htmlText.append("<p>Hello " + userName + "</p>");
//
//            htmlText.append("<p><u><b>Your Login Details :</b></u><br><br>");
//            htmlText.append("Login Id : <b>" + loginId + "</b><br>");
//            htmlText.append("Password : <b>" + password + "</b><br><br>");
//            htmlText.append("<br>To change password please login with above mentioned password.<br>");
//            htmlText.append("URL :&nbsp;&nbsp;&nbsp;&nbsp;");
//            htmlText.append("<a href='http://192.168.1.179:8084/tpo'>192.168.1.179:8084/tpo</a><br><br>");
//
//            htmlText.append("<b>Please Note:</b><br>");
//            htmlText.append("To better protect ");
//            htmlText.append("your account, make sure that your password is memorable ");
//            htmlText.append("for you but difficult for others to guess. Never ");
//            htmlText.append("use the same password that you have used in the past,");
//            htmlText.append(" and do not share your password with anyone.<br></br><br>");
//            htmlText.append("<b>Regards,</b><br>");
//            htmlText.append("Miracle Supply Chain Visibility Portal Team,</p></font><br>");
//            htmlText.append("<font color='red', size='2' face='Arial'>*Note:Please do not reply to this e-mail.  It was generated by our System.</font>");
//            htmlText.append("</body></html>");
            messageBodyPart.setContent(htmlText.toString(), "text/html");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            transport.connect();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.BCC));
            transport.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return "success";
    }

    public static void tpoDetails(int partnerId, String loginId, String Email, String partnerName, String protocol, String transferMode, String filepath, String sslFilepath, Map mp, String flag) {
        // SUBSTITUTE YOUR EMAIL ADDRESSES HERE!!!
        /**
         * The to is used for storing the user mail id to send details.
         */
        String to = Email;
        /**
         * The from is used for storing the from address.
         */
        String from = "mscvp_alerts@miraclesoft.com";
        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
        /**
         * The host is used for storing the IP address of mail
         */
        /**
         * The props is instance variabel to <code>Properties</code> class
         */
        Properties props = new Properties();
        /**
         * Here set smtp protocal to props
         */
        props.setProperty("mail.transport.protocol", "smtp");
        //**Here set the address of the host to props */
        props.setProperty("mail.host", SMTP_HOST);
        props.put("mail.smtp.starttls.enable", "true");
        /**
         * Here set the authentication for the host *
         */
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", PORT);
        Authenticator auth = new SMTPAuthenticator();
        // Session mailSession = Session.getDefaultInstance(props, null);
        Session mailSession = Session.getDefaultInstance(props, auth);
        mailSession.setDebug(true);
        Transport transport;
        try {
            Map ptotoMap = mp;
            Collection<String> keys = ptotoMap.keySet();
            Collection<String> vals = ptotoMap.values();
            System.out.println("keys.size() " + keys.size() + " vals.size()" + vals.size());
            String[] key = keys.toArray(new String[keys.size()]);
            String[] val = vals.toArray(new String[vals.size()]);
            int i = 0;
            Iterator<Map.Entry<String, String>> li = ptotoMap.entrySet().iterator();
            while (li.hasNext()) {
                Map.Entry<String, String> r = li.next();
                key[i] = r.getKey();
                val[i] = r.getValue();
                i++;
            }
            transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Miracle Trading Partner Onboarding Details");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress("skunibilli@miraclesoft.com"));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("rpulleboina@miraclesoft.com"));
            // This HTML mail have to 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");
            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            MimeBodyPart messageBodyPart2 = null;
            MimeBodyPart messageBodyPart3 = null;
            StringBuilder htmlText = new StringBuilder();

            htmlText.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
            htmlText.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
            htmlText.append("<head>");
            htmlText.append("  <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
            htmlText.append("  <title>Your password has been reset successfully&#33;&#33;</title>");
            htmlText.append("  <style type='text/css'>");

            htmlText.append(" body {");
            htmlText.append("  padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   margin:0 !important;");
            htmlText.append("  width: 100% !important;");
            htmlText.append("  -webkit-text-size-adjust: 100% !important;");
            htmlText.append(" -ms-text-size-adjust: 100% !important;;");
            htmlText.append(" -webkit-font-smoothing: antialiased !important;");
            htmlText.append(" }");
            htmlText.append(" .tableContent img {");
            htmlText.append("   border: 0 !important;");
            htmlText.append("  display: block !important;");
            htmlText.append("   outline: none !important;");
            htmlText.append(" }");

            htmlText.append("a{");
            htmlText.append("color:#382F2E;");
            htmlText.append("}");

            htmlText.append("p, h1,h2,ul,ol,li,div{");
            htmlText.append("margin:0;");
            htmlText.append("padding:0;");
            htmlText.append("}");

            htmlText.append("h1,h2{");
            htmlText.append("font-weight: normal;");
            htmlText.append("  background:transparent !important;");
            htmlText.append("border:none !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.big,.contentEditable h1.big{");
            htmlText.append("  font-size: 26px !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.bigger,.contentEditable h1.bigger{");
            htmlText.append("font-size: 37px !important;");
            htmlText.append("}");

            htmlText.append("td,table{");
            htmlText.append("vertical-align: top;");
            htmlText.append("}");

            htmlText.append("td.middle{");
            htmlText.append("vertical-align: middle;");
            htmlText.append("}");

            htmlText.append(" a.link1{");
            htmlText.append("font-size:13px;");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("text-decoration:none;");
            htmlText.append("}");

            htmlText.append("a{");
            htmlText.append("text-decoration: none;");
            htmlText.append("}");

            htmlText.append(".link2{");
            htmlText.append("color:#fc3f3f;");
            htmlText.append("border-top:0px solid #fc3f3f;");
            htmlText.append("border-bottom:0px solid #fc3f3f;");
            htmlText.append("border-left:10px solid #fc3f3f;");
            htmlText.append("border-right:10px solid #fc3f3f;");
            htmlText.append("border-radius:1px;");
            htmlText.append("-moz-border-radius:5px;");
            htmlText.append("-webkit-border-radius:5px;");
            htmlText.append("background:#fc3f3f;");
            htmlText.append("}");

            htmlText.append(".link3{");
            htmlText.append("color:#555555;");
            htmlText.append("border:1px solid #cccccc;");
            htmlText.append("padding:10px 18px;");
            htmlText.append("border-radius:3px;");
            htmlText.append("-moz-border-radius:3px;");
            htmlText.append("-webkit-border-radius:3px;");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".link4{");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("}");

            htmlText.append("h2,h1{");
            htmlText.append("line-height: 20px;");
            htmlText.append("}");

            htmlText.append("p{");
            htmlText.append("font-size: 14px;");
            htmlText.append("line-height: 21px;");
            htmlText.append(" color:#AAAAAA;");
            htmlText.append("}");

            htmlText.append(".contentEditable li{");
            htmlText.append("}");

            htmlText.append(".appart p{");
            htmlText.append("}");

            htmlText.append(".bgItem{");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".bgBody{");
            htmlText.append("background: #0d416b;");
            htmlText.append("}");

            htmlText.append("img {");
            htmlText.append("outline:none;");
            htmlText.append("text-decoration:none;");
            htmlText.append("-ms-interpolation-mode: bicubic;");
            htmlText.append("width: auto;");
            htmlText.append("max-width: 100%;");
            htmlText.append("clear: both;");
            htmlText.append("display: block;");
            htmlText.append("float: none;");
            htmlText.append("}");
            htmlText.append("</style>");

            htmlText.append("<script type='colorScheme' class='swatch active'>");
            htmlText.append("{");
            htmlText.append("'name':'Default',");
            htmlText.append("'bgBody':'ffffff',");
            htmlText.append("'link':'27A1E5',");
            htmlText.append("'color':'AAAAAA',");
            htmlText.append("'bgItem':'ffffff',");
            htmlText.append("'title':'444444'");
            htmlText.append("}");

            htmlText.append("</script>");

            htmlText.append("</head>");
            htmlText.append("<body paddingwidth='0' paddingheight='0' bgcolor='#d1d3d4' style='padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;' offset='0' toppadding='0' leftpadding='0' data-gr-c-s-loaded='true'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='tableContent bgBody' align='center' style='font-family:Helvetica, sans-serif;'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='bgItem' align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='movableContentContainer' align='center'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable'>");
            htmlText.append("<a href='http://www.miraclesoft.com/index.php' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/logo.png' alt='Logo' height='45' data-default='placeholder' data-max-width='200'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;'>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;' width='150'>");
            htmlText.append("<br>");
            htmlText.append("<table width='300' border='0' cellpadding='0' cellspacing='0' align='right' style='text-align: right; font-size: 13px; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='fullCenter'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='55' valign='middle' width='100%' style='font-family: Open Sans; color:#232527;'>");
            htmlText.append("<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com/company/about-us.php' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'>");
            htmlText.append("Company");
            htmlText.append("</a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com/careers/' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'> Careers </a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='580' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='border: 0px solid #ffffff; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#00aae7; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:px'>");
            htmlText.append("<table width='630' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='15'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: left;'>");
            htmlText.append("<h2 style='font-size: 25px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("<b>Trading Partner Information </b>");
            htmlText.append("</font>");
            htmlText.append("</h2>");
            htmlText.append("<br>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("<b>Hello " + loginId + ",</b>");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            if (flag.equalsIgnoreCase("Insert")) {
                htmlText.append("Trading Partner Profile Information :");
            } else {
                htmlText.append("Updated Trading Partner Profile Information :");
            }
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");

            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' style='padding: 5px 0 5px 0; border-top: 1px #2368a0; border-bottom: 1px #2368a0; font-size: 14px; line-height: 25px; font-family: Open Sans; color: #232527;' class='padding-copy'>");
            htmlText.append("<b style='font-size: 14px; color: #ef4048;'>");
            htmlText.append("<p><u><b> Server Details :</b></u><br><br>");
            htmlText.append("Selected Protocol : <b>" + protocol + "</b><br>");
            if (!"".equals(transferMode) && transferMode != null && !"AS2".equals(protocol) && !"SMTP".equals(protocol)) {
                htmlText.append("Transfer Mode : <b>" + transferMode + "</b><br>");
            }

            htmlText.append("<br>");
            if (protocol.equalsIgnoreCase("FTP") || protocol.equalsIgnoreCase("AS2") || protocol.equalsIgnoreCase("SFTP")) {
                htmlText.append(key[0] + "         : <b>" + val[0] + "</b><br>");
                htmlText.append(key[1] + "         : <b>" + val[1] + "</b><br>");
                htmlText.append(key[2] + "         : <b>" + val[2] + "</b><br>");
                htmlText.append(key[3] + "         : <b>" + val[3] + "</b><br>");
                htmlText.append(key[4] + "         : <b>" + val[4] + "</b><br>");
                htmlText.append(key[5] + "         : <b>" + val[5] + "</b><br>");
                htmlText.append(key[6] + "         : <b>" + val[6] + "</b><br>");
                htmlText.append(key[7] + "         : <b>" + val[7] + "</b><br>");
                htmlText.append(key[8] + "         : <b>" + val[8] + "</b><br>");
                htmlText.append("<br>");
            }
            if (protocol.equalsIgnoreCase("HTTP")) {
                htmlText.append(key[0] + "         : <b>" + val[0] + "</b><br>");
                htmlText.append(key[1] + "         : <b>" + val[1] + "</b><br>");
                htmlText.append(key[2] + "         : <b>" + val[2] + "</b><br>");
                htmlText.append(key[3] + "         : <b>" + val[3] + "</b><br>");
                htmlText.append(key[4] + "         : <b>" + val[4] + "</b><br>");
                htmlText.append(key[5] + "         : <b>" + val[5] + "</b><br>");
                htmlText.append("<br>");
            }
            if (protocol.equalsIgnoreCase("SMTP")) {
                htmlText.append(key[0] + "         : <b>" + val[0] + "</b><br>");
                htmlText.append(key[1] + "         : <b>" + val[1] + "</b><br>");
                htmlText.append(key[2] + "         : <b>" + val[2] + "</b><br>");
                htmlText.append(key[3] + "         : <b>" + val[3] + "</b><br>");
                htmlText.append(key[4] + "         : <b>" + val[4] + "</b><br>");
                htmlText.append("<br>");
            }
            if ((protocol.equalsIgnoreCase("AS2"))) {
                //   htmlText.append(key[5] + "         : <b>" + val[5] + "</b><br>");
                if (filepath != null) {
                    messageBodyPart2 = new MimeBodyPart();
                    String filename = filepath;//change accordingly 
                    DataSource source = new FileDataSource(filename);
                    messageBodyPart2.setDataHandler(new DataHandler(source));
                    messageBodyPart2.setFileName((partnerName + "_" + partnerId + "_TRUSTEDCERT") + ".cer");
                }
                if ((sslFilepath != null)) {
                    messageBodyPart3 = new MimeBodyPart();
                    String filename = sslFilepath;//change accordingly 
                    DataSource source = new FileDataSource(filename);
                    messageBodyPart3.setDataHandler(new DataHandler(source));
                    messageBodyPart3.setFileName((partnerName + "_" + partnerId + "_CACERT") + ".cer");
                }
            }
            if ((protocol.equalsIgnoreCase("SFTP")) && (filepath != null)) {
                messageBodyPart2 = new MimeBodyPart();
                String filename = filepath;//change accordingly 
                DataSource source = new FileDataSource(filename);
                messageBodyPart2.setDataHandler(new DataHandler(source));
                messageBodyPart2.setFileName((partnerName + "_" + partnerId + "_PUBLICKEY") + ".cer");
            }
            if ((protocol.equalsIgnoreCase("FTP") || protocol.equalsIgnoreCase("HTTP")) && (sslFilepath != null)) {
                messageBodyPart2 = new MimeBodyPart();
                String filename = sslFilepath;//change accordingly 
                DataSource source = new FileDataSource(filename);
                messageBodyPart2.setDataHandler(new DataHandler(source));
                messageBodyPart2.setFileName((partnerName + "_" + partnerId + "_CACERT") + ".cer");
            }

            htmlText.append("<br>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td style='padding-top: 0px;' align='left' valign='top'>");
            htmlText.append("<table class='textbutton' style='margin: 0;' align='left' border='0' cellpadding='0' cellspacing='0'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' valign='top' style='margin: 0; padding-top: 5px; font-size:14px ; font-weight: normal; color:#000000; font-family: 'Open Sans'; line-height: 180%;  mso-line-height-rule: exactly;'>");

            htmlText.append("<p style='text-align: justify; font-size: 14px;'><font color='#000000' face='trebuchet ms'>");
            htmlText.append("<b>Thanks & Regards,</b><br/>");
            htmlText.append("<b>Miracle TP On-boarding Team,</b><br/>");
            htmlText.append("Miracle Software Systems, Inc.<br/>");
            htmlText.append("<b> Email: </b>");
            htmlText.append("<a href='mailto:mscvp_alerts@miraclesoft.com '>");
            htmlText.append("mscvp_alerts@miraclesoft.com </a>");
            htmlText.append("<br/>");
            htmlText.append("<b>Phone: </b>");
            htmlText.append("(+1)248-232-0224");
            htmlText.append("</p>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='left' style='padding: 5px 0 5px 0; font-size: 14px; line-height: 22px; font-family: Open Sans; color: #ef4048; font-style: italic;' class='padding-copy'>");
            htmlText.append("* Note: Please do not reply to this email as this is an automated notification");
            htmlText.append("</td>");
            htmlText.append("</tr>");

            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='655' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("<td width='660' align='center' style='text-align: center;'>");
            htmlText.append("<table width='660' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<p style='text-align: center; font-size: 14px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append(" ©Copyright 2016 Miracle Software Systems, Inc.<br>");
            htmlText.append("45625 Grand River Avenue<br>");
            htmlText.append("Novi, MI - USA");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("<td width='190' align='center' style='text-align: center;'>");
            htmlText.append("<table width='190' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentFacebookEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.facebook.com/miracle45625' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/fb.png' alt='facebook' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentTwitterEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://twitter.com/team_mss' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/tweet.png' alt='twitter' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://plus.google.com/+Team_MSS/posts' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/googleplus.png' alt='Pinterest' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.linkedin.com/company/miracle-software-systems-inc' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/linkedin.png' alt='Social media' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='40'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");

            htmlText.append("<span class='gr__tooltip'>");
            htmlText.append("<span class='gr__tooltip-content'>");
            htmlText.append("</span>");
            htmlText.append("<i class='gr__tooltip-logo'>");
            htmlText.append("</i>");
            htmlText.append("<span class='gr__triangle'>");
            htmlText.append("</span>");
            htmlText.append("</span>");
            htmlText.append("</body>");
            htmlText.append("</html>");

            messageBodyPart.setContent(htmlText.toString(), "text/html");

            multipart.addBodyPart(messageBodyPart);
            if (messageBodyPart2 != null) {
                multipart.addBodyPart(messageBodyPart2);
            }
            if (messageBodyPart3 != null) {
                multipart.addBodyPart(messageBodyPart3);
            }

            // put everything together
            message.setContent(multipart);

            transport.connect();
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.CC));
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.BCC));
            transport.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }

    public static void tpoUserIdPwd(String regContactName, String regPartName, String email, String loginId, String password) {
        // SUBSTITUTE YOUR EMAIL ADDRESSES HERE!!!
        /**
         * The to is used for storing the user mail id to send details.
         */
        System.out.println("regContactName " + regContactName + " regPartName " + regPartName + " email " + email + " loginId " + loginId + " password " + password);
        String to = email;
        /**
         * The from is used for storing the from address.
         */
        String from = "mscvp_alerts@miraclesoft.com";
        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
        /**
         * The host is used for storing the IP address of mail
         */
        /**
         * The props is instance variabel to <code>Properties</code> class
         */
        Properties props = new Properties();
        /**
         * Here set smtp protocal to props
         */
        props.setProperty("mail.transport.protocol", "smtp");
        //**Here set the address of the host to props */
        props.setProperty("mail.host", SMTP_HOST);
        props.put("mail.smtp.starttls.enable", "true");
        /**
         * Here set the authentication for the host *
         */
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", PORT);
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        mailSession.setDebug(true);
        Transport transport;
        try {
            transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Miracle Trading Partner Onboarding Password Details");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress("skunibilli@miraclesoft.com"));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("rpulleboina@miraclesoft.com"));
            // This HTML mail have to 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");
            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            StringBuilder htmlText = new StringBuilder();

            htmlText.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
            htmlText.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
            htmlText.append("<head>");
            htmlText.append("  <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
            htmlText.append("  <title>Your password has been reset successfully&#33;&#33;</title>");
            htmlText.append("  <style type='text/css'>");

            htmlText.append(" body {");
            htmlText.append("  padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   margin:0 !important;");
            htmlText.append("  width: 100% !important;");
            htmlText.append("  -webkit-text-size-adjust: 100% !important;");
            htmlText.append(" -ms-text-size-adjust: 100% !important;;");
            htmlText.append(" -webkit-font-smoothing: antialiased !important;");
            htmlText.append(" }");
            htmlText.append(" .tableContent img {");
            htmlText.append("   border: 0 !important;");
            htmlText.append("  display: block !important;");
            htmlText.append("   outline: none !important;");
            htmlText.append(" }");

            htmlText.append("a{");
            htmlText.append("color:#382F2E;");
            htmlText.append("}");

            htmlText.append("p, h1,h2,ul,ol,li,div{");
            htmlText.append("margin:0;");
            htmlText.append("padding:0;");
            htmlText.append("}");

            htmlText.append("h1,h2{");
            htmlText.append("font-weight: normal;");
            htmlText.append("  background:transparent !important;");
            htmlText.append("border:none !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.big,.contentEditable h1.big{");
            htmlText.append("  font-size: 26px !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.bigger,.contentEditable h1.bigger{");
            htmlText.append("font-size: 37px !important;");
            htmlText.append("}");

            htmlText.append("td,table{");
            htmlText.append("vertical-align: top;");
            htmlText.append("}");

            htmlText.append("td.middle{");
            htmlText.append("vertical-align: middle;");
            htmlText.append("}");

            htmlText.append(" a.link1{");
            htmlText.append("font-size:13px;");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("text-decoration:none;");
            htmlText.append("}");

            htmlText.append("a{");
            htmlText.append("text-decoration: none;");
            htmlText.append("}");

            htmlText.append(".link2{");
            htmlText.append("color:#fc3f3f;");
            htmlText.append("border-top:0px solid #fc3f3f;");
            htmlText.append("border-bottom:0px solid #fc3f3f;");
            htmlText.append("border-left:10px solid #fc3f3f;");
            htmlText.append("border-right:10px solid #fc3f3f;");
            htmlText.append("border-radius:1px;");
            htmlText.append("-moz-border-radius:5px;");
            htmlText.append("-webkit-border-radius:5px;");
            htmlText.append("background:#fc3f3f;");
            htmlText.append("}");

            htmlText.append(".link3{");
            htmlText.append("color:#555555;");
            htmlText.append("border:1px solid #cccccc;");
            htmlText.append("padding:10px 18px;");
            htmlText.append("border-radius:3px;");
            htmlText.append("-moz-border-radius:3px;");
            htmlText.append("-webkit-border-radius:3px;");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".link4{");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("}");

            htmlText.append("h2,h1{");
            htmlText.append("line-height: 20px;");
            htmlText.append("}");

            htmlText.append("p{");
            htmlText.append("font-size: 14px;");
            htmlText.append("line-height: 21px;");
            htmlText.append(" color:#AAAAAA;");
            htmlText.append("}");

            htmlText.append(".contentEditable li{");
            htmlText.append("}");

            htmlText.append(".appart p{");
            htmlText.append("}");

            htmlText.append(".bgItem{");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".bgBody{");
            htmlText.append("background: #0d416b;");
            htmlText.append("}");

            htmlText.append("img {");
            htmlText.append("outline:none;");
            htmlText.append("text-decoration:none;");
            htmlText.append("-ms-interpolation-mode: bicubic;");
            htmlText.append("width: auto;");
            htmlText.append("max-width: 100%;");
            htmlText.append("clear: both;");
            htmlText.append("display: block;");
            htmlText.append("float: none;");
            htmlText.append("}");
            htmlText.append("</style>");

            htmlText.append("<script type='colorScheme' class='swatch active'>");
            htmlText.append("{");
            htmlText.append("'name':'Default',");
            htmlText.append("'bgBody':'ffffff',");
            htmlText.append("'link':'27A1E5',");
            htmlText.append("'color':'AAAAAA',");
            htmlText.append("'bgItem':'ffffff',");
            htmlText.append("'title':'444444'");
            htmlText.append("}");

            htmlText.append("</script>");

            htmlText.append("</head>");
            htmlText.append("<body paddingwidth='0' paddingheight='0' bgcolor='#d1d3d4' style='padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;' offset='0' toppadding='0' leftpadding='0' data-gr-c-s-loaded='true'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='tableContent bgBody' align='center' style='font-family:Helvetica, sans-serif;'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='bgItem' align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='movableContentContainer' align='center'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable'>");
            htmlText.append("<a href='http://www.miraclesoft.com/index.php' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/logo.png' alt='Logo' height='45' data-default='placeholder' data-max-width='200'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;'>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;' width='150'>");
            htmlText.append("<br>");
            htmlText.append("<table width='300' border='0' cellpadding='0' cellspacing='0' align='right' style='text-align: right; font-size: 13px; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='fullCenter'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='55' valign='middle' width='100%' style='font-family: Open Sans; color:#232527;'>");
            htmlText.append("<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'>");
            htmlText.append("Company");
            htmlText.append("</a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com/careers/' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'> Careers </a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='580' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='border: 0px solid #ffffff; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#00aae7; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:px'>");
            htmlText.append("<table width='630' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='15'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: left;'>");
            htmlText.append("<h2 style='font-size: 25px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("<b>User Account Created</b>");
            htmlText.append("</font>");
            htmlText.append("</h2>");
            htmlText.append("<br>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("<p>Hello " + regContactName + "</p>");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("Welcome to Miracle's Trading Partner On-boarding Portal! Your account has been created with the following credentials. Please login and change your password within 48 hours of this email. ");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");

            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' style='padding: 5px 0 5px 0; border-top: 1px #2368a0; border-bottom: 1px #2368a0; font-size: 14px; line-height: 25px; font-family: Open Sans; color: #232527;' class='padding-copy'>");
            htmlText.append("<b style='font-size: 14px; color: #ef4048;'>");
            htmlText.append("Login Id: " + loginId + "</b>");
            htmlText.append("<br>");
            htmlText.append("<b style='font-size: 14px; color: #ef4048;'>");
            htmlText.append("Password: " + password + "</b>");
            htmlText.append("<br>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr><td align='center' valign='middle' style='color:#FFFFFF; font-family:Helvetica, Arial, sans-serif; font-size:16px; font-weight:bold; letter-spacing:-.5px; line-height:150%; padding-top:5px; padding-right:20px; padding-bottom:5px; padding-left:20px;'>");
            htmlText.append("<a href='http://192.168.1.179:8084/tpo' target='_blank' style='color:#FFFFFF; text-decoration:none;'>Click here to login</a></td></tr></table><br><br>");
            htmlText.append("<tr>");
            htmlText.append("<td style='padding-top: 0px;' align='left' valign='top'>");
            htmlText.append("<table class='textbutton' style='margin: 0;' align='left' border='0' cellpadding='0' cellspacing='0'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' valign='top' style='margin: 0; padding-top: 5px; font-size:14px ; font-weight: normal; color:#000000; font-family: 'Open Sans'; line-height: 180%;  mso-line-height-rule: exactly;'>");

            htmlText.append("<p style='text-align: justify; font-size: 14px;'><font color='#000000' face='trebuchet ms'>");
            htmlText.append("<b>With Thank you,</b><br/>");
            htmlText.append("<b>Miracle TP On-boarding Team,</b><br/>");
            htmlText.append("Miracle Software Systems, Inc.<br/>");
            htmlText.append("<b> Email: </b>");
            htmlText.append("<a href='mailto:mscvp_alerts@miraclesoft.com '>");
            htmlText.append("mscvp_alerts@miraclesoft.com </a>");
            htmlText.append("<br/>");
            htmlText.append("<b>Phone: </b>");
            htmlText.append("(+1)248-232-0224");
            htmlText.append("</p>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='left' style='padding: 5px 0 5px 0; font-size: 14px; line-height: 22px; font-family: Open Sans; color: #ef4048; font-style: italic;' class='padding-copy'>");
            htmlText.append("* Note: Please do not reply to this email as this is an automated notification");
            htmlText.append("</td>");
            htmlText.append("</tr>");

            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='655' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("<td width='660' align='center' style='text-align: center;'>");
            htmlText.append("<table width='660' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<p style='text-align: center; font-size: 14px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append(" ©Copyright 2016 Miracle Software Systems, Inc.<br>");
            htmlText.append("45625 Grand River Avenue<br>");
            htmlText.append("Novi, MI - USA");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("<td width='190' align='center' style='text-align: center;'>");
            htmlText.append("<table width='190' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentFacebookEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.facebook.com/miracle45625' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/fb.png' alt='facebook' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentTwitterEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://twitter.com/team_mss' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/tweet.png' alt='twitter' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://plus.google.com/+Team_MSS/posts' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/googleplus.png' alt='Pinterest' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.linkedin.com/company/miracle-software-systems-inc' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/linkedin.png' alt='Social media' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='40'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");

            htmlText.append("<span class='gr__tooltip'>");
            htmlText.append("<span class='gr__tooltip-content'>");
            htmlText.append("</span>");
            htmlText.append("<i class='gr__tooltip-logo'>");
            htmlText.append("</i>");
            htmlText.append("<span class='gr__triangle'>");
            htmlText.append("</span>");
            htmlText.append("</span>");
            htmlText.append("</body>");
            htmlText.append("</html>");

//            htmlText.append("<html><head><title>Mail From Miracle Trading Partner Onboarding Portal</title>");
//            htmlText.append("</head><body><font color='blue' size='2' face='Arial'>");
//            htmlText.append("<p>Hello " + regContactName + "</p>");
//            htmlText.append("<p><u><b>Your Login Details :</b></u><br><br>");
//            htmlText.append("Login Id : <b>" + loginId + "</b><br>");
//            htmlText.append("Password : <b>" + password + "</b><br><br>");
//            htmlText.append("<table border='0' cellpadding='0' cellspacing='0' style='background-color:#338dff; border:1px solid #353535; border-radius:5px;'>");
//            htmlText.append("<tr><td align='center' valign='middle' style='color:#FFFFFF; font-family:Helvetica, Arial, sans-serif; font-size:16px; font-weight:bold; letter-spacing:-.5px; line-height:150%; padding-top:5px; padding-right:20px; padding-bottom:5px; padding-left:20px;'>");
//            htmlText.append("<a href='http://192.168.1.179:8084/tpo' target='_blank' style='color:#FFFFFF; text-decoration:none;'>Click here to login</a></td></tr></table><br><br>");
//            htmlText.append("<b>Please Note:</b><br>");
//            htmlText.append("To better protect ");
//            htmlText.append("your account, make sure that your password is memorable ");
//            htmlText.append("for you but difficult for others to guess. Never ");
//            htmlText.append("use the same password that you have used in the past,");
//            htmlText.append(" and do not share your password with anyone.<br></br><br>");
//            htmlText.append("<b>Regards,</b><br>");
//            htmlText.append("Miracle Trading Partner Onboarding Portal Team,</p></font><br>");
//            htmlText.append("<font color='red', size='2' face='Arial'>*Note:Please do not reply to this e-mail.  It was generated by our System.</font>");
//            htmlText.append("</body></html>");
            messageBodyPart.setContent(htmlText.toString(), "text/html");

            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            transport.connect();
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.CC));
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.BCC));
            transport.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }

    public static void tpoPartnerSelfRegistrationMail(String userloginId, String password, AjaxHandlerAction ajaxHandlerAction) {
        // SUBSTITUTE YOUR EMAIL ADDRESSES HERE!!!
        /**
         * The to is used for storing the user mail id to send details.
         */
        String to = ajaxHandlerAction.getEmail();
        /**
         * The from is used for storing the from address.
         */
        String from = "mscvp_alerts@miraclesoft.com";
        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
        /**
         * The host is used for storing the IP address of mail
         */
        /**
         * The props is instance variabel to <code>Properties</code> class
         */
        Properties props = new Properties();
        /**
         * Here set smtp protocal to props
         */
        props.setProperty("mail.transport.protocol", "smtp");
        //**Here set the address of the host to props */
        props.setProperty("mail.host", SMTP_HOST);
        props.put("mail.smtp.starttls.enable", "true");
        /**
         * Here set the authentication for the host *
         */
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", PORT);
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        mailSession.setDebug(true);
        Transport transport;
        try {
            transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Miracle Trading Partner Onboarding Password Details");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress("skunibilli@miraclesoft.com"));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("rpulleboina@miraclesoft.com"));

            // This HTML mail have to 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");
            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            StringBuilder htmlText = new StringBuilder();
            htmlText.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
            htmlText.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
            htmlText.append("<head>");
            htmlText.append("  <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
            htmlText.append("  <title>Partner Self Registration Details</title>");
            htmlText.append("  <style type='text/css'>");

            htmlText.append(" body {");
            htmlText.append("  padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   margin:0 !important;");
            htmlText.append("  width: 100% !important;");
            htmlText.append("  -webkit-text-size-adjust: 100% !important;");
            htmlText.append(" -ms-text-size-adjust: 100% !important;;");
            htmlText.append(" -webkit-font-smoothing: antialiased !important;");
            htmlText.append(" }");
            htmlText.append(" .tableContent img {");
            htmlText.append("   border: 0 !important;");
            htmlText.append("  display: block !important;");
            htmlText.append("   outline: none !important;");
            htmlText.append(" }");

            htmlText.append("a{");
            htmlText.append("color:#382F2E;");
            htmlText.append("}");

            htmlText.append("p, h1,h2,ul,ol,li,div{");
            htmlText.append("margin:0;");
            htmlText.append("padding:0;");
            htmlText.append("}");

            htmlText.append("h1,h2{");
            htmlText.append("font-weight: normal;");
            htmlText.append("  background:transparent !important;");
            htmlText.append("border:none !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.big,.contentEditable h1.big{");
            htmlText.append("  font-size: 26px !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.bigger,.contentEditable h1.bigger{");
            htmlText.append("font-size: 37px !important;");
            htmlText.append("}");

            htmlText.append("td,table{");
            htmlText.append("vertical-align: top;");
            htmlText.append("}");

            htmlText.append("td.middle{");
            htmlText.append("vertical-align: middle;");
            htmlText.append("}");

            htmlText.append(" a.link1{");
            htmlText.append("font-size:13px;");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("text-decoration:none;");
            htmlText.append("}");

            htmlText.append("a{");
            htmlText.append("text-decoration: none;");
            htmlText.append("}");

            htmlText.append(".link2{");
            htmlText.append("color:#fc3f3f;");
            htmlText.append("border-top:0px solid #fc3f3f;");
            htmlText.append("border-bottom:0px solid #fc3f3f;");
            htmlText.append("border-left:10px solid #fc3f3f;");
            htmlText.append("border-right:10px solid #fc3f3f;");
            htmlText.append("border-radius:1px;");
            htmlText.append("-moz-border-radius:5px;");
            htmlText.append("-webkit-border-radius:5px;");
            htmlText.append("background:#fc3f3f;");
            htmlText.append("}");

            htmlText.append(".link3{");
            htmlText.append("color:#555555;");
            htmlText.append("border:1px solid #cccccc;");
            htmlText.append("padding:10px 18px;");
            htmlText.append("border-radius:3px;");
            htmlText.append("-moz-border-radius:3px;");
            htmlText.append("-webkit-border-radius:3px;");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".link4{");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("}");

            htmlText.append("h2,h1{");
            htmlText.append("line-height: 20px;");
            htmlText.append("}");

            htmlText.append("p{");
            htmlText.append("font-size: 14px;");
            htmlText.append("line-height: 21px;");
            htmlText.append(" color:#AAAAAA;");
            htmlText.append("}");

            htmlText.append(".contentEditable li{");
            htmlText.append("}");

            htmlText.append(".appart p{");
            htmlText.append("}");

            htmlText.append(".bgItem{");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".bgBody{");
            htmlText.append("background: #0d416b;");
            htmlText.append("}");

            htmlText.append("img {");
            htmlText.append("outline:none;");
            htmlText.append("text-decoration:none;");
            htmlText.append("-ms-interpolation-mode: bicubic;");
            htmlText.append("width: auto;");
            htmlText.append("max-width: 100%;");
            htmlText.append("clear: both;");
            htmlText.append("display: block;");
            htmlText.append("float: none;");
            htmlText.append("}");
            htmlText.append("</style>");

            htmlText.append("<script type='colorScheme' class='swatch active'>");
            htmlText.append("{");
            htmlText.append("'name':'Default',");
            htmlText.append("'bgBody':'ffffff',");
            htmlText.append("'link':'27A1E5',");
            htmlText.append("'color':'AAAAAA',");
            htmlText.append("'bgItem':'ffffff',");
            htmlText.append("'title':'444444'");
            htmlText.append("}");

            htmlText.append("</script>");

            htmlText.append("</head>");
            htmlText.append("<body paddingwidth='0' paddingheight='0' bgcolor='#d1d3d4' style='padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;' offset='0' toppadding='0' leftpadding='0' data-gr-c-s-loaded='true'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='tableContent bgBody' align='center' style='font-family:Helvetica, sans-serif;'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='bgItem' align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='movableContentContainer' align='center'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable'>");
            htmlText.append("<a href='http://www.miraclesoft.com/index.php' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/logo.png' alt='Logo' height='45' data-default='placeholder' data-max-width='200'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;'>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;' width='150'>");
            htmlText.append("<br>");
            htmlText.append("<table width='300' border='0' cellpadding='0' cellspacing='0' align='right' style='text-align: right; font-size: 13px; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='fullCenter'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='55' valign='middle' width='100%' style='font-family: Open Sans; color:#232527;'>");
            htmlText.append("<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com/company/about-us.php' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'>");
            htmlText.append("Company");
            htmlText.append("</a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com/careers/' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'> Careers </a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='580' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='border: 0px solid #ffffff; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#00aae7; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:px'>");
            htmlText.append("<table width='630' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='15'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: left;'>");
            htmlText.append("<h2 style='font-size: 25px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("<b>Trading Partner Information </b>");
            htmlText.append("</font>");
            htmlText.append("</h2>");
            htmlText.append("<br>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("<b>Hello " + ajaxHandlerAction.getAddpartnerName() + ",</b>");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");

            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");

            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' style='padding: 5px 0 5px 0; border-top: 1px #2368a0; border-bottom: 1px #2368a0; font-size: 14px; line-height: 25px; font-family: Open Sans; color: #232527;' class='padding-copy'>");
            htmlText.append("<b style='font-size: 14px; color: #ef4048;'>");

            htmlText.append("<p><u><b> Your Login Details :</b></u><br>");
            htmlText.append("LoginId : <b>" + userloginId + "</b><br>");
            htmlText.append("Password : <b>" + password + "</b><br>");

            htmlText.append("<br>");
            htmlText.append("</p><br>");
            htmlText.append("<p><u><b> Your Registration Details :</b></u><br><br>");
            htmlText.append("Partner Name : <b>" + ajaxHandlerAction.getAddpartnerName() + "</b><br>");
            htmlText.append("Phone No : <b>" + ajaxHandlerAction.getAddphoneNo() + "</b><br>");
            htmlText.append("Address : <b>" + ajaxHandlerAction.getAddaddress1() + "</b><br>");
            htmlText.append("City : <b>" + ajaxHandlerAction.getAddcity() + "</b><br>");
            htmlText.append("State : <b>" + ajaxHandlerAction.getAddstate() + "</b><br>");
            htmlText.append("Country : <b>" + ajaxHandlerAction.getAddcountry() + "</b><br>");
            htmlText.append("Zip-Code : <b>" + ajaxHandlerAction.getAddzipCode() + "</b><br>");
            htmlText.append("URL : <b>" + ajaxHandlerAction.getUrl() + "</b><br>");
            htmlText.append("Description : <b>" + ajaxHandlerAction.getDescription() + "</b><br>");

            htmlText.append("<br>");
            htmlText.append("</p></b></td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td style='padding-top: 0px;' align='left' valign='top'>");
            htmlText.append("<table class='textbutton' style='margin: 0;' align='left' border='0' cellpadding='0' cellspacing='0'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' valign='top' style='margin: 0; padding-top: 5px; font-size:14px ; font-weight: normal; color:#000000; font-family: 'Open Sans'; line-height: 180%;  mso-line-height-rule: exactly;'>");

            htmlText.append("<p style='text-align: justify; font-size: 14px;'><font color='#000000' face='trebuchet ms'>");
            htmlText.append("<b>Thanks & Regards,</b><br/>");
            htmlText.append("<b>Miracle TP On-boarding Team,</b><br/>");
            htmlText.append("Miracle Software Systems, Inc.<br/>");
            htmlText.append("<b> Email: </b>");
            htmlText.append("<a href='mailto:mscvp_alerts@miraclesoft.com '>");
            htmlText.append("mscvp_alerts@miraclesoft.com </a>");
            htmlText.append("<br/>");
            htmlText.append("<b>Phone: </b>");
            htmlText.append("(+1)248-232-0224");
            htmlText.append("</p>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='left' style='padding: 5px 0 5px 0; font-size: 14px; line-height: 22px; font-family: Open Sans; color: #ef4048; font-style: italic;' class='padding-copy'>");
            htmlText.append("* Note: Please do not reply to this email as this is an automated notification");
            htmlText.append("</td>");
            htmlText.append("</tr>");

            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='655' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("<td width='660' align='center' style='text-align: center;'>");
            htmlText.append("<table width='660' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<p style='text-align: center; font-size: 14px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append(" ©Copyright 2016 Miracle Software Systems, Inc.<br>");
            htmlText.append("45625 Grand River Avenue<br>");
            htmlText.append("Novi, MI - USA");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("<td width='190' align='center' style='text-align: center;'>");
            htmlText.append("<table width='190' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentFacebookEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.facebook.com/miracle45625' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/fb.png' alt='facebook' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentTwitterEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://twitter.com/team_mss' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/tweet.png' alt='twitter' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://plus.google.com/+Team_MSS/posts' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/googleplus.png' alt='Pinterest' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.linkedin.com/company/miracle-software-systems-inc' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/linkedin.png' alt='Social media' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='40'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");

            htmlText.append("<span class='gr__tooltip'>");
            htmlText.append("<span class='gr__tooltip-content'>");
            htmlText.append("</span>");
            htmlText.append("<i class='gr__tooltip-logo'>");
            htmlText.append("</i>");
            htmlText.append("<span class='gr__triangle'>");
            htmlText.append("</span>");
            htmlText.append("</span>");
            htmlText.append("</body>");
            htmlText.append("</html>");

            messageBodyPart.setContent(htmlText.toString(), "text/html");

            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            transport.connect();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.CC));
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.BCC));

            transport.close();

            /* Mail to admin*/
            String toAdmin = "rpulleboina@miraclesoft.com";
            Session mailSession1 = Session.getDefaultInstance(props, auth);
            mailSession1.setDebug(true);
            Transport transport1;
            transport1 = mailSession1.getTransport();
            MimeMessage message1 = new MimeMessage(mailSession);
            message1.setSubject("Miracle Trading Partner Onboarding Password Details");
            message1.setFrom(new InternetAddress(from));
            message1.addRecipient(Message.RecipientType.TO, new InternetAddress(toAdmin));
            message1.addRecipient(Message.RecipientType.CC, new InternetAddress("skunibilli@miraclesoft.com"));
            message1.addRecipient(Message.RecipientType.BCC, new InternetAddress("rpulleboina@miraclesoft.com"));

            // This HTML mail have to 2 part, the BODY and the embedded image
            MimeMultipart multipart1 = new MimeMultipart("related");
            // first part  (the html)
            BodyPart messageBodyPart1 = new MimeBodyPart();
            StringBuilder htmlText1 = new StringBuilder();
            htmlText1.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
            htmlText1.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
            htmlText1.append("<head>");
            htmlText1.append("  <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
            htmlText1.append("  <title>Partner Self Registration Details</title>");
            htmlText1.append("  <style type='text/css'>");

            htmlText1.append(" body {");
            htmlText1.append("  padding-top: 0 !important;");
            htmlText1.append("  padding-bottom: 0 !important;");
            htmlText1.append("   padding-top: 0 !important;");
            htmlText1.append("  padding-bottom: 0 !important;");
            htmlText1.append("   margin:0 !important;");
            htmlText1.append("  width: 100% !important;");
            htmlText1.append("  -webkit-text-size-adjust: 100% !important;");
            htmlText1.append(" -ms-text-size-adjust: 100% !important;;");
            htmlText1.append(" -webkit-font-smoothing: antialiased !important;");
            htmlText1.append(" }");
            htmlText1.append(" .tableContent img {");
            htmlText1.append("   border: 0 !important;");
            htmlText1.append("  display: block !important;");
            htmlText1.append("   outline: none !important;");
            htmlText1.append(" }");

            htmlText1.append("a{");
            htmlText1.append("color:#382F2E;");
            htmlText1.append("}");

            htmlText1.append("p, h1,h2,ul,ol,li,div{");
            htmlText1.append("margin:0;");
            htmlText1.append("padding:0;");
            htmlText1.append("}");

            htmlText1.append("h1,h2{");
            htmlText1.append("font-weight: normal;");
            htmlText1.append("  background:transparent !important;");
            htmlText1.append("border:none !important;");
            htmlText1.append("}");

            htmlText1.append(".contentEditable h2.big,.contentEditable h1.big{");
            htmlText1.append("  font-size: 26px !important;");
            htmlText1.append("}");

            htmlText1.append(".contentEditable h2.bigger,.contentEditable h1.bigger{");
            htmlText1.append("font-size: 37px !important;");
            htmlText1.append("}");

            htmlText1.append("td,table{");
            htmlText1.append("vertical-align: top;");
            htmlText1.append("}");

            htmlText1.append("td.middle{");
            htmlText1.append("vertical-align: middle;");
            htmlText1.append("}");

            htmlText1.append(" a.link1{");
            htmlText1.append("font-size:13px;");
            htmlText1.append("color:#27A1E5;");
            htmlText1.append("line-height: 24px;");
            htmlText1.append("text-decoration:none;");
            htmlText1.append("}");

            htmlText1.append("a{");
            htmlText1.append("text-decoration: none;");
            htmlText1.append("}");

            htmlText1.append(".link2{");
            htmlText1.append("color:#fc3f3f;");
            htmlText1.append("border-top:0px solid #fc3f3f;");
            htmlText1.append("border-bottom:0px solid #fc3f3f;");
            htmlText1.append("border-left:10px solid #fc3f3f;");
            htmlText1.append("border-right:10px solid #fc3f3f;");
            htmlText1.append("border-radius:1px;");
            htmlText1.append("-moz-border-radius:5px;");
            htmlText1.append("-webkit-border-radius:5px;");
            htmlText1.append("background:#fc3f3f;");
            htmlText1.append("}");

            htmlText1.append(".link3{");
            htmlText1.append("color:#555555;");
            htmlText1.append("border:1px solid #cccccc;");
            htmlText1.append("padding:10px 18px;");
            htmlText1.append("border-radius:3px;");
            htmlText1.append("-moz-border-radius:3px;");
            htmlText1.append("-webkit-border-radius:3px;");
            htmlText1.append("background:#ffffff;");
            htmlText1.append("}");

            htmlText1.append(".link4{");
            htmlText1.append("color:#27A1E5;");
            htmlText1.append("line-height: 24px;");
            htmlText1.append("}");

            htmlText1.append("h2,h1{");
            htmlText1.append("line-height: 20px;");
            htmlText1.append("}");

            htmlText1.append("p{");
            htmlText1.append("font-size: 14px;");
            htmlText1.append("line-height: 21px;");
            htmlText1.append(" color:#AAAAAA;");
            htmlText1.append("}");

            htmlText1.append(".contentEditable li{");
            htmlText1.append("}");

            htmlText1.append(".appart p{");
            htmlText1.append("}");

            htmlText1.append(".bgItem{");
            htmlText1.append("background:#ffffff;");
            htmlText1.append("}");

            htmlText1.append(".bgBody{");
            htmlText1.append("background: #0d416b;");
            htmlText1.append("}");

            htmlText1.append("img {");
            htmlText1.append("outline:none;");
            htmlText1.append("text-decoration:none;");
            htmlText1.append("-ms-interpolation-mode: bicubic;");
            htmlText1.append("width: auto;");
            htmlText1.append("max-width: 100%;");
            htmlText1.append("clear: both;");
            htmlText1.append("display: block;");
            htmlText1.append("float: none;");
            htmlText1.append("}");
            htmlText1.append("</style>");

            htmlText1.append("<script type='colorScheme' class='swatch active'>");
            htmlText1.append("{");
            htmlText1.append("'name':'Default',");
            htmlText1.append("'bgBody':'ffffff',");
            htmlText1.append("'link':'27A1E5',");
            htmlText1.append("'color':'AAAAAA',");
            htmlText1.append("'bgItem':'ffffff',");
            htmlText1.append("'title':'444444'");
            htmlText1.append("}");

            htmlText1.append("</script>");

            htmlText1.append("</head>");
            htmlText1.append("<body paddingwidth='0' paddingheight='0' bgcolor='#d1d3d4' style='padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;' offset='0' toppadding='0' leftpadding='0' data-gr-c-s-loaded='true'>");
            htmlText1.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='tableContent bgBody' align='center' style='font-family:Helvetica, sans-serif;'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td align='center'>");
            htmlText1.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td class='bgItem' align='center'>");
            htmlText1.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td class='movableContentContainer' align='center'>");
            htmlText1.append("<div class='movableContent'>");
            htmlText1.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='20'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText1.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td>");
            htmlText1.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText1.append("<div class='contentEditable'>");
            htmlText1.append("<a href='http://www.miraclesoft.com/index.php' target='_blank'>");
            htmlText1.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/logo.png' alt='Logo' height='45' data-default='placeholder' data-max-width='200'>");
            htmlText1.append("</a>");
            htmlText1.append("</div>");
            htmlText1.append("</div>");
            htmlText1.append("</td>");
            htmlText1.append("<td valign='middle' style='vertical-align: middle;'>");
            htmlText1.append("</td>");
            htmlText1.append("<td valign='middle' style='vertical-align: middle;' width='150'>");
            htmlText1.append("<br>");
            htmlText1.append("<table width='300' border='0' cellpadding='0' cellspacing='0' align='right' style='text-align: right; font-size: 13px; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='fullCenter'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td height='55' valign='middle' width='100%' style='font-family: Open Sans; color:#232527;'>");
            htmlText1.append("<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText1.append("<a href='http://www.miraclesoft.com/company/about-us.php' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'>");
            htmlText1.append("Company");
            htmlText1.append("</a>");
            htmlText1.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText1.append("<a href='http://www.miraclesoft.com/careers/' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'> Careers </a>");
            htmlText1.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</div>");
            htmlText1.append("<div class='movableContent'>");
            htmlText1.append("<table width='580' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td style='border: 0px solid #ffffff; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText1.append("<div class='movableContent'>");
            htmlText1.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td style='background:#00aae7; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:px'>");
            htmlText1.append("<table width='630' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td height='15'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td>");
            htmlText1.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText1.append("<div class='contentEditable' style='text-align: left;'>");
            htmlText1.append("<h2 style='font-size: 25px;'>");
            htmlText1.append("<font color='#ffffff' face='Open Sans'>");
            htmlText1.append("<b>Trading Partner Information </b>");
            htmlText1.append("</font>");
            htmlText1.append("</h2>");
            htmlText1.append("<br>");
            htmlText1.append("</div>");
            htmlText1.append("</div>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</div>");
            htmlText1.append("<p>");
            htmlText1.append("</p>");
            htmlText1.append("<p>");
            htmlText1.append("</p>");
            htmlText1.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td height='5'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td>");
            htmlText1.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText1.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText1.append("<br>");
            htmlText1.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText1.append("<font color='#232527' face='Open Sans'>");
            htmlText1.append("<b>Hello Admin,</b>");
            htmlText1.append("</font>");
            htmlText1.append("</p>");
            htmlText1.append("<font color='#232527' face='Open Sans'>");
            htmlText1.append("</font>");
            htmlText1.append("</div>");
            htmlText1.append("<font color='#232527' face='Open Sans'>");
            htmlText1.append("</font>");
            htmlText1.append("</div>");
            htmlText1.append("<font color='#232527' face='Open Sans'>");
            htmlText1.append("</font>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td height='0'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td>");
            htmlText1.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText1.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText1.append("<br>");
            htmlText1.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText1.append("<font color='#232527' face='Open Sans'>");

            htmlText1.append("</font>");
            htmlText1.append("</p>");
            htmlText1.append("<font color='#232527' face='Open Sans'>");

            htmlText1.append("</font>");
            htmlText1.append("</div>");
            htmlText1.append("<font color='#232527' face='Open Sans'>");
            htmlText1.append("</font>");
            htmlText1.append("</div>");
            htmlText1.append("<font color='#232527' face='Open Sans'>");
            htmlText1.append("</font>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td align='justify' style='padding: 5px 0 5px 0; border-top: 1px #2368a0; border-bottom: 1px #2368a0; font-size: 14px; line-height: 25px; font-family: Open Sans; color: #232527;' class='padding-copy'>");
            htmlText1.append("<b style='font-size: 14px; color: #ef4048;'>");
            htmlText1.append("<p><u><b> Partner Registration Details :</b></u><br><br>");
            htmlText1.append("Partner Name : <b>" + ajaxHandlerAction.getAddpartnerName() + "</b><br>");
            htmlText1.append("Phone No : <b>" + ajaxHandlerAction.getAddphoneNo() + "</b><br>");
            htmlText1.append("Address : <b>" + ajaxHandlerAction.getAddaddress1() + "</b><br>");
            htmlText1.append("City : <b>" + ajaxHandlerAction.getAddcity() + "</b><br>");
            htmlText1.append("State : <b>" + ajaxHandlerAction.getAddstate() + "</b><br>");
            htmlText1.append("Country : <b>" + ajaxHandlerAction.getAddcountry() + "</b><br>");
            htmlText1.append("Zip-Code : <b>" + ajaxHandlerAction.getAddzipCode() + "</b><br>");
            htmlText1.append("URL : <b>" + ajaxHandlerAction.getUrl() + "</b><br>");
            htmlText1.append("Description : <b>" + ajaxHandlerAction.getDescription() + "</b><br>");

            htmlText1.append("<br>");
            // htmlText1.append("<input type='button' id='addbutton' value='Accept' class='btn btn-primary'/>");
            //  htmlText1.append("<input type='button' id='addbutton' value='Reject' class='btn btn-primary'/>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td style='padding-top: 0px;' align='left' valign='top'>");
            htmlText1.append("<table class='textbutton' style='margin: 0;' align='left' border='0' cellpadding='0' cellspacing='0'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td align='justify' valign='top' style='margin: 0; padding-top: 5px; font-size:14px ; font-weight: normal; color:#000000; font-family: 'Open Sans'; line-height: 180%;  mso-line-height-rule: exactly;'>");

            htmlText1.append("<p style='text-align: justify; font-size: 14px;'><font color='#000000' face='trebuchet ms'>");
            htmlText1.append("<b>Thanks & Regards,</b><br/>");
            htmlText1.append("<b>Miracle TP On-boarding Team,</b><br/>");
            htmlText1.append("Miracle Software Systems, Inc.<br/>");
            htmlText1.append("<b> Email: </b>");
            htmlText1.append("<a href='mailto:mscvp_alerts@miraclesoft.com '>");
            htmlText1.append("mscvp_alerts@miraclesoft.com </a>");
            htmlText1.append("<br/>");
            htmlText1.append("<b>Phone: </b>");
            htmlText1.append("(+1)248-232-0224");
            htmlText1.append("</p>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td align='left' style='padding: 5px 0 5px 0; font-size: 14px; line-height: 22px; font-family: Open Sans; color: #ef4048; font-style: italic;' class='padding-copy'>");
            htmlText1.append("* Note: Please do not reply to this email as this is an automated notification");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");

            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td>");
            htmlText1.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText1.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText1.append("<p>");
            htmlText1.append("</p>");
            htmlText1.append("</div>");
            htmlText1.append("</div>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td height='5'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</div>");
            htmlText1.append("<div class='movableContent'>");
            htmlText1.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText1.append("<table width='655' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td colspan='3' height='20'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td width='90'>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='660' align='center' style='text-align: center;'>");
            htmlText1.append("<table width='660' cellpadding='0' cellspacing='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td>");
            htmlText1.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText1.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText1.append("<p style='text-align: center; font-size: 14px;'>");
            htmlText1.append("<font color='#ffffff' face='Open Sans'>");
            htmlText1.append(" ©Copyright 2016 Miracle Software Systems, Inc.<br>");
            htmlText1.append("45625 Grand River Avenue<br>");
            htmlText1.append("Novi, MI - USA");
            htmlText1.append("</font>");
            htmlText1.append("</p>");
            htmlText1.append("<font color='#ffffff' face='Open Sans'>");
            htmlText1.append("</font>");
            htmlText1.append("</div>");
            htmlText1.append("<font color='#ffffff' face='Open Sans'>");
            htmlText1.append("</font>");
            htmlText1.append("</div>");
            htmlText1.append("<font color='#ffffff' face='Open Sans'>");
            htmlText1.append("</font>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='90'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td colspan='3' height='20'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td width='195'>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='190' align='center' style='text-align: center;'>");
            htmlText1.append("<table width='190' cellpadding='0' cellspacing='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td width='40'>");
            htmlText1.append("<div class='contentEditableContainer contentFacebookEditable'>");
            htmlText1.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText1.append("<a href='https://www.facebook.com/miracle45625' target='_blank'>");
            htmlText1.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/fb.png' alt='facebook' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText1.append("</a>");
            htmlText1.append("</div>");
            htmlText1.append("</div>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='10'>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='40'>");
            htmlText1.append("<div class='contentEditableContainer contentTwitterEditable'>");
            htmlText1.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText1.append("<a href='https://twitter.com/team_mss' target='_blank'>");
            htmlText1.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/tweet.png' alt='twitter' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText1.append("</a>");
            htmlText1.append("</div>");
            htmlText1.append("</div>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='10'>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='40'>");
            htmlText1.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText1.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText1.append("<a href='https://plus.google.com/+Team_MSS/posts' target='_blank'>");
            htmlText1.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/googleplus.png' alt='Pinterest' width='32' height='32' data-max-width='40'>");
            htmlText1.append("</a>");
            htmlText1.append("</div>");
            htmlText1.append("</div>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='10'>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='40'>");
            htmlText1.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText1.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText1.append("<a href='https://www.linkedin.com/company/miracle-software-systems-inc' target='_blank'>");
            htmlText1.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/linkedin.png' alt='Social media' width='32' height='32' data-max-width='40'>");
            htmlText1.append("</a>");
            htmlText1.append("</div>");
            htmlText1.append("</div>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("<td width='195'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("<td colspan='3' height='40'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</div>");
            htmlText1.append("<div class='movableContent'>");
            htmlText1.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText1.append("<tbody>");
            htmlText1.append("<tr>");
            htmlText1.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='0'>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("<tr>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</div>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");
            htmlText1.append("</td>");
            htmlText1.append("</tr>");
            htmlText1.append("</tbody>");
            htmlText1.append("</table>");

            htmlText1.append("<span class='gr__tooltip'>");
            htmlText1.append("<span class='gr__tooltip-content'>");
            htmlText1.append("</span>");
            htmlText1.append("<i class='gr__tooltip-logo'>");
            htmlText1.append("</i>");
            htmlText1.append("<span class='gr__triangle'>");
            htmlText1.append("</span>");
            htmlText1.append("</span>");
            htmlText1.append("</body>");
            htmlText1.append("</html>");

            messageBodyPart1.setContent(htmlText1.toString(), "text/html");

            multipart1.addBodyPart(messageBodyPart1);

            message1.setContent(multipart1);
            transport1.connect();
            transport1.sendMessage(message1, message1.getRecipients(Message.RecipientType.TO));
            transport1.sendMessage(message1, message1.getRecipients(Message.RecipientType.CC));
            transport1.sendMessage(message1, message1.getRecipients(Message.RecipientType.BCC));
            transport1.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public static void tpoPartnerAddMail(String userloginId, String password, AjaxHandlerAction ajaxHandlerAction) {
        // SUBSTITUTE YOUR EMAIL ADDRESSES HERE!!!
        /**
         * The to is used for storing the user mail id to send details.
         */
        String to = ajaxHandlerAction.getEmail();
        /**
         * The from is used for storing the from address.
         */
        String from = "mscvp_alerts@miraclesoft.com";
        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
        /**
         * The host is used for storing the IP address of mail
         */
        /**
         * The props is instance variabel to <code>Properties</code> class
         */
        Properties props = new Properties();
        /**
         * Here set smtp protocal to props
         */
        props.setProperty("mail.transport.protocol", "smtp");
        //**Here set the address of the host to props */
        props.setProperty("mail.host", SMTP_HOST);
        props.put("mail.smtp.starttls.enable", "true");
        /**
         * Here set the authentication for the host *
         */
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", PORT);
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        mailSession.setDebug(true);
        Transport transport;
        try {
            transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Miracle Trading Partner Onboarding Password Details");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress("skunibilli@miraclesoft.com"));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("rpulleboina@miraclesoft.com"));

            // This HTML mail have to 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");
            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            StringBuilder htmlText = new StringBuilder();
            htmlText.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
            htmlText.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
            htmlText.append("<head>");
            htmlText.append("  <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
            htmlText.append("  <title>Partner Self Registration Details</title>");
            htmlText.append("  <style type='text/css'>");

            htmlText.append(" body {");
            htmlText.append("  padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   margin:0 !important;");
            htmlText.append("  width: 100% !important;");
            htmlText.append("  -webkit-text-size-adjust: 100% !important;");
            htmlText.append(" -ms-text-size-adjust: 100% !important;;");
            htmlText.append(" -webkit-font-smoothing: antialiased !important;");
            htmlText.append(" }");
            htmlText.append(" .tableContent img {");
            htmlText.append("   border: 0 !important;");
            htmlText.append("  display: block !important;");
            htmlText.append("   outline: none !important;");
            htmlText.append(" }");

            htmlText.append("a{");
            htmlText.append("color:#382F2E;");
            htmlText.append("}");

            htmlText.append("p, h1,h2,ul,ol,li,div{");
            htmlText.append("margin:0;");
            htmlText.append("padding:0;");
            htmlText.append("}");

            htmlText.append("h1,h2{");
            htmlText.append("font-weight: normal;");
            htmlText.append("  background:transparent !important;");
            htmlText.append("border:none !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.big,.contentEditable h1.big{");
            htmlText.append("  font-size: 26px !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.bigger,.contentEditable h1.bigger{");
            htmlText.append("font-size: 37px !important;");
            htmlText.append("}");

            htmlText.append("td,table{");
            htmlText.append("vertical-align: top;");
            htmlText.append("}");

            htmlText.append("td.middle{");
            htmlText.append("vertical-align: middle;");
            htmlText.append("}");

            htmlText.append(" a.link1{");
            htmlText.append("font-size:13px;");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("text-decoration:none;");
            htmlText.append("}");

            htmlText.append("a{");
            htmlText.append("text-decoration: none;");
            htmlText.append("}");

            htmlText.append(".link2{");
            htmlText.append("color:#fc3f3f;");
            htmlText.append("border-top:0px solid #fc3f3f;");
            htmlText.append("border-bottom:0px solid #fc3f3f;");
            htmlText.append("border-left:10px solid #fc3f3f;");
            htmlText.append("border-right:10px solid #fc3f3f;");
            htmlText.append("border-radius:1px;");
            htmlText.append("-moz-border-radius:5px;");
            htmlText.append("-webkit-border-radius:5px;");
            htmlText.append("background:#fc3f3f;");
            htmlText.append("}");

            htmlText.append(".link3{");
            htmlText.append("color:#555555;");
            htmlText.append("border:1px solid #cccccc;");
            htmlText.append("padding:10px 18px;");
            htmlText.append("border-radius:3px;");
            htmlText.append("-moz-border-radius:3px;");
            htmlText.append("-webkit-border-radius:3px;");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".link4{");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("}");

            htmlText.append("h2,h1{");
            htmlText.append("line-height: 20px;");
            htmlText.append("}");

            htmlText.append("p{");
            htmlText.append("font-size: 14px;");
            htmlText.append("line-height: 21px;");
            htmlText.append(" color:#AAAAAA;");
            htmlText.append("}");

            htmlText.append(".contentEditable li{");
            htmlText.append("}");

            htmlText.append(".appart p{");
            htmlText.append("}");

            htmlText.append(".bgItem{");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".bgBody{");
            htmlText.append("background: #0d416b;");
            htmlText.append("}");

            htmlText.append("img {");
            htmlText.append("outline:none;");
            htmlText.append("text-decoration:none;");
            htmlText.append("-ms-interpolation-mode: bicubic;");
            htmlText.append("width: auto;");
            htmlText.append("max-width: 100%;");
            htmlText.append("clear: both;");
            htmlText.append("display: block;");
            htmlText.append("float: none;");
            htmlText.append("}");
            htmlText.append("</style>");

            htmlText.append("<script type='colorScheme' class='swatch active'>");
            htmlText.append("{");
            htmlText.append("'name':'Default',");
            htmlText.append("'bgBody':'ffffff',");
            htmlText.append("'link':'27A1E5',");
            htmlText.append("'color':'AAAAAA',");
            htmlText.append("'bgItem':'ffffff',");
            htmlText.append("'title':'444444'");
            htmlText.append("}");

            htmlText.append("</script>");

            htmlText.append("</head>");
            htmlText.append("<body paddingwidth='0' paddingheight='0' bgcolor='#d1d3d4' style='padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;' offset='0' toppadding='0' leftpadding='0' data-gr-c-s-loaded='true'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='tableContent bgBody' align='center' style='font-family:Helvetica, sans-serif;'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='bgItem' align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='movableContentContainer' align='center'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable'>");
            htmlText.append("<a href='http://www.miraclesoft.com/index.php' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/logo.png' alt='Logo' height='45' data-default='placeholder' data-max-width='200'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;'>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;' width='150'>");
            htmlText.append("<br>");
            htmlText.append("<table width='300' border='0' cellpadding='0' cellspacing='0' align='right' style='text-align: right; font-size: 13px; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='fullCenter'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='55' valign='middle' width='100%' style='font-family: Open Sans; color:#232527;'>");
            htmlText.append("<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com/company/about-us.php' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'>");
            htmlText.append("Company");
            htmlText.append("</a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com/careers/' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'> Careers </a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='580' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='border: 0px solid #ffffff; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#00aae7; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:px'>");
            htmlText.append("<table width='630' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='15'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: left;'>");
            htmlText.append("<h2 style='font-size: 25px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("<b>Trading Partner Information </b>");
            htmlText.append("</font>");
            htmlText.append("</h2>");
            htmlText.append("<br>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("<b>Hello " + ajaxHandlerAction.getAddpartnerName() + ",</b>");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");

            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#232527' face='Open Sans'>");

            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' style='padding: 5px 0 5px 0; border-top: 1px #2368a0; border-bottom: 1px #2368a0; font-size: 14px; line-height: 25px; font-family: Open Sans; color: #232527;' class='padding-copy'>");
            htmlText.append("<b style='font-size: 14px; color: #ef4048;'>");

            htmlText.append("<p><u><b> Your Login Details :</b></u><br>");
            htmlText.append("LoginId : <b>" + userloginId + "</b><br>");
            htmlText.append("Password : <b>" + password + "</b><br>");

            htmlText.append("<br>");
            htmlText.append("</p><br>");

            htmlText.append("<p><u><b> Your Registration Details :</b></u><br><br>");
            htmlText.append("Partner Name : <b>" + ajaxHandlerAction.getAddpartnerName() + "</b><br>");
            htmlText.append("Phone No : <b>" + ajaxHandlerAction.getAddphoneNo() + "</b><br>");
            htmlText.append("Address : <b>" + ajaxHandlerAction.getAddaddress1() + "</b><br>");
            htmlText.append("City : <b>" + ajaxHandlerAction.getAddcity() + "</b><br>");
            htmlText.append("State : <b>" + ajaxHandlerAction.getAddstate() + "</b><br>");
            htmlText.append("Country : <b>" + ajaxHandlerAction.getAddcountry() + "</b><br>");
            htmlText.append("Zip-Code : <b>" + ajaxHandlerAction.getAddzipCode() + "</b><br>");
            htmlText.append("URL : <b>" + ajaxHandlerAction.getUrl() + "</b><br>");
            htmlText.append("Description : <b>" + ajaxHandlerAction.getDescription() + "</b><br>");

            htmlText.append("<br>");
            htmlText.append("</p></b></td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td style='padding-top: 0px;' align='left' valign='top'>");
            htmlText.append("<table class='textbutton' style='margin: 0;' align='left' border='0' cellpadding='0' cellspacing='0'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' valign='top' style='margin: 0; padding-top: 5px; font-size:14px ; font-weight: normal; color:#000000; font-family: 'Open Sans'; line-height: 180%;  mso-line-height-rule: exactly;'>");

            htmlText.append("<p style='text-align: justify; font-size: 14px;'><font color='#000000' face='trebuchet ms'>");
            htmlText.append("<b>Thanks & Regards,</b><br/>");
            htmlText.append("<b>Miracle TP On-boarding Team,</b><br/>");
            htmlText.append("Miracle Software Systems, Inc.<br/>");
            htmlText.append("<b> Email: </b>");
            htmlText.append("<a href='mailto:mscvp_alerts@miraclesoft.com '>");
            htmlText.append("mscvp_alerts@miraclesoft.com </a>");
            htmlText.append("<br/>");
            htmlText.append("<b>Phone: </b>");
            htmlText.append("(+1)248-232-0224");
            htmlText.append("</p>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td align='left' style='padding: 5px 0 5px 0; font-size: 14px; line-height: 22px; font-family: Open Sans; color: #ef4048; font-style: italic;' class='padding-copy'>");
            htmlText.append("* Note: Please do not reply to this email as this is an automated notification");
            htmlText.append("</td>");
            htmlText.append("</tr>");

            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='655' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("<td width='660' align='center' style='text-align: center;'>");
            htmlText.append("<table width='660' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<p style='text-align: center; font-size: 14px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append(" ©Copyright 2016 Miracle Software Systems, Inc.<br>");
            htmlText.append("45625 Grand River Avenue<br>");
            htmlText.append("Novi, MI - USA");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("<td width='190' align='center' style='text-align: center;'>");
            htmlText.append("<table width='190' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentFacebookEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.facebook.com/miracle45625' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/fb.png' alt='facebook' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentTwitterEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://twitter.com/team_mss' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/tweet.png' alt='twitter' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://plus.google.com/+Team_MSS/posts' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/googleplus.png' alt='Pinterest' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.linkedin.com/company/miracle-software-systems-inc' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/linkedin.png' alt='Social media' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='40'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");

            htmlText.append("<span class='gr__tooltip'>");
            htmlText.append("<span class='gr__tooltip-content'>");
            htmlText.append("</span>");
            htmlText.append("<i class='gr__tooltip-logo'>");
            htmlText.append("</i>");
            htmlText.append("<span class='gr__triangle'>");
            htmlText.append("</span>");
            htmlText.append("</span>");
            htmlText.append("</body>");
            htmlText.append("</html>");

            messageBodyPart.setContent(htmlText.toString(), "text/html");

            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            transport.connect();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.CC));
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.BCC));

            transport.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public static void tpoAcceptOrRejectPartner(String flag, String regContactName, String regPartName, String email, String loginId, String password) {
        // SUBSTITUTE YOUR EMAIL ADDRESSES HERE!!!
        /**
         * The to is used for storing the user mail id to send details.
         */
        System.out.println("regContactName " + regContactName + " regPartName " + regPartName + " email " + email + " loginId " + loginId + " password " + password);
        String to = email;
        /**
         * The from is used for storing the from address.
         */
        String from = "mscvp_alerts@miraclesoft.com";
        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
        /**
         * The host is used for storing the IP address of mail
         */
        /**
         * The props is instance variabel to <code>Properties</code> class
         */
        Properties props = new Properties();
        /**
         * Here set smtp protocal to props
         */
        props.setProperty("mail.transport.protocol", "smtp");
        //**Here set the address of the host to props */
        props.setProperty("mail.host", SMTP_HOST);
        props.put("mail.smtp.starttls.enable", "true");
        /**
         * Here set the authentication for the host *
         */
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", PORT);
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        mailSession.setDebug(true);
        Transport transport;
        try {
            transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Miracle Trading Partner Onboarding Password Details");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress("skunibilli@miraclesoft.com"));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("rpulleboina@miraclesoft.com"));
            // This HTML mail have to 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");
            // first part  (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            StringBuilder htmlText = new StringBuilder();

            htmlText.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
            htmlText.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
            htmlText.append("<head>");
            htmlText.append("  <meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
            htmlText.append("  <title>Your password has been reset successfully&#33;&#33;</title>");
            htmlText.append("  <style type='text/css'>");

            htmlText.append(" body {");
            htmlText.append("  padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   padding-top: 0 !important;");
            htmlText.append("  padding-bottom: 0 !important;");
            htmlText.append("   margin:0 !important;");
            htmlText.append("  width: 100% !important;");
            htmlText.append("  -webkit-text-size-adjust: 100% !important;");
            htmlText.append(" -ms-text-size-adjust: 100% !important;;");
            htmlText.append(" -webkit-font-smoothing: antialiased !important;");
            htmlText.append(" }");
            htmlText.append(" .tableContent img {");
            htmlText.append("   border: 0 !important;");
            htmlText.append("  display: block !important;");
            htmlText.append("   outline: none !important;");
            htmlText.append(" }");

            htmlText.append("a{");
            htmlText.append("color:#382F2E;");
            htmlText.append("}");

            htmlText.append("p, h1,h2,ul,ol,li,div{");
            htmlText.append("margin:0;");
            htmlText.append("padding:0;");
            htmlText.append("}");

            htmlText.append("h1,h2{");
            htmlText.append("font-weight: normal;");
            htmlText.append("  background:transparent !important;");
            htmlText.append("border:none !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.big,.contentEditable h1.big{");
            htmlText.append("  font-size: 26px !important;");
            htmlText.append("}");

            htmlText.append(".contentEditable h2.bigger,.contentEditable h1.bigger{");
            htmlText.append("font-size: 37px !important;");
            htmlText.append("}");

            htmlText.append("td,table{");
            htmlText.append("vertical-align: top;");
            htmlText.append("}");

            htmlText.append("td.middle{");
            htmlText.append("vertical-align: middle;");
            htmlText.append("}");

            htmlText.append(" a.link1{");
            htmlText.append("font-size:13px;");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("text-decoration:none;");
            htmlText.append("}");

            htmlText.append("a{");
            htmlText.append("text-decoration: none;");
            htmlText.append("}");

            htmlText.append(".link2{");
            htmlText.append("color:#fc3f3f;");
            htmlText.append("border-top:0px solid #fc3f3f;");
            htmlText.append("border-bottom:0px solid #fc3f3f;");
            htmlText.append("border-left:10px solid #fc3f3f;");
            htmlText.append("border-right:10px solid #fc3f3f;");
            htmlText.append("border-radius:1px;");
            htmlText.append("-moz-border-radius:5px;");
            htmlText.append("-webkit-border-radius:5px;");
            htmlText.append("background:#fc3f3f;");
            htmlText.append("}");

            htmlText.append(".link3{");
            htmlText.append("color:#555555;");
            htmlText.append("border:1px solid #cccccc;");
            htmlText.append("padding:10px 18px;");
            htmlText.append("border-radius:3px;");
            htmlText.append("-moz-border-radius:3px;");
            htmlText.append("-webkit-border-radius:3px;");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".link4{");
            htmlText.append("color:#27A1E5;");
            htmlText.append("line-height: 24px;");
            htmlText.append("}");

            htmlText.append("h2,h1{");
            htmlText.append("line-height: 20px;");
            htmlText.append("}");

            htmlText.append("p{");
            htmlText.append("font-size: 14px;");
            htmlText.append("line-height: 21px;");
            htmlText.append(" color:#AAAAAA;");
            htmlText.append("}");

            htmlText.append(".contentEditable li{");
            htmlText.append("}");

            htmlText.append(".appart p{");
            htmlText.append("}");

            htmlText.append(".bgItem{");
            htmlText.append("background:#ffffff;");
            htmlText.append("}");

            htmlText.append(".bgBody{");
            htmlText.append("background: #0d416b;");
            htmlText.append("}");

            htmlText.append("img {");
            htmlText.append("outline:none;");
            htmlText.append("text-decoration:none;");
            htmlText.append("-ms-interpolation-mode: bicubic;");
            htmlText.append("width: auto;");
            htmlText.append("max-width: 100%;");
            htmlText.append("clear: both;");
            htmlText.append("display: block;");
            htmlText.append("float: none;");
            htmlText.append("}");
            htmlText.append("</style>");

            htmlText.append("<script type='colorScheme' class='swatch active'>");
            htmlText.append("{");
            htmlText.append("'name':'Default',");
            htmlText.append("'bgBody':'ffffff',");
            htmlText.append("'link':'27A1E5',");
            htmlText.append("'color':'AAAAAA',");
            htmlText.append("'bgItem':'ffffff',");
            htmlText.append("'title':'444444'");
            htmlText.append("}");

            htmlText.append("</script>");

            htmlText.append("</head>");
            htmlText.append("<body paddingwidth='0' paddingheight='0' bgcolor='#d1d3d4' style='padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;' offset='0' toppadding='0' leftpadding='0' data-gr-c-s-loaded='true'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' class='tableContent bgBody' align='center' style='font-family:Helvetica, sans-serif;'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='bgItem' align='center'>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td class='movableContentContainer' align='center'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable'>");
            htmlText.append("<a href='http://www.miraclesoft.com/index.php' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/logo.png' alt='Logo' height='45' data-default='placeholder' data-max-width='200'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;'>");
            htmlText.append("</td>");
            htmlText.append("<td valign='middle' style='vertical-align: middle;' width='150'>");
            htmlText.append("<br>");
            htmlText.append("<table width='300' border='0' cellpadding='0' cellspacing='0' align='right' style='text-align: right; font-size: 13px; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;' class='fullCenter'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='55' valign='middle' width='100%' style='font-family: Open Sans; color:#232527;'>");
            htmlText.append("<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'>");
            htmlText.append("Company");
            htmlText.append("</a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<span style='font-family: 'proxima_nova_rgregular', Open Sans; font-weight: normal;'>");
            htmlText.append("<a href='http://www.miraclesoft.com/careers/' target='_blank' style='text-decoration: none; color:#ffffff;' class='underline'> Careers </a>");
            htmlText.append("</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='580' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='border: 0px solid #ffffff; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#00aae7; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:px'>");
            htmlText.append("<table width='630' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='15'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: left;'>");
            htmlText.append("<h2 style='font-size: 25px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("<b>User Account Created</b>");
            htmlText.append("</font>");
            htmlText.append("</h2>");
            htmlText.append("<br>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");
            htmlText.append("<p>Hello " + regPartName + "</p>");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");

            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<br>");
            htmlText.append("<p style='line-height:180%; text-align: justify; font-size: 14px;'>");
            htmlText.append("<font color='#232527' face='Open Sans'>");

            if ("acceptPartner".equals(flag)) {
                htmlText.append("Welcome to Miracle's Trading Partner On-boarding Portal! Your account has been activated with the following credentials. Please login and change your password within 48 hours of this email. ");
            } else if ("rejectPartner".equals(flag)) {
                htmlText.append("Welcome to Miracle's Trading Partner On-boarding Portal! Your account has been rejected. Please contact admin for details.");
            }

            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");

            if ("acceptPartner".equals(flag)) {
                htmlText.append("<tr>");
                htmlText.append("<td align='justify' style='padding: 5px 0 5px 0; border-top: 1px #2368a0; border-bottom: 1px #2368a0; font-size: 14px; line-height: 25px; font-family: Open Sans; color: #232527;' class='padding-copy'>");
                htmlText.append("<b style='font-size: 14px; color: #ef4048;'>");
                htmlText.append("Login Id: " + loginId + "</b>");
                htmlText.append("<br>");
                htmlText.append("<b style='font-size: 14px; color: #ef4048;'>");
                htmlText.append("Password: " + password + "</b>");
                htmlText.append("<br>");
                htmlText.append("</td>");
                htmlText.append("</tr>");
            }

            htmlText.append("<tr><td align='center' valign='middle' style='color:#FFFFFF; font-family:Helvetica, Arial, sans-serif; font-size:16px; font-weight:bold; letter-spacing:-.5px; line-height:150%; padding-top:5px; padding-right:20px; padding-bottom:5px; padding-left:20px;'>");
            htmlText.append("<a href='http://192.168.1.179:8084/tpo' target='_blank' style='color:#FFFFFF; text-decoration:none;'>Click here to login</a></td></tr></table><br><br>");
            htmlText.append("<tr>");
            htmlText.append("<td style='padding-top: 0px;' align='left' valign='top'>");
            htmlText.append("<table class='textbutton' style='margin: 0;' align='left' border='0' cellpadding='0' cellspacing='0'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td align='justify' valign='top' style='margin: 0; padding-top: 5px; font-size:14px ; font-weight: normal; color:#000000; font-family: 'Open Sans'; line-height: 180%;  mso-line-height-rule: exactly;'>");
            htmlText.append("<p style='text-align: justify; font-size: 14px;'><font color='#000000' face='trebuchet ms'>");
            htmlText.append("<b>With Thank you,</b><br/>");
            htmlText.append("<b>Miracle TP On-boarding Team,</b><br/>");
            htmlText.append("Miracle Software Systems, Inc.<br/>");
            htmlText.append("<b> Email: </b>");
            htmlText.append("<a href='mailto:mscvp_alerts@miraclesoft.com '>");
            htmlText.append("mscvp_alerts@miraclesoft.com </a>");
            htmlText.append("<br/>");
            htmlText.append("<b>Phone: </b>");
            htmlText.append("(+1)248-232-0224");
            htmlText.append("</p>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");

            htmlText.append("<tr>");
            htmlText.append("<td align='left' style='padding: 5px 0 5px 0; font-size: 14px; line-height: 22px; font-family: Open Sans; color: #ef4048; font-style: italic;' class='padding-copy'>");
            htmlText.append("* Note: Please do not reply to this email as this is an automated notification");
            htmlText.append("</td>");
            htmlText.append("</tr>");

            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='600' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;'>");
            htmlText.append("<p>");
            htmlText.append("</p>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td height='5'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='660' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px'>");
            htmlText.append("<table width='655' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("<td width='660' align='center' style='text-align: center;'>");
            htmlText.append("<table width='660' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td>");
            htmlText.append("<div class='contentEditableContainer contentTextEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<p style='text-align: center; font-size: 14px;'>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append(" ©Copyright 2016 Miracle Software Systems, Inc.<br>");
            htmlText.append("45625 Grand River Avenue<br>");
            htmlText.append("Novi, MI - USA");
            htmlText.append("</font>");
            htmlText.append("</p>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</div>");
            htmlText.append("<font color='#ffffff' face='Open Sans'>");
            htmlText.append("</font>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='90'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("<table width='650' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='20'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("<td width='190' align='center' style='text-align: center;'>");
            htmlText.append("<table width='190' cellpadding='0' cellspacing='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentFacebookEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.facebook.com/miracle45625' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/fb.png' alt='facebook' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentTwitterEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://twitter.com/team_mss' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/tweet.png' alt='twitter' width='32' height='32' data-max-width='40' data-customicon='true'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://plus.google.com/+Team_MSS/posts' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/googleplus.png' alt='Pinterest' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("<td width='10'>");
            htmlText.append("</td>");
            htmlText.append("<td width='40'>");
            htmlText.append("<div class='contentEditableContainer contentImageEditable'>");
            htmlText.append("<div class='contentEditable' style='text-align: center;color:#AAAAAA;'>");
            htmlText.append("<a href='https://www.linkedin.com/company/miracle-software-systems-inc' target='_blank'>");
            htmlText.append("<img src='http://www.miraclesoft.com/newsletters/others/invite_interconnect_2015/images/linkedin.png' alt='Social media' width='32' height='32' data-max-width='40'>");
            htmlText.append("</a>");
            htmlText.append("</div>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("<td width='195'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("<td colspan='3' height='40'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("<div class='movableContent'>");
            htmlText.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' align='center'>");
            htmlText.append("<tbody>");
            htmlText.append("<tr>");
            htmlText.append("<td style='background:#0d416b; border-radius:0px;-moz-border-radius:0px;-webkit-border-radius:0px' height='0'>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("<tr>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</div>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");
            htmlText.append("</td>");
            htmlText.append("</tr>");
            htmlText.append("</tbody>");
            htmlText.append("</table>");

            htmlText.append("<span class='gr__tooltip'>");
            htmlText.append("<span class='gr__tooltip-content'>");
            htmlText.append("</span>");
            htmlText.append("<i class='gr__tooltip-logo'>");
            htmlText.append("</i>");
            htmlText.append("<span class='gr__triangle'>");
            htmlText.append("</span>");
            htmlText.append("</span>");
            htmlText.append("</body>");
            htmlText.append("</html>");

//            htmlText.append("<html><head><title>Mail From Miracle Trading Partner Onboarding Portal</title>");
//            htmlText.append("</head><body><font color='blue' size='2' face='Arial'>");
//            htmlText.append("<p>Hello " + regContactName + "</p>");
//            htmlText.append("<p><u><b>Your Login Details :</b></u><br><br>");
//            htmlText.append("Login Id : <b>" + loginId + "</b><br>");
//            htmlText.append("Password : <b>" + password + "</b><br><br>");
//            htmlText.append("<table border='0' cellpadding='0' cellspacing='0' style='background-color:#338dff; border:1px solid #353535; border-radius:5px;'>");
//            htmlText.append("<tr><td align='center' valign='middle' style='color:#FFFFFF; font-family:Helvetica, Arial, sans-serif; font-size:16px; font-weight:bold; letter-spacing:-.5px; line-height:150%; padding-top:5px; padding-right:20px; padding-bottom:5px; padding-left:20px;'>");
//            htmlText.append("<a href='http://192.168.1.179:8084/tpo' target='_blank' style='color:#FFFFFF; text-decoration:none;'>Click here to login</a></td></tr></table><br><br>");
//            htmlText.append("<b>Please Note:</b><br>");
//            htmlText.append("To better protect ");
//            htmlText.append("your account, make sure that your password is memorable ");
//            htmlText.append("for you but difficult for others to guess. Never ");
//            htmlText.append("use the same password that you have used in the past,");
//            htmlText.append(" and do not share your password with anyone.<br></br><br>");
//            htmlText.append("<b>Regards,</b><br>");
//            htmlText.append("Miracle Trading Partner Onboarding Portal Team,</p></font><br>");
//            htmlText.append("<font color='red', size='2' face='Arial'>*Note:Please do not reply to this e-mail.  It was generated by our System.</font>");
//            htmlText.append("</body></html>");
            messageBodyPart.setContent(htmlText.toString(), "text/html");

            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            transport.connect();
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.CC));
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.BCC));
            transport.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }
}
