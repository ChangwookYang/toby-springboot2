package tobyspringboot.config.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import tobyspringboot.config.ConditionalMyOnClass;
import tobyspringboot.config.MyAutoConfiguration;

@MyAutoConfiguration
@ConditionalMyOnClass(value = "org.apache.catalina.startup.Tomcat")
public class TomcatWebServerConfig {

    @Bean("tomcatWebServerFactory")
    @ConditionalOnMissingBean // 개발자가 직접 등록한 Bean이 있는지 확인해보고 없을 경우 등록한다.
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }
}
