package com.backandwhite.core.api.controllers;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalErrorResponsesCustomizerTest {

    private GlobalErrorResponsesCustomizer customizer;

    @BeforeEach
    void setup() {
        customizer = new GlobalErrorResponsesCustomizer();
    }

    @Test
    void testCustomize_GET_addsExpectedResponses() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.path("/test", new PathItem().get(new Operation()));

        customizer.customise(openAPI);

        ApiResponses responses = openAPI.getPaths().get("/test").getGet().getResponses();
        assertNotNull(responses);

        assertTrue(responses.containsKey("401"));
        assertTrue(responses.containsKey("500"));
        assertTrue(responses.containsKey("404"));
        assertTrue(responses.containsKey("403"));

        Schema<?> schema = responses.get("401").getContent()
                .get("application/json")
                .getSchema();

        assertEquals("object", schema.getType());
        assertTrue(schema.getProperties().containsKey("code"));
        assertTrue(schema.getProperties().containsKey("message"));
        assertTrue(schema.getProperties().containsKey("details"));
        assertTrue(schema.getProperties().containsKey("timeStamp"));
    }

    @Test
    void testCustomize_POST_addsExpectedResponses() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.path("/test", new PathItem().post(new Operation()));

        customizer.customise(openAPI);

        ApiResponses responses = openAPI.getPaths().get("/test").getPost().getResponses();
        assertNotNull(responses);

        assertTrue(responses.containsKey("400"));
        assertTrue(responses.containsKey("409"));
        assertTrue(responses.containsKey("403"));
        assertTrue(responses.containsKey("401"));
        assertTrue(responses.containsKey("500"));
    }

    @Test
    void testCustomize_PUT_addsExpectedResponses() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.path("/test", new PathItem().put(new Operation()));

        customizer.customise(openAPI);

        ApiResponses responses = openAPI.getPaths().get("/test").getPut().getResponses();
        assertNotNull(responses);

        assertTrue(responses.containsKey("400"));
        assertTrue(responses.containsKey("404"));
        assertTrue(responses.containsKey("403"));
        assertTrue(responses.containsKey("401"));
        assertTrue(responses.containsKey("500"));
    }

    @Test
    void testCustomize_DELETE_addsExpectedResponses() {
        OpenAPI openAPI = new OpenAPI();
        openAPI.path("/test", new PathItem().delete(new Operation()));

        customizer.customise(openAPI);

        ApiResponses responses = openAPI.getPaths().get("/test").getDelete().getResponses();
        assertNotNull(responses);

        assertTrue(responses.containsKey("404"));
        assertTrue(responses.containsKey("403"));
        assertTrue(responses.containsKey("401"));
        assertTrue(responses.containsKey("500"));
    }

    @Test
    void testInlineSchemaStructure() throws Exception {
        Schema<?> schema = invokeCreateInlineSchema();

        assertEquals("object", schema.getType());
        assertEquals(4, schema.getProperties().size());
        assertTrue(schema.getProperties().containsKey("code"));
        assertTrue(schema.getProperties().containsKey("message"));
        assertTrue(schema.getProperties().containsKey("details"));
        assertTrue(schema.getProperties().containsKey("timeStamp"));

        Schema<?> timestampSchema = (Schema<?>) schema.getProperties().get("timeStamp");
        assertEquals("date-time", timestampSchema.getFormat());
    }

    private Schema<?> invokeCreateInlineSchema() throws Exception {
        var method = GlobalErrorResponsesCustomizer.class.getDeclaredMethod("createInlineSchema");
        method.setAccessible(true);
        return (Schema<?>) method.invoke(customizer);
    }
}