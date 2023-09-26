/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dropwizard_first_setup.api;

import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author e.sarwar
 */
@Data
public class Anagrafica {
    long ID;
    String Name;
    ArrayList<IndirizzoAnagrafica> addresses;
}
