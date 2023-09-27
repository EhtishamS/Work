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
    @SqlUpdate("INSERT INTO DEMETRA_ADOPTION(CUAA, CAMPAGNA, FG_PROCESSED, ID_LISTA) VALUES (:cuaa, :campagna, :fg_processed, :id_lista) ")
    Integer insertDemetraAdoption(@Bind("cuaa") String cuaa, @Bind("campagna") int campagna, @Bind("fg_processed") int fg_processed, @Bind("id_lista") int id_lista);
    
    @SqlQuery("SELECT * FROM DEMETRA_ADOPTION WHERE CUAA = :cuaa")
    @RegisterFieldMapper(DemetraAdoption.class)
    DemetraAdoption getDemetraAdoptionByCUAA(@Bind("cuaa") String cuaa);
    
    @SqlQuery("SELECT * FROM DEMETRA_ADOPTION WHERE ID_LISTA = :id_lista")
    @RegisterFieldMapper(DemetraAdoption.class)
    ArrayList<DemetraAdoption> getDemetraAdoptionByID(@Bind("id_lista") Integer id_lista);
    
    @SqlQuery("SELECT * FROM DEMETRA_ADOPTION")
    @RegisterFieldMapper(DemetraAdoption.class)
    ArrayList<DemetraAdoption> getAllDemetraAdoption();
    
    @SqlQuery("SELECT * FROM DEMETRA_ADOPTION WHERE FG_PROCESSED = 0")
    @RegisterFieldMapper(DemetraAdoption.class)
    ArrayList<DemetraAdoption> getAllUnReadDemetraAdoption();
    
    @SqlUpdate("UPDATE DEMETRA_ADOPTION SET FG_PROCESSED = :n WHERE CUAA = :cuaa")
    Integer changeFG_PROCESSED(@Bind("cuaa") String cuaa, @Bind("n") int n);
    
    @SqlQuery("SELECT MAX(ID_LISTA) FROM DEMETRA_ADOPTION")
    Integer getMaxIDList();
    
    void close();
}