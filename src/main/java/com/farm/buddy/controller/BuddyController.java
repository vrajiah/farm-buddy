package com.farm.buddy.controller;

import com.farm.buddy.model.CropName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface BuddyController {
    @RequestMapping(
            value = "/predict",
            method = RequestMethod.GET,
            produces = "application/vnd.farm.buddy.api.v1+json"
    )
    public @ResponseBody
    ResponseEntity predict(@RequestParam(required = true) CropName crop,
                           @RequestParam(required = true) String mobile);
}
