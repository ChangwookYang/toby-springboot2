package tobyspringboot.helloboot2;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan // @Component가 붙은 클래스들을 찾아서 빈으로 등록해달라
// applicationContext에 등록된 Helloboot2Application부터 하위 패키지들을 스캔한다.
// 장점으로 별도 소스에 Bean을 등록할 필요 없이 Component 어노테이션만 추가하면 된다.
public class Helloboot2Application {

    // 기능을 담당하는 Bean 외에
    // TomcatServletWebServerFactory, DispatcherServlet Object들이 있다.
    // 이 두가지의 Object가 없으면 애플리케이션 자체를 시작할 수 없다.
    // 이 두가지도 main에서 구성하지 말고 Bean으로 등록하는 방법을 알아보자.
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    // MySpringApplication 을 만들어 리팩토링하였더니
    // SpringApplication.run(Helloboot2Application.class, args);
    // 초기의 형태와 똑같아졌다.
    public static void main(String[] args) {
        MySpringApplication.run(Helloboot2Application.class, args);
    }


}