package com.farm.buddy.service.impl;

import com.farm.buddy.exception.InternalServerErrorException;
import com.farm.buddy.model.CropName;
import com.farm.buddy.model.Report;
import com.farm.buddy.model.SoilType;
import com.farm.buddy.service.BuddyService;
import com.farm.buddy.service.WeatherService;
import com.farm.buddy.traits.Crop;
import com.farm.buddy.traits.Soil;
import com.farm.buddy.traits.Weather;
import com.farm.buddy.utils.BuddyErrors;
import com.farm.buddy.utils.SMS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Configuration
@PropertySource("classpath:/arduino/soil-sensor-data.properties")
public class BuddyServiceImpl implements BuddyService {
    private final Logger logger = LogManager.getLogger(BuddyServiceImpl.class);

    private Environment sensorData;
    private String apiKey;
    private WeatherService weatherService;

    public BuddyServiceImpl(@Autowired Environment sensorData,  @Autowired WeatherService weatherService,
                            @Value( "${sms.apiKey}" ) String apiKey) {
        this.sensorData = sensorData;
        this.apiKey = apiKey;
        this.weatherService = weatherService;
    }

    @Override
    public Report predict(CropName crop, String mobile) {
        // Create default report
        UUID uuid = UUID.randomUUID();
        Report report = new Report(uuid.toString(), mobile, crop);

        // Get soil type from arduino sensor data (file)
        String soilType = null;
        if(sensorData.containsProperty("sensor.soil.type")) {
            soilType = sensorData.getProperty("sensor.soil.type");
        }
        logger.debug("Soil type from sensor data is {}", soilType);

        // Get soil moisture from arduino sensor data
        int soilMoisture = 0;
        if(sensorData.containsProperty("sensor.soil.moisture")) {
            soilMoisture = Integer.parseInt(sensorData.getProperty("sensor.soil.moisture"));
        }
        logger.debug("Soil moisture from sensor data is {}", soilMoisture);

        // Set soil moisture in report
        report.setSoilMoisture(soilMoisture);

        // Get weather traits from historic data for the current month
        Weather weatherTraits = weatherService.getWeatherData();
        if (weatherTraits == null) {
            throw new InternalServerErrorException(BuddyErrors.WEATHER_DATA_ERROR);
        }
        logger.debug("Weather Traits::average precipitation is {}", weatherTraits.getAvgPrecipitation());
        logger.debug("Weather Traits::average evapotranspiration is {}", weatherTraits.getAvgEvapotranspiration());

        // Set weather data in report
        report.setAvgPrecipitation(weatherTraits.getAvgPrecipitation());
        report.setAvgEvapotranspiration(weatherTraits.getAvgEvapotranspiration());

        // set user's soil traits
        Soil soilTraits = new Soil();
        soilTraits.setMoisture(soilMoisture);
        if (SoilType.Clay.equals(SoilType.valueOf(soilType))) {
            soilTraits.setPercolation(4);
        }
        logger.debug("Soil percolation is {}", soilTraits.getPercolation());

        // Set user's crop factors
        Crop cropTraits = new Crop();
        if(CropName.Paddy.equals(crop)) {
            cropTraits.setCropFactor(1.1);
        }
        logger.debug("Crop factor {}", cropTraits.getCropFactor());

        // Predict the water required for farm based on crop, soil traits and weather historic data
        int litres = getRequiredWater(weatherTraits, soilTraits, cropTraits);
        logger.info("Water required {} in litres", litres);

        String message = "Water required for your " + crop + " field is " + litres + " litres per day for an Acre";
        // Send message to the mobile provided
        SMS sms = new SMS(apiKey);
        sms.send(mobile, message);

        // Set the message in the report
        report.setMessage(message);
        return report;
    }

    /**
     * @param weather weather traits for the current month from historic info
     * @param soil user soil traits based on type, moisture and percolation details
     * @param crop user crop traits with crop factor
     * @return required amount of water for the crop based on weather, soil and crop
     */
    private int getRequiredWater(Weather weather, Soil soil, Crop crop) {
        int litres = (int) (((((weather.getAvgEvapotranspiration() * crop.getCropFactor())
                + soil.getPercolation()) - ((double)weather.getAvgPrecipitation() / 30)) / 10) * 442860) / 1000;

        double soilMoistureCover = (double) (100 - soil.getMoisture()) / 100;
        return (int) (litres * soilMoistureCover);
    }
}
