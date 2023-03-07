package tobyspringboot.helloboot2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;

@SpringBootApplication
public class Helloboot2Application {

    public static void main(String[] args) {
        // [서블릿 컨테이너 띄우기]
        // 방법 1 new Tomcat().start(); <<< 단순히 실행되지 않고 부가적인 설정정보 들이 필요하다.
        // new Tomcat().start();

        // 방법 2 스프링 부트가 톰캣 서블릿 컨테이너를 내장해서 코드로 쉽게 사용할수 있게 만든 도우미 클래스가 있다.
        // TomcatServletWebServerFactory의 인스턴스를 생성해서 웹서버를 시작할 수 있다.
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer();
        webServer.start();
    }

}