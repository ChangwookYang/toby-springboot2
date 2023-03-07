package tobyspringboot.helloboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
@SpringBootApplication
public class Helloboot2Application {

    public static void main(String[] args) {
        SpringApplication.run(Helloboot2Application.class, args);
    }

}
*/
/*
SpringBootApplication 어노테이션과
SpringApplication.run(Helloboot2Application.class, args) 메소드만으로 Containerless 환경을 구축해냈다.
이제 SpringBootApplication와 SpringApplication.run(Helloboot2Application.class, args)  없이 구현해보자!!
*/
public class Helloboot2Application {

    public static void main(String[] args) {
        System.out.println("실행해도 콘솔에 찍히지만 톰캣이 올라가지 않으며 Spring boot는 실행되지 않는다.");
    }

}