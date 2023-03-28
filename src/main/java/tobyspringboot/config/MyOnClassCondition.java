package tobyspringboot.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

import java.util.Map;

public class MyOnClassCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalMyOnClass.class.getName());
        String value = (String) attributes.get("value");
        // value에 해당하는 class가 존재하면 true, 존재하지 않으면 false
        return ClassUtils.isPresent(value, context.getClassLoader());
    }
}
