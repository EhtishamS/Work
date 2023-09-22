/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NotifyRecever;

import Api.Mail;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 *
 * @author e.sarwar
 */
public class SelectiveRecv {
    private static final String EXCHANGE_NAME = "NOTIFY_MESSAGE";
    
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, "anagrafica.reset");
            
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            
            String id = message.substring(7, message.length()-1);
            
            Mail mail = new Mail("e.sarwar@abacogroup.eu", "no-reply@abacofarmer.com", "AbacoFarMlP2021!", "smtp.siti4farmer.eu", "587");
            
            mail.send("DELETE REQUEST", "The request for deleting the id " + id + " has been submitted succesfully!");
            
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> { });
    }
}
