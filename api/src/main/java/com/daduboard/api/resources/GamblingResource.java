package com.daduboard.api.resources;

import com.codahale.metrics.annotation.Timed;
import com.daduboard.api.dao.GamblingDao;
import com.daduboard.api.representations.Gambling;
import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;

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
    private final GamblingDao gamblingDao;
    private final Validator validator;

    @Inject
    public GamblingResource(DBI jdbi, Validator validator) {
        this.validator = validator;
        this.gamblingDao = jdbi.onDemand(GamblingDao.class);
    }

    @GET
    @Timed
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {
        Gambling gambling = gamblingDao.getGamblingById(id);
        return Response.ok(gambling).build();
    }

    @POST
    @Timed
    public Response createContact(Gambling gambling) throws URISyntaxException {
        return validateAndReturn(gambling, () -> {
            int newGamblingId = gamblingDao.createGambling(gambling.getTitle(),
                    gambling.getDescription());
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
