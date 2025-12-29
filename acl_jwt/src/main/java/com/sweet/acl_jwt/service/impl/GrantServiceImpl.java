package com.sweet.acl_jwt.service.impl;

import com.sweet.acl_jwt.constant.ErrorMessage;
import com.sweet.acl_jwt.dto.GrantDto;
import com.sweet.acl_jwt.dto.request.grant.GrantRequest;
import com.sweet.acl_jwt.entity.GrantEntity;
import com.sweet.acl_jwt.exception.GrantExistedException;
import com.sweet.acl_jwt.repo.GrantRepository;
import com.sweet.acl_jwt.service.GrantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrantServiceImpl implements GrantService {
    private final GrantRepository grantRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public GrantDto createGrant(GrantRequest grantRequest) {
        GrantEntity existingGrant = grantRepository.existsValidGrantEntity(grantRequest.getUserGrantedId(), grantRequest.getResourceId(), grantRequest.getAction());
        if(existingGrant != null) {
            throw new GrantExistedException(ErrorMessage.Grant.GRANT_EXISTED);
        }

        GrantEntity grantEntity = modelMapper.map(grantRequest, GrantEntity.class);
        grantEntity.setId(null);

        return modelMapper.map(grantRepository.save(grantEntity), GrantDto.class);
    }
}
