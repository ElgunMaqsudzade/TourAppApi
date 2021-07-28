package az.code.tourappapi.components;

import az.code.tourappapi.services.interfaces.AppUserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {
    private final AppUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");
        if (auth != null) {
            String[] chunks = auth.split("\\.");
            Base64.Decoder decoder = Base64.getDecoder();
            String data = new String(decoder.decode(chunks[1]));
            JsonNode payload = new ObjectMapper().readValue(data, JsonNode.class);
            String email = payload.get("email").textValue();
            request.setAttribute("user", userService.find(email));
        }
        return true;
    }
}