package spring.web;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import spring.web.backend.BackendConfig;
import spring.web.controller.WebConfig;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;

public class WebAppInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{BackendConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(System.getProperty("java.io.tmpdir"),
                        5 * 1024 * 1024,
                        20 * 1024 * 1024,
                        0);
        registration.setMultipartConfig(multipartConfigElement);
    }
}
