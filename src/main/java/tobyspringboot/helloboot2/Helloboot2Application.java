package tobyspringboot.helloboot2;

import org.springframework.boot.SpringApplication;
import tobyspringboot.config.MySpringBootAnnotation;

@MySpringBootAnnotation
public class Helloboot2Application {

    public static void main(String[] args) {
        SpringApplication.run(Helloboot2Application.class, args);
    }


}