package com.farm.buddy.exception;

import com.farm.buddy.utils.BuddyErrors;

public class InternalServerErrorException extends FarmBuddyException {
    public InternalServerErrorException(BuddyErrors errors) {
        super(errors);
    }
}
