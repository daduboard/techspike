package com.daduboard.api.db;

import com.daduboard.api.core.Gambling;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;


/**
 * Created by swzhou on 15/6/5.
 */
public class GamblingDAO extends AbstractDAO<Gambling> {
    /**
     * Creates a new DAO with a given session provider.
     *
     * @param sessionFactory a session provider
     */
    @Inject
    public GamblingDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Gambling> findById(long id) {
        return Optional.fromNullable(get(id));
    }

    public long create(Gambling gambling) {
        return persist(gambling).getId();
    }
}
