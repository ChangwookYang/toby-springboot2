package tobyspringboot.helloboot2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class Helloboot2Application {

    public static void main(String[] args) {
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
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
        applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(SimpleHelloService.class);

        applicationContext.refresh();
    }

}