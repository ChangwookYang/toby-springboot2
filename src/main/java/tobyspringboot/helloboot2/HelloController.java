package tobyspringboot.helloboot2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@RequestMapping("/hello")
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    // GetMapping("/hello")를 추가하게되면
    // DispatcherServlet은 Bean을 다 뒤져서 요청정보를 추출해낸다.
    // DispatcherServlet은 웹 컨트롤러인지 구분을 해야하는데
    // GetMapping만 적어놓을경우 method레벨이므로 DispatcherServlet이 모든 메소드를 다 뒤질수는 없다.
    // 따라서 클래스 레벨에도 @RequestMapping이 필요하다.
    // 여기서 GET /hello를 실행하게되면 Mapping은 정상적으로 되었으나 에러가 발생하게 된다.

    // 바로 String Return 타입일 경우에는 view의 이름을 노출해주는 것으로 DispatcherServlet은 인식하게 된다.
    // View를 찾으려고 하는데 원하는 뷰가 없어서 에러를 리턴하게 된다.
    // View 대신에 Body에 넣어서 동작하게 하려면 @ResponseBody의 추가가 필요하다.
    // @RestController Annotation을 클래스 레벨에 붙이게되면 DispatcherServlet은 응답을 view가 아닌 Body에 넣어서 응답하는 것으로 인식하게 된다.
    // (@ResponseBody가 붙은것으로 인식한다.)
    @GetMapping
    @ResponseBody
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
