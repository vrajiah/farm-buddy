package com.farm.buddy.controller;

import com.farm.buddy.model.CropName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface BuddyController {
    @GetMapping(
            value = "/predict",
            produces = "application/vnd.farm.buddy.api.v1+json"
    )
    public @ResponseBody
    ResponseEntity predict(@RequestParam(required = true) CropName crop,
                           @RequestParam(required = true) String mobile);
}
