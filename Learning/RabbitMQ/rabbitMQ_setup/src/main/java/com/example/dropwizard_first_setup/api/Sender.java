/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dropwizard_first_setup.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Data;

/**
 *
 * @author e.sarwar
 */
@Data
public class Sender {
    private static final String EXCHANGE_NAME = "DELETE_MESSAGE";
    
    public void send(String host, String message) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String severity = "anagrafica.reset";

            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
        }
    }
}
