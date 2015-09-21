package com.readingroom.aspects;

import com.readingroom.util.common.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

@Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.readingroom.tests..*.*(..))")
    public void anyTestMethodExec() {}

    @Pointcut("@within(com.readingroom.annotations.Loggable)")
    public void annotatedType() {}

    @Around("anyTestMethodExec()")
    public Object logAnyTestMethodExec(ProceedingJoinPoint thisJoinPoint) throws Exception {
        String methodName = thisJoinPoint.getSignature().getName();
        Object[] methodArgs = thisJoinPoint.getArgs();

        Log.DEBUG("Thread:" + Thread.currentThread().getId() + " - Call method " + methodName + " with args " + Arrays.toString(methodArgs));

        Object result;

        try {
            result = thisJoinPoint.proceed();

            Log.DEBUG("Thread:" + Thread.currentThread().getId() + " - Method " + methodName + " returns " + result);
        } catch (Throwable ex) {
            StringWriter error = new StringWriter();
            ex.printStackTrace(new PrintWriter(error));

            Log.INFO("Thread:" + Thread.currentThread().getId() + " - Method " + methodName + " with args " + Arrays.toString(methodArgs) + "\t\nOccurred exception: " + error);

            throw new Exception(ex);  // to fail tests
        }

        return result;
    }
}