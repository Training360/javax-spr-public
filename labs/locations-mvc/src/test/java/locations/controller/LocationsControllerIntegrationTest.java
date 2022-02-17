package locations.controller;

import locations.backend.BackendConfig;
import locations.backend.LocationService;
import locations.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({@ContextConfiguration(classes = BackendConfig.class), @ContextConfiguration(classes = WebConfig.class)})
@WebAppConfiguration
@Sql(statements = "delete from locations")
public class LocationsControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LocationService locationService;

    private MockMvc mockMvc;

    @Before
    public void reset() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSaveThenListLocations() throws Exception {
        MockMultipartFile testFile = new MockMultipartFile("file", "filename.svg",
                "image/svg+xml", new ClassPathResource("/spring.svg", LocationsControllerIntegrationTest.class).getInputStream());

        mockMvc.perform(multipart("/")
                .file(testFile)
                .param("name", "Budapest")
                .param("coordinates", "34.62, 12.87"));

        MvcResult mvcResult = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("locations",
                        hasItem(hasProperty("name", equalTo("Budapest")))))
                .andExpect(model().attribute("locations",
                        hasItem(hasProperty("lat", equalTo(34.62)))))
                .andExpect(model().attribute("locations",
                        hasItem(hasProperty("lon", equalTo(12.87)))))
                .andExpect(content().string(containsString("Budapest")))
                .andReturn();

        long id = ((List<Location>) mvcResult.getModelAndView().getModel().get("locations")).get(0).getId();

        mockMvc.perform(get("/images/{id}", id))
                .andExpect(status().isOk());
    }
}
