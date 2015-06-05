package com.daduboard.api.resources;

import com.codahale.metrics.annotation.Timed;
import com.daduboard.api.db.GamblingDAO;
import com.daduboard.api.core.Gambling;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by swzhou on 15/6/3.
 */
@Path("/gambling")
@Produces(MediaType.APPLICATION_JSON)
public class GamblingResource {
    private final GamblingDAO gamblingDao;
    private final Validator validator;

    @Inject
    public GamblingResource(GamblingDAO dao, Validator validator) {
        this.validator = validator;
        this.gamblingDao = dao;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Response getGambling(@PathParam("id") LongParam id) {
        final Optional<Gambling> gambling = gamblingDao.findById(id.get());
        return gambling.isPresent()
                ? Response.ok(gambling.get()).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Timed
    @UnitOfWork
    public Response createGambling(Gambling gambling) throws URISyntaxException {
        return validateAndReturn(gambling, () -> {
            long newGamblingId = gamblingDao.create(gambling);
            return Response.created(new URI(String.valueOf(newGamblingId))).build();
        });
    }

    private Response validateAndReturn(Gambling gambling, ResponseGetter responseGetter) throws URISyntaxException {
        Set<ConstraintViolation<Gambling>> violations = validator.validate(gambling);
        if (!violations.isEmpty()) {
            List<String> validationMessages = violations.stream()
                    .map(v -> v.getPropertyPath().toString() + ":" + v.getMessage())
                    .collect(Collectors.toList());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages).build();
        }
        return responseGetter.get();
    }
}
