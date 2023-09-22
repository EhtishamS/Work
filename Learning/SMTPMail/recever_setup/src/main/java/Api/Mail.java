/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Api;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
    private String sender;
    private String recever;
    private String password;
    private String host;
    private String port;
    private Properties properties;
    private Session session;
    
    public Mail(String recever ,String sender, String sender_password, String host, String port){
        this.recever = recever;
        this.sender = sender;
        this.password = sender_password;
        this.host = host;
        this.port = port;
        
        properties = new Properties();
        
        properties.put("mail.smtp.host", this.host);
        properties.put("mail.smtp.port", this.port);
        
        if (this.password != null){
            properties.put("mail.smtp.auth", "true");
            
            session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });
            
        } else {
            properties.put("mail.smtp.auth", "falso");
            session = Session.getDefaultInstance(properties);
        }
    }
    
    public void send(String subject, String body){
        try {  
            MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress(sender));  
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(recever));  
            message.setSubject(subject);  
            message.setText(body);  

            //send the message  
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
