package com.balancer.service.model.factory;

import com.balancer.service.domain.User;
import com.balancer.service.model.security.UserCredentials;
import org.springframework.security.core.authority.AuthorityUtils;


public class UserCredentialsFactory {

    public static UserCredentials create(User user) {
        return new UserCredentials(
                user.getOid(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getLastPasswordReset(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
        );
    }
}
