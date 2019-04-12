package com.mayavi.models;

import lombok.Data;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

@Data
public class GenericResponse<T> {
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILURE = "FAILURE";

    private String status;
    private T response;
    private String message;

    public GenericResponse() {
    }

    public GenericResponse(boolean success, String message) {
        this.status = success ? STATUS_SUCCESS : STATUS_FAILURE;
        this.message = message;
    }

    public GenericResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public GenericResponse(boolean success, T response, String message) {
        this.status = success ? STATUS_SUCCESS : STATUS_FAILURE;
        this.response = response;
        this.message = message;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "status='" + status + '\'' +
                ", response='" + response + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

