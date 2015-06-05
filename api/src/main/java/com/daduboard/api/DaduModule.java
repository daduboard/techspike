package com.daduboard.api;

import com.daduboard.api.core.Gambling;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;

import javax.validation.Validator;

/**
 * Created by swzhou on 15/6/4.
 */
public class DaduModule extends AbstractModule {
    private final HibernateBundle<DaduConfiguration> hibernate = new HibernateBundle<DaduConfiguration>(Gambling.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(DaduConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    public DaduModule(Bootstrap<DaduConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    protected void configure() {

    }

    @Provides
    public Validator providesValidator(Environment environment) {
        return environment.getValidator();
    }

    @Provides
    public SessionFactory provides() {
        return hibernate.getSessionFactory();
    }
}
