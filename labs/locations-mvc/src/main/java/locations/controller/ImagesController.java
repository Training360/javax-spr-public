package locations.controller;

import locations.backend.LocationService;
import locations.model.Image;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/images")
public class ImagesController {

    private LocationService locationService;

    public ImagesController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable("id") long id) {
        Image image = locationService.findLocationById(id).getImage();
        return ResponseEntity
                .ok()
                .header("Content-Type", image.getContentType())
                .body(new ByteArrayResource(image.getContent()));
    }
}
