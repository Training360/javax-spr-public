package spring.di;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Aspect
public class CounterAspect {

    private AtomicInteger counter = new AtomicInteger();

    @Before("execution(* spring.di.EmployeeService.*(..))")
    public void inc() {
        counter.incrementAndGet();
    }

    public int getCounter() {
        return counter.get();
    }

    public void reset() {
        counter.set(0);
    }
}
