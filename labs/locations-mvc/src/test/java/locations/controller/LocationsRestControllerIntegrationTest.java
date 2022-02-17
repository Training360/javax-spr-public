package locations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import locations.backend.BackendConfig;
import locations.backend.LocationService;
import locations.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(classes = BackendConfig.class),
        @ContextConfiguration(classes = WebConfig.class)})
@WebAppConfiguration
@Sql(statements = "delete from locations")
public class LocationsRestControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LocationService locationService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSaveThenListLocations() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new Location("BP", 23.45, 67.89));
        System.out.println(json);
        mockMvc.perform(post("/api/locations")
                .header("Content-Type", "application/json")
                .content(json));

        mockMvc.perform(get("/api/locations"))
                .andExpect(status().isOk())
                .andDo(r -> System.out.println(r.getResponse().getContentAsString()))
                .andExpect(jsonPath("$[0].name", equalTo("BP")));
    }
}
