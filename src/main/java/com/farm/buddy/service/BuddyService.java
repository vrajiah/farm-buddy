package com.farm.buddy.service;

import com.farm.buddy.model.Report;
import com.farm.buddy.model.CropName;

public interface BuddyService {

    /**
     * @param crop name of the crop
     * @param mobile contact # of the user
     * @return report of water required based on weather data
     */
    Report predict(CropName crop, String mobile);
}
