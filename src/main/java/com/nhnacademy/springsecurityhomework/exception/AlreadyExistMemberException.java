package com.nhnacademy.springsecurityhomework.exception;

public class AlreadyExistMemberException extends RuntimeException {
    public AlreadyExistMemberException(String s) {
        super(s);
    }

}
