/**
 * ファイル名: Smtp.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */
package jp.co.kcs_grp.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;

public class Smtp
{
    /** 特性 */
    private Properties prop;

    /**
     * デフォルト特性を得ます
     *
     * @return デフォルト特性
     */
    private Properties getDefaultProperties() {
        Properties defaultProp = new Properties();
        return defaultProp;
    }

    /**
     * セット・ホスト
     *
     * @param host ホスト
     */
    public void setSmtpHost(String host) {
        if (this.prop == null) {
            this.prop = new Properties();
        }
        this.prop.put("mail.smtp.host", host);
    }

    /**
     * 送られたメイル
     * @param from から
     * @param to に
     * @param text テキスト
     * @param subject 主題
     */
    public void sendmail(String from, String to, String text, String subject) throws Exception {
        sendmail(from, to, null, text, subject);
    }
    public void sendmail(String from, String to, String bcc, String text, String subject) throws Exception {
        String mailText="";

        if (this.prop == null) {
            this.prop = getDefaultProperties();
        }
        this.prop.put("mail.debug", "true");
 //       this.prop.put("mail.smtp.auth", "true");
 //       this.prop.put("mail.smtp.starttls.enable","true");
 //       this.prop.put("mail.smtp.EnableSSL.enable","true");

        try {
        	Map<String, String> configMap = AppParams.getMailProp();
        	this.prop.put("mail.smtp.host", configMap.get("smtpHost"));
        	this.prop.put("mail.smtp.port", configMap.get("smtpPort"));
            javax.mail.Session s = javax.mail.Session.getInstance(this.prop, new javax.mail.Authenticator() {
	    	    @Override
	            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
	                return new javax.mail.PasswordAuthentication( configMap.get("SYSTEM_MAIL") , configMap.get("SYSTEM_MAIL_PASSWORD"));
	            }
	       });

            javax.mail.internet.MimeMessage msg;
            msg = new javax.mail.internet.MimeMessage(s);
            msg.setHeader("Content-Transfer-Encoding", "7bit");
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to));
            if (StringUtils.isNotBlank(bcc)) {
                msg.setRecipients(javax.mail.Message.RecipientType.BCC, InternetAddress.parse(bcc));
            }
            msg.setSubject(subject, "iso-2022-jp");

            mailText = convMailText(text);
            msg.setText(mailText, "iso-2022-jp");

            javax.mail.Transport.send(msg);
        }

        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return;
    }
    public void sendmailThrowE(String from, String to, String text, String subject) throws Exception{
        String mailText="";

        if (this.prop == null) {
            this.prop = getDefaultProperties();
        }
        this.prop.put("mail.debug", "true");
        try {
            javax.mail.Session s = javax.mail.Session.getInstance(this.prop, null);
            javax.mail.internet.MimeMessage msg;
            msg = new javax.mail.internet.MimeMessage(s);
            msg.setHeader("Content-Transfer-Encoding", "7bit");
            msg.setFrom(new javax.mail.internet.InternetAddress(from));
            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject, "iso-2022-jp");
            mailText = convMailText(text);
            msg.setText(mailText, "iso-2022-jp");

            javax.mail.Transport.send(msg);
        }

        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return;
    }
    //2020-03-11 add  aws環境メール送信
    public void sendmail2(String from, String to, String bcc, String text, String subject) throws Exception {
        String mailText="";

        if (this.prop == null) {
            this.prop = getDefaultProperties();
        }
        this.prop.put("mail.debug", "true");
 //       this.prop.put("mail.smtp.auth", "true");
 //       this.prop.put("mail.smtp.starttls.enable","true");
 //       this.prop.put("mail.smtp.EnableSSL.enable","true");

        try {
        	Map<String, String> configMap = AppParams.getMailProp();
        	javax.mail.Session s = javax.mail.Session.getInstance(this.prop, new javax.mail.Authenticator() {
            	@Override
            	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            		return new javax.mail.PasswordAuthentication( "system@orlsj.jp" , "");
            	}
            });

            javax.mail.internet.MimeMessage msg;
            msg = new javax.mail.internet.MimeMessage(s);
            msg.setHeader("Content-Transfer-Encoding", "7bit");
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(to));
            if (StringUtils.isNotBlank(bcc)) {
                msg.setRecipients(javax.mail.Message.RecipientType.BCC, InternetAddress.parse(bcc));
            }
            msg.setSubject(subject, "iso-2022-jp");

            mailText = convMailText(text);
            msg.setText(mailText, "iso-2022-jp");

            javax.mail.Transport.send(msg);
        }

        catch (Exception ex) {
       	StringWriter stack = new StringWriter();
            ex.printStackTrace(new PrintWriter(stack));
            throw ex;
        }
        return;
    }

    /**
     * 文字化け対応："～￣―∥－￠￡￢"
     * 機種依存文字は対応外
     * @param text
     * @return
     */
    private String convMailText(String text) {
        String strText="";
        strText = text.replace('～', '\u301C');
        strText = strText.replace('￣', '\uffe3');
        strText = strText.replace('―', '\u2014');
        strText = strText.replace('∥', '\u2016');
        strText = strText.replace('－', '\u2212');
        strText = strText.replace('￠', '\u00a2');
        strText = strText.replace('￡', '\u00a3');
        strText = strText.replace('￢', '\u00ac');
        return strText;
    }
}
