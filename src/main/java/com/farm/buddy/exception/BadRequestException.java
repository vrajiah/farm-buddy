package com.farm.buddy.exception;

import com.farm.buddy.utils.BuddyErrors;

public class BadRequestException extends FarmBuddyException {
    public BadRequestException(BuddyErrors errors) {
        super(errors);
    }
}
