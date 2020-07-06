package com.farm.buddy.service.impl;

import com.farm.buddy.service.WeatherService;
import com.farm.buddy.traits.Weather;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final Logger logger = LogManager.getLogger(WeatherServiceImpl.class);

    @Autowired
    private Weather weather;

    public WeatherServiceImpl(Weather weather) {
        this.weather = weather;
    }

    @Override
    public Weather getWeatherData() {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        logger.debug("Getting weather data for month {}", currentMonth);
        try {
            weather.setAvgPrecipitation(currentMonth);
            weather.setAvgEvapotranspiration(currentMonth);
            logger.debug("Weather data processed and created weather traits");
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return weather;
    }
}
