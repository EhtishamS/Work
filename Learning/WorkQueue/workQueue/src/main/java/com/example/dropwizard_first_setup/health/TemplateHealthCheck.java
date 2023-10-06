/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dropwizard_first_setup.health;

import com.codahale.metrics.health.HealthCheck;

/**
 *
 * @author e.sarwar
 */
public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    public TemplateHealthCheck(String template) {
        this.template = template;
    }
    
    
    /*
        In this case this method checks that the string in well formatted and
        that it produces the template in output given the name.
    */
    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST"); // format check
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }
}
