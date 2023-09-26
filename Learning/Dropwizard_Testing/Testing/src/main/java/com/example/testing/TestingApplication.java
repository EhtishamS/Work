package com.example.testing;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class TestingApplication extends Application<TestingConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TestingApplication().run(args);
    }

    @Override
    public String getName() {
        return "Testing";
    }

    @Override
    public void initialize(final Bootstrap<TestingConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final TestingConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
