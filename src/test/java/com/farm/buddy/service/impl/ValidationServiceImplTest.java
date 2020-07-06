package com.farm.buddy.service.impl;

import com.farm.buddy.exception.BadRequestException;
import com.farm.buddy.service.ValidationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ValidationServiceImplTest {
    private ValidationService validationService;

    public ValidationServiceImplTest() {
       validationService = new ValidationServiceImpl();
    }

    @Test
    void validationFailureTest() {
        try {
            validationService.validateMobile("123456");
        } catch(BadRequestException bre){
            Assert.assertTrue(true);
        } catch(Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    void validationTest() {
        try {
            validationService.validateMobile("1234567890");
        } catch(BadRequestException bre){
            Assert.assertTrue(false);
        }
    }
}
