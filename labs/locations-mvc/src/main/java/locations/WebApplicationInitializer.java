package locations;

import locations.backend.BackendConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import locations.controller.WebConfig;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {BackendConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(System.getProperty("java.io.tmpdir"),
                        5 * 1024 * 1024L,
                        20 * 1024 * 1024L,
                        0);
        registration.setMultipartConfig(multipartConfigElement);
    }
}
