package com.example.shoppingapp.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    @Pointcut("execution(* com.example.shoppingapp.controller.SellerController.*ProcessingOrderToCompleteBySellerAndOrderId(..)))")
    public void inControllerLayer(){}

    @Pointcut("execution(* com.example.shoppingapp.dao.OrderDao.purchaseProduct())")
    public void inDAOLayer(){}
}
