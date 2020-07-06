package com.farm.buddy.traits;

import com.farm.buddy.service.impl.WeatherServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class Weather {
    private final Logger logger = LogManager.getLogger(WeatherServiceImpl.class);

    private int avgPrecipitation;
    private int avgEvapotranspiration;

    public int getAvgPrecipitation() {
        return avgPrecipitation;
    }

    public int getAvgEvapotranspiration() {
        return avgEvapotranspiration;
    }

    public void setAvgPrecipitation(int month) throws IOException {
        avgPrecipitation = getAverage("/weather/Precipitation.csv", month);
        logger.debug("Average Precipitation for the month {} is {}", month, avgPrecipitation);
    }

    public void setAvgEvapotranspiration(int month) throws IOException {
        avgEvapotranspiration = getAverage("/weather/Evapotranspiration.csv", month);
        logger.debug("Average Evapotranspiration for the month {} is {}", month, avgEvapotranspiration);
    }

    /**
     * @param fileName
     * @param monthIndex
     * @return returns average of month from data set
     */
    private int getAverage(String fileName, int monthIndex) throws IOException {
        int average = 0;
        BufferedReader br = null;
        Resource resource = new ClassPathResource(fileName);
        try {
            String line = "";
            String cvsSplitBy = ",";

            br = new BufferedReader(new FileReader(resource.getFile()));
            int i = 0;
            int sum = 0;
            while ((line = br.readLine()) != null) {
                i++;
                // use comma as separator
                String[] entries = line.split(cvsSplitBy);
                sum = (int) (sum + Double.parseDouble(entries[monthIndex]));
            }
            if(i > 0) {
                average = sum/i;
            }
        }  finally {
            if(br != null) {
                br.close();
            }
        }
        return average;
    }
}
