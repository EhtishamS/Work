package com.example.dropwizard_first_setup.resources;


import com.abacogroup.embeddedqueue.WorkQueue;
import com.example.dropwizard_first_setup.api.DemetraAdoption;
import com.example.dropwizard_first_setup.api.MultipleDemetraAdoption;
import com.example.dropwizard_first_setup.api.ResultOfProcess;
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
    
    @GET
    public ArrayList<DemetraAdoption> getAllDemetraAdoption(){
        return dao.getAllDemetraAdoption();
    }
    
    @GET
    @Path("/cuaa/{CUAA}")
    public DemetraAdoption getDemetraAdoptionByCUAA(@PathParam("CUAA") String cuaa){
        return dao.getDemetraAdoptionByCUAA(cuaa);
    }
    
    @GET
    @Path("/id/{ID}")
    public ArrayList<DemetraAdoption> getDemetraAdoptionByID(@PathParam("ID") Integer id){
        return dao.getDemetraAdoptionByID(id);
    }
    
    
    @POST
    public ArrayList<DemetraAdoption> postMDemetraAdoption(MultipleDemetraAdoption MdemetraAdoption){
        ArrayList<DemetraAdoption> cuaas = new ArrayList<>();
        Integer ID_lista = dao.getMaxIDList();
        
        if(ID_lista == null)
            ID_lista = 0;
        
        ID_lista++;
        
        for(DemetraAdoption x : MdemetraAdoption.getCUAAS()){
            int ris = dao.insertDemetraAdoption(x.getCUAA(), x.getCAMPAGNA(), 0, ID_lista);
        
            if (ris > 0)
                cuaas.add(dao.getDemetraAdoptionByCUAA(x.getCUAA()));
        }
        
        if (!cuaas.isEmpty())
            return cuaas;
            
        return null;
    }
    
    @PUT
    public String putProcessByLastID(){
        ArrayList<DemetraAdoption> demetraAdoption = getDemetraAdoptionByID(dao.getMaxIDList());
        
        for(DemetraAdoption x : demetraAdoption){
            queue.add(x, System.currentTimeMillis());
        }
        
        return "The list with id " + dao.getMaxIDList() + " has been added to be processed";
    }
    
    @PUT
    @Path("/{ID}")
    public String putProcessByID(@PathParam("ID") Integer id){
        ArrayList<DemetraAdoption> demetraAdoption = getDemetraAdoptionByID(id);
        
        for(DemetraAdoption x : demetraAdoption){
            if(x.getFG_PROCESSED() == 0)
                queue.add(x, System.currentTimeMillis());
        }
        
        return "The list with id " + id + " has been added to be processed";
    }
    
    @GET
    @Path("/ResultOfLastProcess")
    public ResultOfProcess getResultOfProcessByID(){
        ResultOfProcess process = new ResultOfProcess();
        ArrayList<DemetraAdoption> cuaas = dao.getDemetraAdoptionByID(dao.getMaxIDList());
        
        process.setID(dao.getMaxIDList());
        
        for (DemetraAdoption x : cuaas){
            if(x.getFG_PROCESSED() == 1)
                process.incrementProcessed();
            else 
                process.incrementErrored();
        }
        
        return process;
    }
    
    @GET
    @Path("/ResultOfProcess/{ID}")
    public ResultOfProcess getResultOfProcessByID(@PathParam("ID") Integer id_lista){
        ResultOfProcess process = new ResultOfProcess();
        ArrayList<DemetraAdoption> cuaas = dao.getDemetraAdoptionByID(id_lista);
        
        process.setID(id_lista);
        
        for (DemetraAdoption x : cuaas){
            if(x.getFG_PROCESSED() == 1)
                process.incrementProcessed();
            else 
                process.incrementErrored();
        }
        
        return process;
    }
}
