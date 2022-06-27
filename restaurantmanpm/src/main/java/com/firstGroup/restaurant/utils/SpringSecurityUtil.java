package com.firstGroup.restaurant.utils;

import com.firstGroup.restaurant.model.dto.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {
    public static AppUserDetails getCurrentUserDetails() {
        return (AppUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }
}
