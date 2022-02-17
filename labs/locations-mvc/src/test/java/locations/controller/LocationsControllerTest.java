package locations.controller;

import locations.backend.LocationService;
import locations.model.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocationsControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationsController locationsController;

    @Test
    public void testListEmployees() {
        when(locationService.listLocations()).thenReturn(
                Collections.singletonList(new Location(1L, "Budapest", 23.45, 64.01)));

        ModelAndView modelAndView = locationsController.listLocations();
        assertEquals("index", modelAndView.getViewName());
        assertEquals(Collections.singletonList("Budapest"), ((List<Location>) modelAndView.getModel().get("locations"))
                .stream().map(Location::getName).collect(Collectors.toList()));
    }
}
