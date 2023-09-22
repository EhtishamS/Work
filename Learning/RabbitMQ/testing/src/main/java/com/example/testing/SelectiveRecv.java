/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.testing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SelectiveRecv {  
    private static final String EXCHANGE_NAME = "DELETE_MESSAGE";
    
    public static void main(String[] argv) throws Exception {
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
            
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8080/anagrafica/"+id);
            ClientResponse response = webResource.accept("application/json").delete(ClientResponse.class);
            
            if(response.getStatus() == 204){
                throw new RuntimeException("Resourse does not exist!");
            } else if(response.getStatus() != 200){
                throw new RuntimeException("Failed: HTTP error code : " + response.getStatus());
            }
            
            String output = response.getEntity(String.class);
            
            System.out.println("Output from server ... \n");
            System.out.println(output);
            
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> { });
    }   
}
