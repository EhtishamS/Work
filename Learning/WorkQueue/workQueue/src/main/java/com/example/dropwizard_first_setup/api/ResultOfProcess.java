/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dropwizard_first_setup.api;

import lombok.Data;

@Data
public class ResultOfProcess {
    Integer ID;
    Integer processed;
    Integer errored;

    public ResultOfProcess() {
        this.processed = 0;
        this.errored = 0;
    }
    
    public void incrementProcessed(){
        this.processed++;
    }
    
    public void incrementErrored(){
        this.errored++;
    }
}
