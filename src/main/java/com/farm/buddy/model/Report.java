package com.farm.buddy.model;

import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

import java.io.Serializable;

@Type("report")
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String mobile;
    private CropName crop;
    private int soilMoisture;
    private int avgPrecipitation;
    private int avgEvapotranspiration;
    private String message;

    public Report(String id, String mobile, CropName crop) {
        this.id = id;
        this.mobile = mobile;
        this.crop = crop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CropName getCrop() {
        return crop;
    }

    public void setCrop(CropName crop) {
        this.crop = crop;
    }

    public int getAvgPrecipitation() {
        return avgPrecipitation;
    }

    public void setAvgPrecipitation(int avgPrecipitation) {
        this.avgPrecipitation = avgPrecipitation;
    }

    public int getAvgEvapotranspiration() {
        return avgEvapotranspiration;
    }

    public void setAvgEvapotranspiration(int avgEvapotranspiration) {
        this.avgEvapotranspiration = avgEvapotranspiration;
    }

    public int getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(int soilMoisture) {
        this.soilMoisture = soilMoisture;
    }
}
