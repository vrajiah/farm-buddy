package com.farm.buddy.controller.impl;

import com.farm.buddy.controller.BuddyController;
import com.farm.buddy.exception.BadRequestException;
import com.farm.buddy.exception.InternalServerErrorException;
import com.farm.buddy.model.CropName;
import com.farm.buddy.model.Report;
import com.farm.buddy.service.BuddyService;
import com.farm.buddy.service.ValidationService;
import com.farm.buddy.utils.BuddyUtils;
import com.github.jasminb.jsonapi.ResourceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class BuddyControllerImpl implements BuddyController {
    private final Logger logger = LogManager.getLogger(BuddyControllerImpl.class);

    @Autowired
    private BuddyService buddyService;

    @Autowired
    private ValidationService validationService;

    public BuddyControllerImpl(BuddyService buddyService, ValidationService validationService) {
        this.buddyService = buddyService;
        this.validationService = validationService;
    }

    @Override
    public ResponseEntity predict(CropName crop, String mobile) {
        try {
            logger.info("Request received to predict usage for crop {} by mobile {}", crop, mobile);
            // validation of inputs
            validationService.validateMobile(mobile);
            logger.debug("Validation of request completed.");

            // predict water required based on weather data and sensor inputs
            Report report = buddyService.predict(crop, mobile);
            logger.info("Prediction completed, processing response to form json spec.");

            // Create a converter for Report and get jsonSpec string
            ResourceConverter converter = new ResourceConverter(Report.class);
            String response = BuddyUtils.getJsonSpecString(converter, report);
            logger.debug("Json Spec Response: {}", response);
            return ResponseEntity.ok(response);
        } catch (InternalServerErrorException ise) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ise.getError());
        } catch (BadRequestException br) {
            return ResponseEntity.badRequest().body(br.getError());
        }
    }
}
