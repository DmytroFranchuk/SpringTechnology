package de.telran.SpringTechnologyBankApp.controllers.welcome;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ControllerWelcome {
    private final HttpSession session;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Метод для проверки введенного пользователем пароля
    public boolean checkPassword(String rawPassword, String encodedPasswordFromCLI) {
        return passwordEncoder.matches(rawPassword, encodedPasswordFromCLI);
    }

    @GetMapping(value = "/hello")
    public String welcome() {
        return "Welcome to SpringTechnologyBankApp";
    }

    @GetMapping(value = "/test")
    public String test() {
        return "Welcome to Test-Spring-Technology";
    }

    @GetMapping("/echo")
    public String getEchoInfo(@NotNull HttpServletRequest request) {

        boolean checkPassword1 = checkPassword(
                "1212",
                "$2a$10$cs0f0w748.vyBxl8agJcre1DZ2jgkJmA8Cc7YD7FRjUiG9DxXX4Ka"
        );
        boolean checkPassword2 = checkPassword(
                "1212",
                "$2a$10$zd8HWgtHUG6cBVokUYjse.qRkxx6qrJxhcWxRWWUr1Htr5SwgpiZ2"
        );
        boolean checkPassword3 = checkPassword(
                "1212",
                "$2a$10$XB/t3f/RlwZ4wE2Wek9hkuNkBTYRPnHLAqzjxuSn/K525nwULDUR6"
        );

        session.setMaxInactiveInterval(120);
        String ipAddress = request.getRemoteAddr();
        String sessionId = request.getSession().getId();
        String postmanToken = request.getHeader("Postman-Token");
        String localAddr = request.getLocalAddr();

        long creationTime = request.getSession().getCreationTime();
        long accessedTime = request.getSession().getLastAccessedTime();
        int maxInactiveInterval = request.getSession().getMaxInactiveInterval();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode responseJson = objectMapper.createObjectNode();
        responseJson.put("IP-Address-откуда-пришел-запрос", ipAddress);
        responseJson.put("ID-Session", sessionId);
        responseJson.put("Postman-Token", postmanToken);
        responseJson.put("IP-LocalAddress-откуда-пришел-ответ", localAddr);
        responseJson.put("getCreationTime", creationTime);
        responseJson.put("getLastAccessedTime", accessedTime);
        responseJson.put("getMaxInactiveInterval", maxInactiveInterval);
        responseJson.put("Check-Password admin", checkPassword1);
        responseJson.put("Check-Password manager", checkPassword2);
        responseJson.put("Check-Password client", checkPassword3);
        return responseJson.toString();
    }
}