package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class EmailSender {

	Logger logger = Logger.getLogger(EmailSender.class);
	// 邮箱服务器
	private String host = "mail.bit.edu.cn";
	// 这个是你的邮箱用户名
	private String username = "";
	// 你的邮箱密码
	private String password = "";

	private String mail_head_name = "文件头";

	private String mail_head_value = "文件头值";

	private String mail_to = "";

	private String mail_from = "40711087@bit.edu.cn";

	private String mail_subject = "";

	private String mail_body = "this is the mail_body of this test mail";

	// 显示在发件人那地方的名字
	private String personalName = "北理工NoPrinter打印铺";

	public EmailSender() {
		this.username="40711087@bit.edu.cn";
		this.password="nvidia7600";
	}
	public EmailSender(String server){
		if(server.equals("126")){
			this.username = "noprinter@126.com";
			this.password = "omartech@406";
			this.host = "smtp.126.com";
			this.mail_from ="noprinter@126.com";
		}else if(server.equals("bit")){
			this.username="40711087@bit.edu.cn";
			this.password="nvidia7600";	
			this.host = "mail.bit.edu.cn";
			this.mail_from ="40711087@bit.edu.cn";
		}else if(server.equals("noprinter")){
			this.username = "admin@noprinter.cn";
			this.password = "85136984";
			this.host = "smtp.noprinter.cn";
			this.mail_from = "admin@noprinter.cn";
		}else if(server.equals("qq")){
			this.username = "2536151131@qq.com";
			this.password = "omartech@406";
			this.host = "smtp.qq.com";
			this.mail_from = "2536151131@qq.com";
		}
	}

	public void sendEmail(String mailTo, String title, String body){
		this.setMail_to(mailTo);
		this.setMail_subject(title);
		this.setMail_body(body);
		try {
			send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public static String folderPath = "/home/coder/git/dlde-parent/dlde-commons/src/test/resources/";
	public static String folderPath = "/home/coder/Desktop/shareToWindows/noprinter/";
	public static void main(String[] args) throws Exception {
//		creatEmail(folderPath);
//		testEmails();
		groupSend();
		
//		testEmail();
		
	
	}
	public static void groupSend() throws Exception{
		EmailSender sendmail = new EmailSender("qq");
		File folder = new File(folderPath);
		File[] files = folder.listFiles();
		for(File file : files){
			String fileName = file.getName();
			if(!fileName.endsWith(".number")){
				continue;
			}
			List<String> emails = getEmails(file);
			for(String email : emails){
				System.out.println(email);
				String mail_title = "NoPrinter在北理工有实体店啦！！职消超市2楼C6！！";
				String mail_body = makeContent();
				sendmail.sendEmail(email, mail_title, mail_body);
//				Thread.sleep(500);
			}
		}
	}
	
	public static void testEmail(){
		EmailSender sendmail = new EmailSender("qq");
		try {
//			String mail_to = "sonyfe25cp@gmail.com";
			String mail_to = "284198757@qq.com";
//			String mail_to = "3120100382@bit.edu.cn";
			String mail_title = "NoPrinter在北理工有实体店啦！！职消超市2楼C6！！";
			String mail_body = makeContent();
//			System.out.println("1:"+mail_body);
			sendmail.sendEmail(mail_to, mail_title, mail_body);
		} catch (Exception ex) {
		}
	}
	public static void testEmails() throws Exception{
		String folderPath = "/home/coder/git/dlde-parent/dlde-commons/src/test/resources/";
		File folder = new File(folderPath);
		File[] files = folder.listFiles();
		for(File file : files){
			String fileName = file.getName();
			if(!fileName.endsWith(".number")){
				continue;
			}
			List<String> emails = getEmails(file);
			for(String email : emails){
				System.out.println(email);
			}
		}
	}
	public static List<String> getEmails(File file) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<String> emails = new ArrayList<String>();
		String line = br.readLine();
		while(line!=null){
			emails.add(line);
			line = br.readLine();
		}
		br.close();
		return emails;
	}
	
	public static void creatEmail(String folderPath) throws IOException{
		File folder = new File(folderPath);
		File[] files = folder.listFiles();
		for(File file : files){
			String fileName = file.getName();
			if(!fileName.startsWith("qq-")){
				continue;
			}
			System.out.println(fileName+" is ok");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			String str ="";
			while(line!=null){
				str += line;
				line = br.readLine();
			}
			br.close();
			
			String regex = "nameCard_[0-9]+";  
		    Pattern p = Pattern.compile(regex);  
		    Matcher m = p.matcher(str);
		    List<String> emails = new ArrayList<String>();
		    while (m.find()){
		        String val = m.group();
		        String qq = val.substring(9);
		        String mail = qq+"@qq.com";
		        emails.add(mail);
//		        System.out.println(mail);
		    }
		    String newFileName = fileName+".number";
		    if(emails.size() == 0 ){
		    	continue;
		    }
		    FileWriter fw = new FileWriter(new File(folderPath+newFileName));
		    for(String email:emails){
		    	fw.write(email);
		    	fw.write("\n");
		    }
		    fw.flush();
		    fw.close();
		}
	}
	
	
	public static String makeContent() throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(new File("/home/coder/git/dlde-parent/dlde-commons/src/test/resources/market.html")));
		String line = br.readLine();
		String str ="";
		while(line!=null){
			str += line;
			line = br.readLine();
		}
		br.close();
		return str;
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
			

			message.setSubject(mail_subject); // 设置邮件主题
			message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题
			message.setSentDate(new Date()); // 设置邮件发送日期
			Address address = new InternetAddress(mail_from, personalName);
			message.setFrom(address); // 设置邮件发送者的地址
			Address toAddress = new InternetAddress(mail_to); // 设置邮件接收方的地址
			message.addRecipient(Message.RecipientType.TO, toAddress);

			Multipart mp = new MimeMultipart("related");// related意味着可以发送html格式的邮件
			BodyPart bodyPart = new MimeBodyPart();// 正文
			bodyPart.setDataHandler(new DataHandler(mail_body,"text/html;charset=utf8"));// 网页格式
			mp.addBodyPart(bodyPart);
			message.setContent(mp, "text/html;charset = gbk");  
			message.saveChanges();  
			
			Transport.send(message); // 发送邮件
			System.out.println("send email to "+mail_to+" ok!");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
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
