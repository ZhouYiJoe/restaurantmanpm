package com.firstGroup.restaurant.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppGrantedAuthority implements GrantedAuthority {
    private String authority;
}
