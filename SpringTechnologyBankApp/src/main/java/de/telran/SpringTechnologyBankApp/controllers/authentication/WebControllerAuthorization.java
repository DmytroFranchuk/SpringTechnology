package de.telran.SpringTechnologyBankApp.controllers.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebControllerAuthorization {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "loginPage";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String getLogoutPage(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/login";
    }

}


