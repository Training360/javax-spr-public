package spring.di;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BenchmarkAspect {

    @Around("execution(* spring.di.EmployeeService.*(..))")
    public Object doBenchmark(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        Object[] params = pjp.getArgs();
        if (params.length == 1 && params[0] instanceof String) {
            params[0] = ((String) params[0]).toUpperCase();
        }

        Object ret = pjp.proceed(params);

        long end = System.currentTimeMillis();

        System.out.println("Method: " + pjp.getSignature().getName() + " " + (end - start));

        return ret;
    }
}
