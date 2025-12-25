package com.sweet.acl_jwt.config;

import com.sweet.acl_jwt.entity.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@EnableJpaAuditing()
public class JpaAuditingConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (Objects.equals(authentication.getPrincipal(), "anonymousUser")){
                return Optional.of("anonymousUser");
            }
//            UserCustom principal = (UserCustom) authentication.getPrincipal();
//            return Optional.of(principal.getUsername());
            UserEntity principal = (UserEntity) authentication.getPrincipal();
            return Optional.of(principal.getUsername());
        }

        return Optional.of("");
    }
}
