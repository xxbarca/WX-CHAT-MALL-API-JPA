package com.li.missyou.exception;

import com.li.missyou.exception.http.HttpException;

public class UpdateSuccess extends HttpException {

    public UpdateSuccess(int code) {
        this.code = code;
        this.httpStatusCode = 200;
    }

}
