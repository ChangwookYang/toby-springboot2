package tobyspringboot.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MyAutoConfigurationImportSelector.class) // Configuration 어노테이션이 붙은 클래스들을 구성정보에 직접 추가할 수 있다.
public @interface EnableMyAutoConfiguration {
}
