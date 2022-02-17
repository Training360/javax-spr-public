package locations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {

    @Mock
    private LocationDao locationDao;

    @InjectMocks
    private LocationService locationService;

    @Test
    public void testSave() {
        locationService.createLocation("BP", 1.0, 1.0);
        verify(locationDao).save("BP", 1.0, 1.0);
    }
}
