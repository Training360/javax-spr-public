package locations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Aspect
public class CounterAspect {

    private Map<String, AtomicInteger> numLocationsWithCharMap = new HashMap<>();

    @Around("execution(* locations.LocationService.createLocation(..))")
    public Object inc(ProceedingJoinPoint pjp) throws Throwable {
        Object[] params = pjp.getArgs();
        if(params.length != 0 && params[0] instanceof String) {
            String name = String.valueOf(params[0]);
            String firstChar = name.substring(0, 1).toLowerCase();
            if(numLocationsWithCharMap.containsKey(firstChar)) {
                numLocationsWithCharMap.get(firstChar).incrementAndGet();
            } else {
                numLocationsWithCharMap.put(firstChar, new AtomicInteger(1));
            }
        }
        return pjp.proceed(params);
    }

    public Map<String, AtomicInteger> getMap() {
        return numLocationsWithCharMap;
    }

    public void reset() {
        numLocationsWithCharMap.clear();
    }
}
