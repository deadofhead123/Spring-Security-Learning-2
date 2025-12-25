package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.dto.PolicyDto;
import com.sweet.acl_jwt.dto.request.PolicyRequest;
import com.sweet.acl_jwt.entity.PolicyEntity;
import com.sweet.acl_jwt.entity.ResourceEntity;
import com.sweet.acl_jwt.entity.UserEntity;
import com.sweet.acl_jwt.repo.PolicyRepository;
import com.sweet.acl_jwt.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {
    private final PolicyRepository policyRepository;
    private final ModelMapper modelMapper;
    private final ExpressionParser parser = new SpelExpressionParser();

    @Override
    public PolicyDto createPolicy(PolicyRequest policyRequest) {
        PolicyEntity policyEntity = new PolicyEntity();
        policyEntity.setAction(policyRequest.getAction());
        policyEntity.setResourceType(policyRequest.getResourceType());

        // Validate condition expression
        String conditionExpression = policyRequest.getConditionExpression();
        validate(conditionExpression);
        policyEntity.setConditionExpression(conditionExpression);

        return modelMapper.map(policyRepository.save(policyEntity), PolicyDto.class);
    }

    private void validate(String expression) {
        try {
            // Dummy data
            UserEntity user = dummyUser();
            ResourceEntity resource = dummyResource();

            StandardEvaluationContext ctx =
                    new StandardEvaluationContext();

            ctx.setVariable("user", user);
            ctx.setVariable("resource", resource);

            Boolean result = parser.parseExpression(expression).getValue(ctx, Boolean.class);

            if (result == null) {
                throw new IllegalArgumentException(
                        "Expression does not return boolean");
            }

        } catch (ParseException | EvaluationException ex) {
            throw new IllegalArgumentException(
                    "Invalid condition expression: " + ex.getMessage(), ex);
        }
    }

    private UserEntity dummyUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        return user;
    }

    private ResourceEntity dummyResource() {
        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setOwnerId(1L);
        resourceEntity.setVisibility("PUBLIC");
        return resourceEntity;
    }
}
