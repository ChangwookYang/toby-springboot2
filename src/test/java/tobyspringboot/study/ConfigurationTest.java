package tobyspringboot.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
    @Test
    void configuration() {
        /**
         *
         다음과 같은 경우에는 common을 각 Bean에서 생성하므로 Common의 주소가 다르게 되어 테스트에 실패하게 된다.
         MyConfig myConfig = new MyConfig();
         Bean1 bean1 = myConfig.bean1();
         Bean2 bean2 = myConfig.bean2();

         Assertions.assertThat(bean1.common).isSameAs(bean2.common);
         */

        // 아래와 같은 경우는 동일하게 되어 성공하게 된다.
        // 스프링 Bean에 등록되어서 동작할때는 일반적인 자바 구현방식과 다르다. (자바는 주소값이 다르다.)

        // proxyBeanMethods의 값이 true 일 경우에는
        // MyConfig가 직접 빈으로 등록되는게 아니라 MyConfig의 Proxy가 Bean으로 등록되게 된다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }


    // Proxy가 내부에서 어떻게 구현되어 있는지 확인하기 위한 구현
    // Proxy가 없다면 isSameAs는 실패로 처리될 것이다
    // proxyCommonMethod = false 라는 것은 @Configuration에 할당된 Config들이 Proxy로 만들어지지 않고 할당된다는 것을 의미한다.
    // 등록하는 Bean에서 다른 Bean을 생성하지 않을때 굳이 Proxy를 선언할 필요가 없으므로 해당 옵션이 false로 처리될 수 있다.
    @Test
    void proxyCommonMethod() {
        MyConfigProxy myConfigProxy = new MyConfigProxy();

        Bean1 bean1 = myConfigProxy.bean1();
        Bean2 bean2 = myConfigProxy.bean2();

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    static class MyConfigProxy extends MyConfig {
        private Common common;

        @Bean
        Common common() {
            // common이 생성되어 있다면 common을 캐싱하도록 하였다.
            if (this.common == null) this.common = super.common();

            return this.common;
        }
    }


    @Configuration
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    static class Bean1 {
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }


    private static class Common {

    }

    // Bean1과 Bean2는 Common을 의존한다.
    // Bean1 <-- Common
    // Bean2 <-- Common
}
