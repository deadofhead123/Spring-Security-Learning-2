package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.entity.PolicyEntity;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.entity.UserEntity;
import com.sweet.acl_jwt.repo.GrantRepository;
import com.sweet.acl_jwt.repo.PolicyRepository;
import com.sweet.acl_jwt.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final GrantRepository grantRepo;
    private final PolicyRepository policyRepo;
    private final ExpressionParser parser = new SpelExpressionParser();

    @Override
    public boolean authorize(UserEntity user, ResourceEntity resource, String action) {
        // Grant override
        if (grantRepo.existsValidGrant(user.getId(), resource.getId(), action)) {
            return true;
        }

        List<PolicyEntity> policies = policyRepo.findByResourceTypeAndAction(resource.getType(), action);

        for (PolicyEntity p : policies) {
            if (evaluate(p.getConditionExpression(), user, resource)) {
                return true;
            }
        }

        return false;
    }

    private boolean evaluate(String expr, UserEntity user, ResourceEntity resource) {
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setVariable("user", user);
        ctx.setVariable("resource", resource);
        return Boolean.TRUE.equals(parser.parseExpression(expr).getValue(ctx, Boolean.class));
    }
}
