package spring.di;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class PosfixCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (context.getEnvironment().containsProperty("postfix")) {
            return Boolean.parseBoolean(context.getEnvironment().getProperty("postfix"));
        }
        else {
            return false;
        }
    }
}
