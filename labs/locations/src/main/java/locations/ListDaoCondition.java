package locations;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ListDaoCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if(conditionContext.getEnvironment().containsProperty("mode")) {
            String mode = conditionContext.getEnvironment().getProperty("mode");
            return mode != null && !mode.equals("dummy");
        } else {
            return true;
        }
    }
}
