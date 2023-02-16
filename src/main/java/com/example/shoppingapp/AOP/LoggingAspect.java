package com.example.shoppingapp.AOP;

import com.example.shoppingapp.domain.entity.Orders;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @After("com.example.shoppingapp.AOP.PointCuts.inDAOLayer()")
    public void logUserOrderEndTime(JoinPoint joinPoint){
        logger.info("User places the order at: " + String.valueOf(LocalDateTime.now()));
    }

    @After("com.example.shoppingapp.AOP.PointCuts.inControllerLayer()")
    public void logSellerActionEndTime(JoinPoint joinPoint){
        logger.info("Seller update the order at: " + String.valueOf(LocalDateTime.now()));
    }




//    @AfterReturning(value = "com.beaconfire.springaop.AOPDemo.AOP.PointCuts.inDAOLayer()", returning = "res")
//    public void logReturnObject(JoinPoint joinPoint, Object res){
//        logger.info("From LoggingAspect.logReturnObject in DAO: " + res + ": " + joinPoint.getSignature());
//    }
//
//    @AfterThrowing(value = "com.beaconfire.springaop.AOPDemo.AOP.PointCuts.inControllerLayer()", throwing = "ex")
//    public void logThrownException(JoinPoint joinPoint, Throwable ex){
//        logger.error("From LoggingAspect.logThrownException in controller: " + ex.getMessage() + ": " + joinPoint.getSignature());
//    }

//    @Around("com.example.shoppingapp.AOP.PointCuts.inDAOLayer()")
//    public Orders logStartAndEndTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        // before
//        logger.info("From LoggingAspect.logStartAndEndTime: " + proceedingJoinPoint.getSignature());
//        logger.info("Start time: " + System.currentTimeMillis());
//
//        //Invoke the actual object
//        Orders orders = (Orders) proceedingJoinPoint.proceed();
//
//        // after
//        logger.info("End time: " + System.currentTimeMillis());
//        return orders;
//    }
}
