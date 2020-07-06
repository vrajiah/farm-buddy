package com.farm.buddy.exception;

import com.farm.buddy.model.Error;
import com.farm.buddy.utils.BuddyErrors;

public class FarmBuddyException extends RuntimeException {
    private final Error error;

    public FarmBuddyException(BuddyErrors errors) {
        this.error = new Error(errors.getErrorCode(), errors.getErrorMessage());
    }

    public Error getError() {
        return error;
    }
}