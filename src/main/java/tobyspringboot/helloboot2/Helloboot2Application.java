package tobyspringboot.helloboot2;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class Helloboot2Application {

    // Factory Method로 Bean Object를 직접만들어서 쓸 수 있다.
    // 복잡한 설정정보를 나열하는 대신에 Java 코드로 구현하면 간결해지고 이해하기 쉽다.
    // 이 Factory Method는 스프링 컨테이너가 호출하게된다.
    // 필요한 오브젝트를 스프링 컨테이너가 넘겨줄 수 있게 파라미터에 HelloService를 추가한다.
    // 스프링 컨테이너가 Bean오브젝트를 만들기위해 쓰이는 것을 알려주기 위해 Bean 어노테이션을 추가한다.
    // 또한 스프링 컨테이너가 Bean 오브젝트를 가진 클래스이다.를 인지하도 클래스레벨에도 @Configuration 클래스를 추가한다.
    @Bean
    public HelloController helloController(HelloService helloService) {
        return new HelloController(helloService);
    }

    @Bean
    public HelloService helloService() {
        return new SimpleHelloService();
    }

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();
                // refresh : 스프링컨테이너의 초기화 과정이 실행된다.
                // onRefresh에서는 스프링 컨테이너가 실행될때 부가적으로 실행할 내용들을 추가할 수 있는데
                // DispatcherServlet을 스프링컨테이너가 초기화 되는 시점에 선언하도록 수정하였다.
                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    // FrontController를 이용하여 Mapping(uri), Binding(parameter) 하였는데 모든 정보를 우리가 직접 입력할 순 없다.
                    // 스프링을 이용하여 다른 전략으로 바꿀 수 있다
                    // 이 상태로 실행하게 되면 에러가 발생한다
                    // Dispatcher에게 어떠한 Bean으로 요청을 전달할지 알려주지 않았기 때문이다.
                    // 예전에는 xml에 명시하기도 하였는데 현재는 Mapping 정보를 Controller에 넣는 방법이 쓰인다.
                    servletContext.addServlet("dispatcherServlet",
                            new DispatcherServlet(this)
                    ).addMapping("/*"); // 서블릿을 추가할때 서블릿 컨테이너가 요청이 들어올때 어느 서블릿에 매핑해줘야할지 결정하는 매핑을 추가한다.
                });
                webServer.start();
            }
        };
        // Bean오브젝트를 가진 클래스를 등록하는 과정이 필요하다.
        applicationContext.register(Helloboot2Application.class);
        applicationContext.refresh();
    }

}