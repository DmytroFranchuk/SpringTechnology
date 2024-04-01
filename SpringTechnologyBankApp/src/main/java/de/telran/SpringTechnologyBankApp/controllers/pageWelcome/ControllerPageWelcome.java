package de.telran.SpringTechnologyBankApp.controllers.pageWelcome;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ControllerPageWelcome {

    private final HttpSession session;

    @GetMapping(value = "/hello")
    public String welcome() {
        return "Welcome to SpringTechnologyBankApp";
    }

    @GetMapping("/echo")
    public String getEchoInfo(HttpServletRequest request) {
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
        responseJson.put("IP Address откуда пришел запрос", ipAddress);
        responseJson.put("ID Session", sessionId);
        responseJson.put("Postman-Token", postmanToken);
        responseJson.put("IP LocalAddress откуда пришел ответ", localAddr);
        responseJson.put("getCreationTime", creationTime);
        responseJson.put("getLastAccessedTime", accessedTime);
        responseJson.put("getMaxInactiveInterval", maxInactiveInterval);
        return responseJson.toString();
    }
}