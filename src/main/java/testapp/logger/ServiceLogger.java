package testapp.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServiceLogger {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* testapp.service.MessageService.saveMessageToDb(testapp.entitie.Message))")
    public void methodToWriteInDB() { }

    @Pointcut("execution(* testapp.service.MessageService.writeToQueue(testapp.entitie.Message))")
    public void methodToWriteInQueue() { }

    @Pointcut("execution(* testapp.service.MessageService.getMessages(..))")
    public void methodGetMessages() { }

    @After("methodGetMessages()")
    public void afterAdviceGet(JoinPoint joinPoint) {
        logger.info("Get messages from database with parameters: " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("methodToWriteInDB()")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("Write to database entity: " + joinPoint.getArgs()[0]);
    }

    @Before("methodToWriteInQueue()")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("Write to queue: " + joinPoint.getArgs()[0]);
    }
}
