package com.example.dropwizard_first_setup;

import com.abacogroup.embeddedqueue.QueueErrorListener.ErrorAction;
import com.abacogroup.embeddedqueue.WorkQueue;
import com.abacogroup.embeddedqueue.db.JdbiRepository;
import com.abacogroup.jdbi.OraJdbiPlugin;
import com.example.dropwizard_first_setup.api.DemetraAdoption;
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
        jdbi.installPlugin(new OraJdbiPlugin());
        
        final MyDAO dao = jdbi.onDemand(MyDAO.class);
        
        WorkQueue<DemetraAdoption> queue = WorkQueue.builder("1", new JdbiRepository(jdbi), DemetraAdoption.class)
                .withNumThreads(2)
                .withTimeoutMs(1000)
                .withMetricsRegistry(null)
                .withErrorListener((element, t) -> {
                    System.out.println("An error has occured: " + t.toString());
                    
                    return ErrorAction.MARK_AS_ERRORED;
                })
                .withConsumer(element -> {
                    dao.changeFG_PROCESSED(element.getCUAA());
                })
                .build(); 
        
        queue.start();
        
        first_projectResources resource = new first_projectResources(dao, queue);
        environment.jersey().register(resource);        
    }

}
