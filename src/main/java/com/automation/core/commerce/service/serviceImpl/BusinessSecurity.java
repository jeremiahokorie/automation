package com.automation.core.commerce.service.serviceImpl;

import com.automation.core.global.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("businessSecurity")
public class BusinessSecurity {

//    public boolean canAccessRegistration(Authentication authentication, Long registrationId) {
//        // Admins can access all
//        if (authentication.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") ||
//                        a.getAuthority().equals("ROLE_SUPERADMIN"))) {
//            return true;
//        }
//
//        // Users can only access their own
//        User currentUser = (User) authentication.getPrincipal();
//        return businessService.isOwner(registrationId, currentUser.getId());
//    }
}