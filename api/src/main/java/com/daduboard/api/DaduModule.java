package com.daduboard.api;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import javax.validation.Validator;

/**
 * Created by swzhou on 15/6/4.
 */
public class DaduModule extends AbstractModule{
    @Override
    protected void configure() {

    }

    @Provides
    public Validator providesValidator(Environment environment) {
        return environment.getValidator();
    }

    @Provides
    public DBI providesJDBI(Environment environment, DaduConfiguration configuration) {
        DBIFactory factory = new DBIFactory();
        return factory.build(environment, configuration.getDatabase(), "mysql");
    }
}
