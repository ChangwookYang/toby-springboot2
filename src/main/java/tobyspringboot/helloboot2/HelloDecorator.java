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

    // Proxy 패턴
    // 실체가 존재하는데 실체 대신에 앞에 대신할 것을 놓는다.
    // HelloController 입장에서는 Proxy가 실제 로직을 갖고 있는 오브젝트를 보는것처럼 알고있지만
    // 실제로는 이를 대신해서 부가적인 효과를 준다.
    // 사용 예로는 실제 오브젝트가 비용도 많고 큰 작업일 경우에 서버가 시작될때 미리 만들어놓을필요 없이
    // 요청이 실제로 들어온 시점에 지연 시켜서 로딩한다. (lazy loading)
    // 또한, 로컬에 구현했다고 생각했지만 먼 곳에 있는 서버 일 수 있는데. 로컬의 시스템은 서버의 위치를 전혀 신경쓰지 않도록 대리자 역할을 할 수 있다.
    // 그 이외에도 access control, jpa, 보안 등 다양한 유연함을 제공할 수 있다.
}
