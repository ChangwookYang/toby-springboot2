package tobyspringboot.helloboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@MySpringBootAnnotation
public class Helloboot2Application {

    public static void main(String[] args) {
        SpringApplication.run(Helloboot2Application.class, args);
    }


}