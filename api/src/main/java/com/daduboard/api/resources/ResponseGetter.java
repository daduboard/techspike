package com.daduboard.api.resources;

import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

@FunctionalInterface
public interface ResponseGetter {
    Response get() throws URISyntaxException;
}
