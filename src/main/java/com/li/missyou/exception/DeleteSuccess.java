package com.li.missyou.exception;

import com.li.missyou.exception.http.HttpException;

public class DeleteSuccess extends HttpException {

    public DeleteSuccess(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }
}
