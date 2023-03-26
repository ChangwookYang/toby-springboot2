package tobyspringboot.helloboot2;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary // 주입이 필요한 똑같은 타입의 Bean이 두개가 존재할때 우선적으로 가져다 사용하기 위한 어노테이션
public class HelloDecorator implements HelloService {
    // Decorator 패턴
    // Decorator 패턴은 Contoller와 Service 중간에 위치하여 Controller가 Decorator에 의존하게 만든다.
    // Controller와 Service 사이에서 어떠한 기능을 수행하고자 할때 필요로 한다.
    // Decorator 패턴은 Service를 구현함과 동시에 Service를 구현한 또다른 오브젝트에 대해서 의존해야한다.

    private final HelloService helloService;

    public HelloDecorator(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) {
        return "*" + helloService.sayHello(name) + "*";
    }

}
