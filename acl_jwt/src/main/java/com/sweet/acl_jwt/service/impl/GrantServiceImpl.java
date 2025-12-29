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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GrantServiceImpl implements GrantService {
    private final GrantRepository grantRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public GrantDto createGrant(GrantRequest grantRequest) {
        List<Long> userGrantedIds = grantRequest.getUserGrantedIds();
        List<GrantEntity> existingGrant = grantRepository.existsValidGrantEntities(grantRequest.getUserGrantedIds(),
                                                                                    grantRequest.getResourceId(), grantRequest.getAction());
        if(existingGrant.size() != 0) {
            throw new GrantExistedException(ErrorMessage.Grant.GRANT_EXISTED);
        }

        List<GrantEntity> grantList = new ArrayList<>();
        for(Long id : userGrantedIds) {
            GrantEntity grantEntity = new GrantEntity();
            grantEntity.setResourceId(grantRequest.getResourceId());
            grantEntity.setAction(grantRequest.getAction());
            grantEntity.setUserGrantedId(id);
            grantList.add(grantEntity);
        }

        grantRepository.saveAll(grantList);

        GrantDto grantDto = new GrantDto();
        grantDto.setUserGrantedIds(userGrantedIds);
        grantDto.setResourceId(grantRequest.getResourceId());
        grantDto.setAction(grantRequest.getAction());

        return grantDto;
    }
}
