package com.automation.core.global.dto.response;

import io.swagger.annotations.ApiParam;
import lombok.*;
import java.util.ArrayList;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse<T> {

    @ApiParam(value = "HTTP status code")
    private int status;

    @ApiParam(value = "Description of http status code")
    private String message;

    @ApiParam(value = "Response data")
    private T data;

    @ApiParam(value = "Time taken to process request(for metrics and performance tracking)")
    private Double execTime = 0D;

    @ApiParam(value = "Specific errors when request does not return a HTTP status of 200.")
    private Object error = new ArrayList<>();

    public AppResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public AppResponse(String mdaDeletedSuccessfully, boolean b, Object o) {
    }

    public AppResponse(boolean b, String message) {
    }

//    public static <T> AppResponse<T> of(int status, T data) {
//        return new AppResponse<>(status, data);
//    }


    // Add this new factory method for errors
    public static <T> AppResponse<T> error(int status, String message) {
        AppResponse<T> response = new AppResponse<>();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }

    // Existing of() method
    public static <T> AppResponse<T> of(int status, T data) {
        AppResponse<T> response = new AppResponse<>();
        response.setStatus(status);
        response.setData(data);
        return response;
    }

}

