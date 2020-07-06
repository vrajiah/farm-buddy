package com.farm.buddy.utils;

import com.farm.buddy.model.Report;
import com.farm.buddy.exception.InternalServerErrorException;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;

public class BuddyUtils {

    /**
     * @param converter
     * @param report
     * @return json specification string if valid report exist
     */
    public static String getJsonSpecString(ResourceConverter converter, Report report) {
        try {
            JSONAPIDocument<Report> result = new JSONAPIDocument<>(report);
            byte[] document = converter.writeDocument(result);
            return new String(document);
        } catch (DocumentSerializationException e) {
            throw new InternalServerErrorException(BuddyErrors.JSON_SERIALIZATION_ERROR);
        }
    }
}
