package com.orange.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author orange
 */
@RestController
public class IndexController {
    //支持校验角色，角色不用加上“ROLE_”
    @PreAuthorize("hasRole('user')")
    // 角色中需要加上“ROLE_”
    //@Secured({"ROLE_user"})
    @RequestMapping("/whoim")
    public Object whoIm() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
