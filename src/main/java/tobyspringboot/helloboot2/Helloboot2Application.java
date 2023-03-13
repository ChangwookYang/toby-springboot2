package tobyspringboot.helloboot2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

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

        // [프론트 컨트롤러]
        // 원래는 서블릿은 각 url에 맞게 매핑을해서 각 url이 맡아서 처리하는 방식으로 중복된 코드가 많았다.
        // 모든 서블릿에 공통적으로 등장하는 코드를 중앙화된 제일 앞단에 존재하는 컨트롤러라는 하는 오브젝트에서
        // 공통적인 업무를 다 처리하고 요청의 종류에 따라 다른 오브젝트에 위임한다.
        // 후처리도 마찬가지로 프론트 컨트롤러가 처리한다.
        // 대표적으로 인증이나 보안, 다국어 처리, 모든 요청에 대해서 공통적으로 리턴해줘야하는 내용 등을 프론트 컨트롤러가 처리하는게 일반적이다.
        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {

            // HelloController 주석 처리 -> 직접 매핑, 바인딩하지 않고 Spring Container를 사용한다.
            // HelloController helloController = new HelloController();

            // 스프링 컨테이너를 대표하는 interface로 ApplicationContext가 있다.
            // 어떤 빈이 들어갈 것인가 리소스에 접근, 이벤트, 구독하는 방법 등의 기능을 구현해야하는 ApplicationContext 인터페이스
            GenericApplicationContext applicationContext = new GenericApplicationContext();
            applicationContext.registerBean(HelloController.class);

            // 어떤 클래스를 가지고 Bean을 만들지 알아야 하기때문에 인터페이스가 아닌 클래스를 넣어준다.
            // HelloController.class 생성자 파라미터로 SimpleHelloService.class를 받아 생성하고 getBean으로 받아서 사용해야하는데
            // 주입을 해주는 것은 어떻게 알 수 있을까? 스프링 컨테이너 HelloService를 구현한 클래스가 있는지 파악하고 HelloController에 주입해준다.
            applicationContext.registerBean(SimpleHelloService.class);
            applicationContext.refresh(); // ApplcationContext가 Bean object들을 만들어주게 된다.

            // hello라는 서블릿을 등록한다. 첫번째는 서블릿명, 두번째는 HttpServlet을 상속하는 익명 클래스 오브젝트를 만들어서 service 메소드를 구현한다.
            // service에서는 request, response가 있고 언제나 요청이 있어야 응답이 있다.
            servletContext.addServlet("frontcontroller", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    // super.service(req, resp);
                    // name request를 받아서 동적인 응답값을 내리도록 수정
                    // 인증, 보안, 다국어, 공통 기능
                    // 프론트 컨트롤러에서 이제 **매핑과 **바인딩 기능을 가져야한다.
                    // 매핑 : URI와 Method 형태를 맞춰준다.
                    if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {

                        // 바인딩 : request를 알맞은 형태로 바인딩(Dto)
                        String name = req.getParameter("name");

                        // HelloController에 동작을 위임하였다.
                        // 처리한 결과를 Message의 Body에 입력하였다.
                        // HelloController가 어떻게 생성되었는지 모르고 applicationContext를 통해 Bean을 가져오게 된다.
                        HelloController helloController = applicationContext.getBean(HelloController.class);
                        String ret = helloController.hello(name);

                        // 웹 응답의 3대 요소 리 (상태코드 / 헤더 / 바디)
                        resp.setStatus(200);
                        resp.setHeader("Content-Type", "text/plain");
                        resp.getWriter().println(ret); // BODY
                    } else if (req.getRequestURI().equals("/user")) {
                        // ..
                    } else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
            }).addMapping("/*"); // 서블릿을 추가할때 서블릿 컨테이너가 요청이 들어올때 어느 서블릿에 매핑해줘야할지 결정하는 매핑을 추가한다.
        });
        webServer.start();
    }

}