package com.li.missyou.exception;

import com.li.missyou.exception.http.HttpException;

public class CreateSuccess extends HttpException {

    public CreateSuccess(int code) {
        this.httpStatusCode = 201;
        this.code = code;
    }
}
