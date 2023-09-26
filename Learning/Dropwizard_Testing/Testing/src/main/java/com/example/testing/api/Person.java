/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.testing.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author e.sarwar
 */
@Data
public class Person {
    private String name;
    private String email;

    private Person() {
        // Jackson deserialization
    }

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }

    // hashCode
    // equals
    // toString etc.
}
