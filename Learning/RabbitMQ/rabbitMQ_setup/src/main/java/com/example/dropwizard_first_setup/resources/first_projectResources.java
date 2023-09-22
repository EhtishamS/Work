package com.example.dropwizard_first_setup.resources;


import com.example.dropwizard_first_setup.api.Anagrafica;
import com.example.dropwizard_first_setup.api.IndirizzoAnagrafica;
import com.example.dropwizard_first_setup.db.MyDAO;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;

import org.jdbi.v3.core.Jdbi;

/**
 *
 * Created by Ehtisham Sarwar on 07/09/2023
 */

@Path("/anagrafica") // This tells Jersey that the application is available on this URL
@Produces(MediaType.APPLICATION_JSON) // Tells Jersey to produce json notation
public class first_projectResources {
    private Jdbi jdbi;
    private MyDAO dao;

    public first_projectResources(Jdbi jdbi, MyDAO dao) {
        this.jdbi = jdbi;
        this.dao = dao;
    }
    
    @GET
    public ArrayList<Anagrafica> getAllAnagrafe(){
        return dao.getAllAnagrafica();
    }
    
    @GET
    @Path("/{ID}")
    public Anagrafica getAnagraficaByID(@PathParam("ID") long id){
        Anagrafica anagrafe = dao.getAnagraficaById(id);
        anagrafe.setAddresses(getAllIndirizzoAnagrafeByID(id));
        
        return anagrafe;
    }
    
    @PUT
    @Path("/{ID}")
    public Anagrafica updateAnagraficaByID(@PathParam("ID") long id, Anagrafica anagrafe){
        if( dao.updateAnagraficaById(id, anagrafe.getName()) > 0)
            return this.getAnagraficaByID(id);
        return null;
    }
    
    @POST
    public Anagrafica postAnagrafica(Anagrafica anagrafe){
        long idAnagraficaNuovo = dao.getNewID();
        int risp = dao.insert(idAnagraficaNuovo, anagrafe.getName()); 
        
        if(risp > 0){
            ArrayList<IndirizzoAnagrafica> indAnagrafica = anagrafe.getAddresses();
            
            if(indAnagrafica != null){
                for(IndirizzoAnagrafica x : indAnagrafica)
                    postIndirizzoAnagraficaByID(idAnagraficaNuovo, x);
                
                Anagrafica nuovoAnagrafe = dao.getAnagraficaById(idAnagraficaNuovo);
                nuovoAnagrafe.setAddresses(dao.getAllIndirizzoAnagrafica(idAnagraficaNuovo));
                
                return nuovoAnagrafe;
            }
            
            return this.getAnagraficaByID(idAnagraficaNuovo);
        }
        
        return null;
    }
    
    @DELETE
    @Path("/{ID}")
    public Anagrafica deleteAnagraficaByid(@PathParam("ID") long id){
        Anagrafica anagrafe = dao.getAnagraficaById(id);
        ArrayList<IndirizzoAnagrafica> addresses = dao.getAllIndirizzoAnagrafica(id);
        
        if (anagrafe != null){
            if(addresses != null){
                anagrafe.setAddresses(addresses);
                
                for(IndirizzoAnagrafica x : addresses)
                    deleteIndirizzoAnagraficaByID(id, x.getID_Anagrafica());
            }
        
            int ris = dao.deleteAnagraficaByid(id);
            if(ris > 0)
                return anagrafe;
        }
        return null;
    }
    
    @GET
    @Path("/{ID}/addresses")
    public ArrayList<IndirizzoAnagrafica> getAllIndirizzoAnagrafeByID(@PathParam("ID") long id){
        return dao.getAllIndirizzoAnagrafica(id);
    }
    
    @GET
    @Path("/{ID}/addresses/{ID_ADD}")
    public IndirizzoAnagrafica getIndirizzoAnagraficaByID(@PathParam("ID") long id, @PathParam("ID_ADD") long id_anagrafica){
        return dao.getIndirizzoAnagraficaById(id, id_anagrafica);
    }
    
    @PUT
    @Path("/{ID}/addresses/{ID_ADD}")
    public IndirizzoAnagrafica updateIndirizzoAnagraficaByID(@PathParam("ID") long id, @PathParam("ID_ADD") long id_anagrafica, IndirizzoAnagrafica indirizzoAnagrafe){
        
        if(dao.updateIndirizzoAnagraficaById(id, id_anagrafica, indirizzoAnagrafe.getAddress()) > 0)
            return getIndirizzoAnagraficaByID(id, id_anagrafica);
        
        return null;
    }
    
    @POST
    @Path("/{ID}/addresses")
    public IndirizzoAnagrafica postIndirizzoAnagraficaByID(@PathParam("ID") long id, IndirizzoAnagrafica indirizzoAnagrafe){
        long ID_nuovo = dao.getNewIndirizzoID();
        
        if(dao.postIndirizzoAnagrafica(ID_nuovo, id, indirizzoAnagrafe.getAddress()) > 0)
            return getIndirizzoAnagraficaByID(ID_nuovo, id);
        
        return null;
    }
    
    @DELETE
    @Path("/{ID}/addresses/{ID_ADD}")
    public IndirizzoAnagrafica deleteIndirizzoAnagraficaByID(@PathParam("ID") long id, @PathParam("ID_ADD") long id_anagrafica){
        IndirizzoAnagrafica indirizzoAnagrafe = getIndirizzoAnagraficaByID(id, id_anagrafica);
        
        if (indirizzoAnagrafe != null )
            if(dao.deleteindirizzoAnagraficaById(id, id_anagrafica) > 0 )
                return indirizzoAnagrafe;
        return null;
    }
    
}
