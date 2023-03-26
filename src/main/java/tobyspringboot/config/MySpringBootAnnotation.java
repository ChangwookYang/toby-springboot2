package tobyspringboot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Retention의 Default값은 Class이다.
// Class의 의미는 컴파일된 클래스 파일까지는 살아있지만, 어노테이션이 달려 있는 클래스를 런타임에 로딩할때는 그 정보가 사라진다.
// Runtime에도 살아있어야하기때문에 Retention을 Runtime으로 지정하였다.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // class, interface, enum에 부여할 수 있는 어노테이션을 만들때 타깃을 Type으로 한다.
@Configuration
@ComponentScan
// @Import({DispatcherServletConfig.class, TomcatWebServerConfig.class}) // Configuration 어노테이션이 붙은 클래스들을 구성정보에 직접 추가할 수 있다.
@EnableMyAutoConfiguration // @Import 정보들을 별도 메타데이터를 만들었다.
public @interface MySpringBootAnnotation {
}
