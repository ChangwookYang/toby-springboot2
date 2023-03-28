package tobyspringboot.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

import java.util.Map;

/*
스프링부트가 제공하는 Conditional에 대해서 알고있어야 하는 이유는
스프링부트가 제공하는 자동구성을 파악해야하는 경우가 있다.

어떠한 조건을 만족하면 어떠한 빈이 등록되는구나를 알아야 해당 빈을 가져다쓰거나
필요한 경우에 Custom Bean을 만들어서 대체할 수 있다.

혹은 스프링에서 제공하지 않는 제 3의 기술을 구현하기 위해서 자동구성 형태로 만들기 위해서는
어노테이션의 확장에 대해서 잘알고 있어야한다.
*/
public class MyOnClassCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalMyOnClass.class.getName());
        String value = (String) attributes.get("value");
        // value에 해당하는 class가 존재하면 true, 존재하지 않으면 false
        return ClassUtils.isPresent(value, context.getClassLoader());
    }
}
