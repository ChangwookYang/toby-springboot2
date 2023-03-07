package tobyspringboot.helloboot2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
public class Helloboot2Application {

    public static void main(String[] args) {
        // [서블릿 컨테이너 띄우기]
        // 방법 1 new Tomcat().start(); <<< 단순히 실행되지 않고 부가적인 설정정보 들이 필요하다.
        // new Tomcat().start();

        // 방법 2 스프링 부트가 톰캣 서블릿 컨테이너를 내장해서 코드로 쉽게 사용할수 있게 만든 도우미 클래스가 있다.
        // TomcatServletWebServerFactory의 인스턴스를 생성해서 웹서버를 시작할 수 있다.

        // [서블릿 등록 (Mapping) - 서블릿을 서블릿 컨테이너에 매핑]
        // getWebServer로 webServer를 만들때에 ServletContextInitializer 에서 서블릿을 등록해준다.
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            // hello라는 서블릿을 등록한다. 첫번째는 서블릿명, 두번째는 HttpServlet을 상속하는 익명 클래스 오브젝트를 만들어서 service 메소드를 구현한다.
            // service에서는 request, response가 있고 언제나 요청이 있어야 응답이 있다.
            servletContext.addServlet("hello", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    // super.service(req, resp);
                    // 웹 응답의 3대 요소 리 (상태코드 / 헤더 / 바디)
                    resp.setStatus(200);
                    resp.setHeader("Content-Type", "text/plain");
                    resp.getWriter().println("Hello Servlet "); // BODY
                }
            }).addMapping("/hello"); // 서블릿을 추가할때 서블릿 컨테이너가 요청이 들어올때 어느 서블릿에 매핑해줘야할지 결정하는 매핑을 추가한다.
        });
        webServer.start();
    }

}