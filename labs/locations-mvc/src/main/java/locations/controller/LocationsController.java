package locations.controller;

import locations.backend.LocationService;
import locations.model.Image;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;

@Controller
public class LocationsController {

    private LocationService locationService;

    private MessageSource messageSource;

    public LocationsController(LocationService locationService, MessageSource messageSource) {
        this.locationService = locationService;
        this.messageSource = messageSource;
    }

    @ModelAttribute
    public LocationForm locationForm() {
        return new LocationForm();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView listLocations() {
        return new ModelAndView("index",
                "locations", locationService.listLocations());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView saveLocation(@Valid LocationForm locationForm, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Locale locale) {
        if(bindingResult.hasErrors()) {
            return new ModelAndView("index",
                    "locations", locationService.listLocations());
        }

        Image image = readImage(locationForm);

        String message = messageSource.getMessage("location.saved", new Object[]{locationForm.getName()}, locale);
        String[] coordinates = locationForm.getCoordinates().split(", ");
        locationService.saveLocation(locationForm.getName(),
                Double.parseDouble(coordinates[0]),
                Double.parseDouble(coordinates[1]),
                image);
        redirectAttributes.addFlashAttribute("message", message);
        //return "redirect:/";
        return new ModelAndView("redirect:/");
    }

    private Image readImage(LocationForm locationForm) {
        if(locationForm.getFile() != null && locationForm.getFile().getSize() != 0) {
            try {
                ByteArrayResource resource = new ByteArrayResource(locationForm.getFile().getBytes());
                return new Image(resource.getByteArray(), locationForm.getFile().getContentType());
            } catch (IOException ioe) {
                throw new IllegalStateException("Failed to read image", ioe);
            }
        }
        return null;
    }
}
