package com.farm.buddy.service.impl;

import com.farm.buddy.service.WeatherService;
import com.farm.buddy.traits.Weather;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class WeatherServiceImplTest {

    private WeatherService weatherService;
    private Weather weather;

    public WeatherServiceImplTest() {
        weather = Mockito.mock(Weather.class);
        weatherService = new WeatherServiceImpl(weather);
    }

    @Test
    void getWeatherDataFailTest() {
        try {
            Mockito.doThrow(IOException.class).when(weather).setAvgPrecipitation(6);
            weatherService.getWeatherData();
        } catch (IOException ioe) {
            Assert.assertTrue(ioe.getClass().getSimpleName().equals(IOException.class.getSimpleName()));
        }
    }
}
