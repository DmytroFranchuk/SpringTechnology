package de.telran.SpringTechnologyBankApp.aspects;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AspectController {

//    @Before("execution(* de.telran.SpringTechnologyBankApp.controllers..*(..))")
//    public void beforeControllerMethodExecution(JoinPoint joinPoint) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//                .currentRequestAttributes()).getRequest();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        System.out.println("Path: " + request.getRequestURI());
//        System.out.println("Username: " + authentication.getPrincipal());
//    }
}