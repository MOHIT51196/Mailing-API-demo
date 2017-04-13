
import java.util.Properties;


import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.swing.JOptionPane;

public class Mailing {

	private static final String host = "smtp.gmail.com";
	private static final String port = "465";
	private static final  String username = "mohitmalhotraking@gmail.com";
	private static String passcode ;
	private static String recipient;
	
	/*
	 * For AuthenticationFailed Error Resolution
	 * Go to the link below
	 * link :  support.google.com/accounts/answer/6010255 google.com/settings/security/lesssecureapps 
	 * Activate less secure app support
	 */
	
	public static void main(String[] args){
	
		
		final String success_msg = " Mail sent Successfully !";
		final String unsuccess_msg = " Mail sending Unsuccessfull \n Check your Internet Connection";
		
		//Feeding required properties to the property Object
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.host", "smtp.gmail.com");    
        properties.put("mail.smtp.socketFactory.port", "465");    
        properties.put("mail.smtp.socketFactory.class",    "javax.net.ssl.SSLSocketFactory"); 
		//properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.auth", true);
		
		
		
		//Session object is formed to manipulate the smtp session
		Session session = Session.getInstance(properties, new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(username, passcode);
			}
		});
		
		if( sendEmail(prepareEmail(session)) ){
			JOptionPane.showMessageDialog(null, success_msg);
		}
		else{
			JOptionPane.showMessageDialog(null, unsuccess_msg);
		}
		
	}
	
	private static void getUserInput(String subject, String content){
		recipient = JOptionPane.showInputDialog("Enter the Recipient Mail ");
		subject = JOptionPane.showInputDialog("Enter the Subject for the mail");
		content = JOptionPane.showInputDialog("Enter the message ");
		passcode = JOptionPane.showInputDialog("Enter your Gmail PASSWORD ");
		
	}
	
	private static Message prepareEmail(Session session){
		
		Message message = new MimeMessage(session);
		String subject = "" ;
		String mailText = "";
		
		getUserInput(subject, mailText);
		System.out.println("Processing the request..........");
		try {
			message.setFrom(new InternetAddress(username));
			
			message.addRecipients(RecipientType.TO, InternetAddress.parse(recipient));
			
			message.setSubject(subject);
			
			message.setText(mailText);
				
		}
		
		
		catch (MessagingException msg_err) {
			
			JOptionPane.showMessageDialog(null, "Required Fields must be filled");
			
			System.out.println("\n\n===========ERROR PREPARING THE MAIL==========\n");
			msg_err.printStackTrace();
		}
		
		return message;
	}
	
	
	private static boolean sendEmail(Message message){
		
		boolean isSend = false;
		
		try {
			Transport.send(message);
			isSend = true;
		} 
		
		catch(AuthenticationFailedException auth_err){
			
			JOptionPane.showMessageDialog(null, "Oops ! Password is Incorrect");
			
			System.out.println("\n\n===========ERROR In AUTHENTICATION==========\n");
			auth_err.printStackTrace();
		}
		catch (MessagingException msg_err) {
			
			JOptionPane.showMessageDialog(null, "Email not sent \nCheck Your Internet Connection");
			
			System.out.println("\n\n===========ERROR SENDING THE MAIL==========\n");
			msg_err.printStackTrace();
		}
		
		return  isSend;
	}
}
