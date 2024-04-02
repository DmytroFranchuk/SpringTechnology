package de.telran.SpringTechnologyBankApp.aspects;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AspectController {

    @Before("execution(* de.telran.SpringTechnologyBankApp.controllers..*(..))")
    public void beforeControllerMethodExecution(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "anonymousUser";
//        String password = getPasswordFromRequest(request);

        System.out.println("Path: " + request.getRequestURI());
        System.out.println("Username: " + username);
//        System.out.println("Check Password: " + checkPassword(
//                "1212",
//                "{bcrypt}$2a$10$ub2PkOvIFAKgsx4zdFhwLOn0HAiqOJoSMd9LfesbZZ81m8eQ6SInK"
//        ));
    }

    private String getPasswordFromRequest(HttpServletRequest request) {
        return request.getParameter("password");
    }

}
