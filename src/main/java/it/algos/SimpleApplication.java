package it.algos;

import com.vaadin.flow.component.dependency.*;
import com.vaadin.flow.component.page.*;
import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.annotation.*;
import com.vaadin.flow.theme.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.*;
import org.springframework.boot.autoconfigure.security.servlet.*;
import org.springframework.boot.web.servlet.support.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: sab, 05-mar-2022
 * Time: 18:50
 * <p>
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication(scanBasePackages = {"it.algos"}, exclude = {SecurityAutoConfiguration.class})
@EnableVaadin({"it.algos"})
@EntityScan({"it.algos"})
@Theme(value = "vaadin23")
@PWA(name = "vaadin23", shortName = "vaadin23", offlineResources = {"images/logo.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class SimpleApplication extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }

}
