package org.doif.projectv.common.response;

public class ResponseUtil {

    public static CommonResponse ok() {
        return new CommonResponse(ResponseCode.OK);
    }

}
