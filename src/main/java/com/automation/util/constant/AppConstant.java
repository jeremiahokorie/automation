package com.automation.util.constant;

import lombok.Getter;

@Getter
public class AppConstant {


    public interface ApiResponseMessage {
        String SUCCESSFUL = "Successfully processed";
        String PENDING = "Pending approval";
        String FAILED = "Failed request";
        String UPDATE = "Successfully updated";
        String GET = "Successfully fetched records";
        String DELETE = "Successfully deleted records";
        String CREATED = "Successfully Created records";
    }
}
