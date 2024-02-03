package com.dileep.poiexample.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralException extends Exception {
    public String errorCode;
    public String massage;

    public GeneralException() {
        super();
    }
    public GeneralException(String massage) {
        super(massage);
        this.massage=massage;
    }
    public GeneralException(String errorCode,String massage) {
        super(massage);
        this.massage=massage;
        this.errorCode=errorCode;
    }
}
