package com.daduboard.api.representations;

import com.daduboard.api.core.Gambling;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by swzhou on 15/6/4.
 */
public class GamblingFacts {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void should_serialize_to_json() throws Exception {
        final Gambling gambling = new Gambling("raining", "according to my calculation, it must rain tomorrow");

        final String expected = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/gambling.json"), Gambling.class));

        assertThat(MAPPER.writeValueAsString(gambling)).isEqualTo(expected);
    }

    @Test
    public void should_deserialize_from_json() throws Exception {
        final Gambling gambling = new Gambling("raining", "according to my calculation, it must rain tomorrow");

        Gambling actualGambling = MAPPER.readValue(fixture("fixtures/gambling.json"), Gambling.class);
        assertThat(actualGambling.getId()).isEqualTo(gambling.getId());
        assertThat(actualGambling.getTitle()).isEqualTo(gambling.getTitle());
        assertThat(actualGambling.getDescription()).isEqualTo(gambling.getDescription());
    }
}
