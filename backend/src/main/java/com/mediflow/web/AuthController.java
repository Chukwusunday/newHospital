package com.mediflow.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    @GetMapping("/me") public Map<String,Object> me(Authentication authentication){
        Map<String,Object> result=new LinkedHashMap<>();
        result.put("username",authentication.getName());
        List<String> roles=new ArrayList<>();
        authentication.getAuthorities().forEach(a->roles.add(a.getAuthority().replace("ROLE_","")));
        result.put("roles",roles);
        return result;
    }
}
