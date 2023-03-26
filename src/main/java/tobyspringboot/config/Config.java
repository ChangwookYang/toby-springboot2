package tobyspringboot.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    // ServletWebServerFactory, DispatcherServlet 오브젝트들을
    // @Component의 Meta annotation인 @Configuration을 선언하여 ComponentScan의 대상으로 지정한다.

    // 자동 설정을 위한 작업
    // 1. package를 이동시켜서 @ComponentScan 대상으로부터 제외시켰다.
    // 2. MySpringBootAnnotation 메타 어노테이션에 @Import 로 해당 Config파일을 참조할 수 있다.
    // 3. 각 Bean들의 역할별로 파일을 구분한다.
    // 4. MySpringBootAnnotation에 각 Configuration 들을 Import 시킨다.

}
