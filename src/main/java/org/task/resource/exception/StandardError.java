package org.task.resource.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardError {

    private Integer status;
    private String message;
    private String date;

    public StandardError(Integer status, String message, String date) {
        super();
        this.status = status;
        this.message = message;
        this.date = date;
    }
}
