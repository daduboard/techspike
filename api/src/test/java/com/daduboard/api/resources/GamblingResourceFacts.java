package com.daduboard.api.resources;

import com.daduboard.api.core.Gambling;
import com.daduboard.api.db.GamblingDAO;
import com.google.common.base.Optional;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by swzhou on 15/6/5.
 */
public class GamblingResourceFacts {
    private static final GamblingDAO dao = mock(GamblingDAO.class);
    private static final Validator validator = mock(Validator.class);
    private Gambling gambling;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new GamblingResource(dao, validator))
            .build();

    @Before
    public void setup() {
        gambling = new Gambling("title", "description");
    }

    @Test
    public void should_get_gambling_given_id_of_existing_gambling() {
        when(dao.findById(eq(1l))).thenReturn(Optional.of(gambling));

        assertThat(resources.client().target("/gambling/1").request().get(Gambling.class))
            .isEqualTo(gambling);
        verify(dao).findById(1);
    }

    @Test
    public void should_return_404_not_found_given_arbitary_id() {
        when(dao.findById(eq(2l))).thenReturn(Optional.<Gambling>absent());

        assertThat(resources.client().target("/gambling/2").request().get().getStatus()).isEqualTo(404);
        verify(dao).findById(2);
    }

    @After
    public void tearDown() {
        reset(dao);
    }
}
