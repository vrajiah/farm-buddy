package com.farm.buddy.service;

import com.farm.buddy.traits.Weather;

public interface WeatherService {

    /**
     * @return Weather traits for the current month
     */
    Weather getWeatherData();
}
