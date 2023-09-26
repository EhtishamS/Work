package com.example.dropwizard_first_setup.resources;


import com.abacogroup.embeddedqueue.WorkQueue;
import com.example.dropwizard_first_setup.api.DemetraAdoption;
import com.example.dropwizard_first_setup.api.MultipleDemetraAdoption;
import com.example.dropwizard_first_setup.db.MyDAO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 *
 * Created by Ehtisham Sarwar on 07/09/2023
 */

@Path("/massive_adoption") // This tells Jersey that the application is available on this URL
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON) // Tells Jersey to produce json notation
public class first_projectResources {
    private MyDAO dao;
    private WorkQueue<DemetraAdoption> queue;

    public first_projectResources(MyDAO dao, WorkQueue<DemetraAdoption> queue) {
        this.dao = dao;
        this.queue = queue;
    }
    
    @POST
    public ArrayList<DemetraAdoption> postMDemetraAdoption(MultipleDemetraAdoption MdemetraAdoption){
        ArrayList<DemetraAdoption> cuaas = new ArrayList<>();
        
        for(DemetraAdoption x : MdemetraAdoption.getCUAAS()){
            int ris = dao.insertDemetraAdoption(x.getCUAA(), x.getCAMPAGNA(), 0);
        
            if (ris > 0)
                cuaas.add(dao.getDemetraAdoption(x.getCUAA()));
        }
        
        if (!cuaas.isEmpty())
            return cuaas;
            
        return null;
    }
    
    @PUT
    public ArrayList<DemetraAdoption> getMDemetraAdoption(){
        ArrayList<DemetraAdoption> demetraAdoption = dao.getAllDemetraAdoption();
        ArrayList<DemetraAdoption> newDemetraAdoption = new ArrayList<>();

        
        for(DemetraAdoption x : demetraAdoption){
            if(x.getFG_PROCESSED() == 0){
                queue.add(x, System.currentTimeMillis());
                newDemetraAdoption.add(dao.getDemetraAdoption(x.getCUAA()));
            }
        }
        
        return newDemetraAdoption;
    }
    
    
}
