package com.ddcsoftware.exception;

import java.time.LocalDateTime;
/*
    record used to generating exception messages
 */
public record ApiError(
        String path,
        String message,
        Integer statusCode,
        LocalDateTime localDateTime
){
}
