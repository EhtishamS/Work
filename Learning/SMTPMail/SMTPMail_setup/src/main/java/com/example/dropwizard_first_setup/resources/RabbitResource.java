/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dropwizard_first_setup.resources;

import com.example.dropwizard_first_setup.api.Sender;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/anagrafica_rmq")
public class RabbitResource {
    private Sender sender;
    
    public RabbitResource(Sender sender){
        this.sender = sender;
    }
    
    @GET
    @Path("/reset/{ID}")
    public Response reset(@PathParam("ID") long id) throws Exception{
        sender.send("localhost", "{\"id\": " + id + "}");
        
        return Response.ok().build();
    }
    
}
