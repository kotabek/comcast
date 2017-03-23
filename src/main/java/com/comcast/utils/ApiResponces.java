package com.comcast.utils;

import com.comcast.to.ApiBaseResponse;

/**
 * Created by kotabek on 3/22/17.
 */
public class ApiResponces {
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_SUCCEFUL = "sucessful";

    public static ApiBaseResponse dataIsEmpty() {
        return new ApiBaseResponse(STATUS_ERROR, "Data is empty");
    }

    public static ApiBaseResponse customError(String message) {
        return new ApiBaseResponse(STATUS_ERROR, message);
    }

    public static ApiBaseResponse ok() {
        return new ApiBaseResponse(STATUS_SUCCEFUL, null);
    }
}
