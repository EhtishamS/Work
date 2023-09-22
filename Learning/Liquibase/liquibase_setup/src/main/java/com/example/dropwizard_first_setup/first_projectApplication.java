package com.example.dropwizard_first_setup;

import com.example.dropwizard_first_setup.db.MyDAO;
import com.example.dropwizard_first_setup.resources.first_projectResources;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;

public class first_projectApplication extends Application<first_projectConfiguration> {

    public static void main(final String[] args) throws Exception {
        new first_projectApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(final Bootstrap<first_projectConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final first_projectConfiguration configuration, final Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "oracle");
        final MyDAO dao = jdbi.onDemand(MyDAO.class);
        
        first_projectResources resource = new first_projectResources(jdbi, dao);
        environment.jersey().register(resource);
    }

}
