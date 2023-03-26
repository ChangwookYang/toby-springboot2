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

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();
                // refresh : 스프링컨테이너의 초기화 과정이 실행된다.
                // onRefresh에서는 스프링 컨테이너가 실행될때 부가적으로 실행할 내용들을 추가할 수 있는데
                // DispatcherServlet을 스프링컨테이너가 초기화 되는 시점에 선언하도록 수정하였다.
                // ServletWebServerFactory.class Bean은 하나이므로 Bean을 찾아온다.
                // DispatcherServlet의 경우 dispatcher servlet에 Spring container를 지정해주었다. (setApplicationContext)
                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                // dispatcherServlet.setApplicationContext(this);
                // 주석처리를 하여도 잘 동작이된다!! >> 스프링컨테이너가 Dispatcher servlet에 Applcation context가 필요함을 이해하고 주입해준다.
                // Dispatcher Servlet은 ApplcationContextAware라는 인터페이스를 구현하여 만들었는데
                // setApplicationContext는 컨테이너가 빈을 등록하고 관리하는 중에 컨테이너가 관리하는 오브젝트를 빈에다가 주입해주는 라이프 사이클 메소드이다.
                // ApplcationContextAware 종류의 인터페이스를 구현하고 있으면 스프링 컨테이너는 이것을 setter method를 통해 주입을 해준다.


                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    // FrontController를 이용하여 Mapping(uri), Binding(parameter) 하였는데 모든 정보를 우리가 직접 입력할 순 없다.
                    // 스프링을 이용하여 다른 전략으로 바꿀 수 있다
                    // 이 상태로 실행하게 되면 에러가 발생한다
                    // Dispatcher에게 어떠한 Bean으로 요청을 전달할지 알려주지 않았기 때문이다.
                    // 예전에는 xml에 명시하기도 하였는데 현재는 Mapping 정보를 Controller에 넣는 방법이 쓰인다.
                    servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                            .addMapping("/*"); // 서블릿을 추가할때 서블릿 컨테이너가 요청이 들어올때 어느 서블릿에 매핑해줘야할지 결정하는 매핑을 추가한다.
                });
                webServer.start();
            }
        };
        // Bean오브젝트를 가진 클래스를 등록하는 과정이 필요하다. (Helloboot2Application
        applicationContext.register(Helloboot2Application.class);
        applicationContext.refresh();
    }

}