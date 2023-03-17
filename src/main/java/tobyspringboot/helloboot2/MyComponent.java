package tobyspringboot.helloboot2;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
// 별도의 메타 어노테이션을 생성하여 Component를 사용할 수 있다.
// 어떠한 기능을 실행하는 Component인지 개발자가 구분하고 싶은 경우가 있기때문이다.

public @interface MyComponent {
}
