package org.task.resource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.task.security.UserSecurity;

import java.util.Map;

@RestController
public class SecurityResource {

    @GetMapping("/info")
    public Map<String, String> currentUser(@AuthenticationPrincipal UserSecurity user) {
        return Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getUsername()
        );
    }
}
