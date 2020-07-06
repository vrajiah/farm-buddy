package com.farm.buddy.service.impl;

import com.farm.buddy.exception.InternalServerErrorException;
import com.farm.buddy.model.CropName;
import com.farm.buddy.model.Report;
import com.farm.buddy.model.SoilType;
import com.farm.buddy.service.BuddyService;
import com.farm.buddy.service.WeatherService;
import com.farm.buddy.traits.Weather;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;

import java.io.IOException;

class BuddyServiceImplTest {
    public static String MOBILE_NUMBER = "1234567890";

    private BuddyService buddyService;
    private WeatherService weatherService;
    private Environment sensorData;

    public BuddyServiceImplTest() {
        sensorData = Mockito.mock(Environment.class);
        weatherService = Mockito.mock(WeatherService.class);
        this.buddyService = new BuddyServiceImpl(sensorData, weatherService,"test_Key");
    }

    @Test
    void predictionTest() {
        Mockito.when(sensorData.containsProperty("sensor.soil.type")).thenReturn(true);
        Mockito.when(sensorData.getProperty("sensor.soil.type")).thenReturn(SoilType.Clay.name());

        Mockito.when(sensorData.containsProperty("sensor.soil.moisture")).thenReturn(true);
        Mockito.when(sensorData.getProperty("sensor.soil.moisture")).thenReturn("67");

        Mockito.when(weatherService.getWeatherData()).thenReturn(getWeather());

        Report report = buddyService.predict(CropName.Paddy, MOBILE_NUMBER);
        Assert.assertEquals(67, report.getSoilMoisture());
        String expectedMsg = "Water required for your " + CropName.Paddy + " field is 94 litres per day for an Acre";
        Assert.assertEquals(expectedMsg, report.getMessage());
        Assert.assertEquals(MOBILE_NUMBER, report.getMobile());
    }

    @Test
    void predictionNonClayTest() {
        Mockito.when(sensorData.containsProperty("sensor.soil.type")).thenReturn(true);
        Mockito.when(sensorData.getProperty("sensor.soil.type")).thenReturn(SoilType.Sandy.name());

        Mockito.when(sensorData.containsProperty("sensor.soil.moisture")).thenReturn(true);
        Mockito.when(sensorData.getProperty("sensor.soil.moisture")).thenReturn("25");

        Mockito.when(weatherService.getWeatherData()).thenReturn(getWeather());

        Report report = buddyService.predict(CropName.Wheat, MOBILE_NUMBER);
        Assert.assertEquals(25, report.getSoilMoisture());
        String expectedMsg = "Water required for your " + CropName.Wheat + " field is 116 litres per day for an Acre";
        Assert.assertEquals(expectedMsg, report.getMessage());
        Assert.assertEquals(MOBILE_NUMBER, report.getMobile());
    }

    @Test
    void predictionWeatherFailureTest() {
        Mockito.when(sensorData.containsProperty("sensor.soil.type")).thenReturn(true);
        Mockito.when(sensorData.getProperty("sensor.soil.type")).thenReturn(SoilType.Sandy.name());

        Mockito.when(sensorData.containsProperty("sensor.soil.moisture")).thenReturn(true);
        Mockito.when(sensorData.getProperty("sensor.soil.moisture")).thenReturn("25");

        Mockito.when(weatherService.getWeatherData()).thenReturn(null);
        try {
            buddyService.predict(CropName.Wheat, MOBILE_NUMBER);
        } catch (InternalServerErrorException ise) {
            Assert.assertTrue("FB-10002".equals(ise.getError().getCode()));
        } catch(Exception e) {
            Assert.assertTrue(false);
        }
    }

    private Weather getWeather()  {
        Weather weather = new Weather();
        try {
            weather.setAvgPrecipitation(6);
            weather.setAvgEvapotranspiration(6);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weather;
    }
}
