/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.dropwizard_first_setup.db;

import com.example.dropwizard_first_setup.api.DemetraAdoption;
import java.util.ArrayList;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 *
 * @author e.sarwar
 */
public interface MyDAO {
    @SqlUpdate("INSERT INTO DEMETRA_ADOPTION(CUAA, CAMPAGNA, FG_PROCESSED) VALUES (:cuaa, :campagna, :fg_processed) ")
    int insertDemetraAdoption(@Bind("cuaa") String cuaa, @Bind("campagna") int campagna, @Bind("fg_processed") int fg_processed);
    
    @SqlQuery("SELECT * FROM DEMETRA_ADOPTION WHERE CUAA = :cuaa")
    @RegisterFieldMapper(DemetraAdoption.class)
    DemetraAdoption getDemetraAdoption(@Bind("cuaa") String cuaa);
    
    @SqlQuery("SELECT * FROM DEMETRA_ADOPTION")
    @RegisterFieldMapper(DemetraAdoption.class)
    ArrayList<DemetraAdoption> getAllDemetraAdoption();
    
    @SqlUpdate("UPDATE DEMETRA_ADOPTION SET FG_PROCESSED = 1 WHERE CUAA = :cuaa")
    int changeFG_PROCESSED(@Bind("cuaa") String cuaa);
    
    /*
    @SqlUpdate("INSERT INTO PROVA_ANAGRAFICA(ID, NAME) VALUES (:id, :name)")
    int insert(@Bind("id") long id, @Bind("name") String name);
    
    @SqlQuery("SELECT ID, NAME FROM PROVA_ANAGRAFICA WHERE ID = :id")
    @RegisterFieldMapper(Anagrafica.class)
    Anagrafica getAnagraficaById(@Bind("id") long id);
    
    @SqlQuery("SELECT * FROM PROVA_ANAGRAFICA ORDER BY ID")
    @RegisterFieldMapper(Anagrafica.class)
    ArrayList<Anagrafica> getAllAnagrafica();
    
    @SqlUpdate("UPDATE PROVA_ANAGRAFICA SET NAME = :name WHERE ID = :id")
    @RegisterFieldMapper(Anagrafica.class)
    int updateAnagraficaById(@Bind("id") long id, @Bind("name") String name);
    
    @SqlUpdate("DELETE FROM PROVA_ANAGRAFICA WHERE ID = :id")
    int deleteAnagraficaByid(@Bind("id") long id);
    
    @SqlQuery("SELECT MAX(ID)+1 FROM PROVA_ANAGRAFICA")
    @RegisterFieldMapper(Anagrafica.class)
    long getNewID();   
    */
    /*--------------------------------------------*/
    /*
    @SqlQuery("SELECT * FROM PROVA_INDIRIZZO WHERE ID_ANAGRAFICA = :id_anagrafica")
    @RegisterFieldMapper(IndirizzoAnagrafica.class)
    ArrayList<IndirizzoAnagrafica> getAllIndirizzoAnagrafica(@Bind("id_anagrafica") long id_anagrafica);
    
    @SqlQuery("SELECT * FROM PROVA_INDIRIZZO WHERE ID = :id AND ID_ANAGRAFICA = :id_anagrafica ")
    @RegisterFieldMapper(IndirizzoAnagrafica.class)
    IndirizzoAnagrafica getIndirizzoAnagraficaById(@Bind("id") long id, @Bind("id_anagrafica") long id_anagrafica);
    
    @SqlUpdate("INSERT INTO PROVA_INDIRIZZO(ID, ID_ANAGRAFICA, ADDRESS) VALUES (:id, :id_anagrafica, :address)")
    int postIndirizzoAnagrafica(@Bind("id") long id, @Bind("id_anagrafica") long id_anagrafica, @Bind("address") String address);
    
    @SqlUpdate("UPDATE PROVA_INDIRIZZO SET ADDRESS = :address WHERE ID = :id AND ID_ANAGRAFICA = :id_anagrafica")
    int updateIndirizzoAnagraficaById(@Bind("id") long id, @Bind("id_anagrafica") long id_anagrafica, @Bind("address") String address);
    
    @SqlUpdate("DELETE FROM PROVA_INDIRIZZO WHERE ID = :id AND ID_ANAGRAFICA = :id_anagrafica")
    @RegisterFieldMapper(IndirizzoAnagrafica.class)
    int deleteindirizzoAnagraficaById(@Bind("id") long id, @Bind("id_anagrafica") long id_anagrafica);
    
    
    @SqlQuery("SELECT MAX(ID)+1 FROM PROVA_INDIRIZZO")
    long getNewIndirizzoID();
    */
    void close();
}