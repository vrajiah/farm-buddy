package com.farm.buddy.service.impl;

import com.farm.buddy.service.ValidationService;
import com.farm.buddy.exception.BadRequestException;
import com.farm.buddy.utils.BuddyErrors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    private final Logger logger = LogManager.getLogger(ValidationServiceImpl.class);

    @Override
    public void validateMobile(String mobile) {
        logger.debug("Validating mobile.");
        if(mobile == null || mobile.length() < 10){
            throw new BadRequestException(BuddyErrors.INVALID_MOBILE);
        }
    }
}
