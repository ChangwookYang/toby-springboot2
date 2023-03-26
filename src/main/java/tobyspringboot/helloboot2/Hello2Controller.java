package tobyspringboot.helloboot2;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class Hello2Controller {
    private final HelloService helloService;
    private final ApplicationContext applicationContext;

    public Hello2Controller(HelloService helloService, ApplicationContext applicationContext) {
        this.helloService = helloService;
        // 결국 스프링 컨테이너는 자기 자신이지만 자기 자신도 Bean으로 처리하여 생성자 주입이 가능하다.
        this.applicationContext = applicationContext;
    }

    @GetMapping("/hello2")
    public String hello(String name) {
        return helloService.sayHello(Objects.requireNonNull(name));
    }

}
