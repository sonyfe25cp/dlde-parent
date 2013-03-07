package edu.bit.dlde.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送普通邮件，接受普通邮件 发送带有附件的邮件，接收带有附件的邮件 发送html形式的邮件，接受html形式的邮件 发送带有图片的邮件等做了一个总结。
 */
public class EmailSender {
	private DLDELogger logger=new DLDELogger();
	// 邮箱服务器
	private String host = "smtp.163.com";
	// 这个是你的邮箱用户名
	private String username = "";
	// 你的邮箱密码
	private String password = "";

	private String mail_head_name = "this is head of this mail";

	private String mail_head_value = "this is head of this mail";

	private String mail_to = "";

	private String mail_from = "";

	private String mail_subject = "this is the subject of this test mail";

	private String mail_body = "this is the mail_body of this test mail";

	//显示在发件人那地方的名字
	private String personalName = "我的邮件";

	public EmailSender() {
		
	}
	

	public EmailSender(String host, String username, String password) {
		this();
		this.host = host;
		this.username = username;
		this.password = password;
	}
	
	public EmailSender(String username, String password) {
		this();
		logger.info("默认使用@163.com邮件服务器");
		this.username = username;
		this.password = password;
	}


	//邮件发送前的信息检查
	private void check(){
		if(username.length()==0||password.length()==0){
			logger.error("用户名密码为空");
		}
		if(mail_to.length()==0||mail_from.length()==0){
			logger.error("发件人or收件人为空");
		}
	}


	/**
	 * 此段代码用来发送普通电子邮件
	 */
	public void send() throws Exception {
		try {
			Properties props = new Properties(); // 获取系统环境
			Authenticator auth = new Email_Autherticator(); // 进行邮件服务器用户认证
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, auth);
			// 设置session,和邮件服务器进行通讯。
			MimeMessage message = new MimeMessage(session);
			// message.setContent("foobar, "application/x-foobar"); // 设置邮件格式
			message.setSubject(mail_subject); // 设置邮件主题
			message.setText(mail_body); // 设置邮件正文
			message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题
			message.setSentDate(new Date()); // 设置邮件发送日期
			Address address = new InternetAddress(mail_from, personalName);
			message.setFrom(address); // 设置邮件发送者的地址
			Address toAddress = new InternetAddress(mail_to); // 设置邮件接收方的地址
			message.addRecipient(Message.RecipientType.TO, toAddress);
			Transport.send(message); // 发送邮件
			System.out.println("send ok!");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	class CheckException extends Exception{
		
	}

	/**
	 * 用来进行服务器对用户的认证
	 */
	public class Email_Autherticator extends Authenticator {
		public Email_Autherticator() {
			super();
		}

		public Email_Autherticator(String user, String pwd) {
			super();
			username = user;
			password = pwd;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	public static void main(String[] args) {
		EmailSender sendmail = new EmailSender();
		try {
			sendmail.send();
		} catch (Exception ex) {
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail_head_name() {
		return mail_head_name;
	}

	public void setMail_head_name(String mail_head_name) {
		this.mail_head_name = mail_head_name;
	}

	public String getMail_head_value() {
		return mail_head_value;
	}

	public void setMail_head_value(String mail_head_value) {
		this.mail_head_value = mail_head_value;
	}

	public String getMail_to() {
		return mail_to;
	}

	public void setMail_to(String mail_to) {
		this.mail_to = mail_to;
	}

	public String getMail_from() {
		return mail_from;
	}

	public void setMail_from(String mail_from) {
		this.mail_from = mail_from;
	}

	public String getMail_subject() {
		return mail_subject;
	}

	public void setMail_subject(String mail_subject) {
		this.mail_subject = mail_subject;
	}

	public String getMail_body() {
		return mail_body;
	}

	public void setMail_body(String mail_body) {
		this.mail_body = mail_body;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

}