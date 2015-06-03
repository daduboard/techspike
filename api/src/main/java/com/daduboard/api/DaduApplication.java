package com.daduboard.api;

import com.daduboard.api.resources.GamblingResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by swzhou on 15/6/3.
 */
public class DaduApplication extends Application<DaduConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaduApplication.class);

    public static void main(String[] args) throws Exception {
        new DaduApplication().run(args);
    }

    @Override
    public void run(DaduConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("start running dadu application");
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDatabase(), "mysql");
        environment.jersey().register(new GamblingResource(jdbi, environment.getValidator()));
    }

    @Override
    public void initialize(Bootstrap<DaduConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<DaduConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(DaduConfiguration configuration) {
                return configuration.getDatabase();
            }
        });
    }
}
