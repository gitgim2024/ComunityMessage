package com.tech.whale.message.global;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component //로그보려면 여기
public class MessageLogAop {

   @Pointcut("within(com.tech.whale.message..*)")//패키지의 모든 메소드
   public void pointcutMethod() {
      
   }
   
   @Around("pointcutMethod()")
   public Object loggerAop(ProceedingJoinPoint jointPoint) throws Throwable{
      
      //공통기능이 적용되는 메소드가 어떤 매소드인지 출력하기위해 메소드 명을 얻어내기
      String typeNameStr=jointPoint.getSignature().getDeclaringTypeName();
      String methodName = jointPoint.getSignature().getName();
      
      System.out.println(">>[JAVA]"+typeNameStr+": "+methodName+" 시작");
      
      try {
         //핵심기능
         Object obj=jointPoint.proceed();//핵심기능실행
         return obj;
      } finally {
    	  System.out.println(">>[JAVA]"+typeNameStr+": "+methodName+" 종료");
      }
      
   }

}