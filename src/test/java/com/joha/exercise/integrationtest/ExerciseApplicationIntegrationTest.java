package com.joha.exercise.integrationtest;

import com.google.gson.Gson;
import com.joha.exercise.configuration.ApplicationConfiguration;
import com.joha.exercise.controllers.SearchController;
import com.joha.exercise.domain.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {ApplicationConfiguration.class})
@WebMvcTest(SearchController.class)
class ExerciseApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /*
    These test a quite powerful, as they test using an application context very similar to the that of the real application.
    They are expensive, and can be a bit cumbersome to setup, if the application has a lot of configuration.
     */
    @Test
    public void testFindingATicket() throws Exception {
        //The result depends on a file in the inboundJsonData directory
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/searchUsingStream?attribute=priority&value=low")).andReturn();
        Gson gson = new Gson();
        Ticket[] tickets = gson.fromJson(mvcResult.getResponse().getContentAsString(), Ticket[].class);
        assertEquals(1, tickets.length);
    }
}
