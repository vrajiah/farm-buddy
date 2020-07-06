package com.farm.buddy.utils;

public enum BuddyErrors {
    INVALID_MOBILE("FB-10001", "Invalid Mobile #, should be 10 digits. Supported only for India for now."),
    WEATHER_DATA_ERROR("FB-10002", "Weather data is missing or file not found."),
    JSON_SERIALIZATION_ERROR("FB-10003", "Unable to process response to generate a json spec response.");

    private String errorCode;
    private String errorMessage;

    BuddyErrors(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
