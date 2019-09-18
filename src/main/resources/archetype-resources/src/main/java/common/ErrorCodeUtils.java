package ${package}.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2018/11/19
 * @Description:
 */
public abstract class ErrorCodeUtils {

    private static Map<Integer, ErrorCodeEnumCommon> errorCodeMaps;

    static {
        errorCodeMaps = new HashMap<>();
        for (CommonErrorCode commonErrorCode : CommonErrorCode.values()) {
            errorCodeMaps.put(commonErrorCode.code, commonErrorCode);
        }
    }

    public static ErrorCodeEnumCommon getErrorCodeEum(int code) {
        ErrorCodeEnumCommon errorCodeEnumCommon = errorCodeMaps.get(code);
        if (null == errorCodeEnumCommon) {
            return CommonErrorCode.ERROR_SERVER_INTERNAL;
        }
        return errorCodeEnumCommon;
    }


    public static boolean isSuccess(ErrorCodeEnumCommon errorCodeEnumCommon) {
        if (CommonErrorCode.SUCCESS.code == errorCodeEnumCommon.getCode()) {
            return true;
        }
        return false;
    }

    public static boolean isSuccess(int code) {
        return CommonErrorCode.SUCCESS.code == code;
    }

    public static boolean isFailed(int code) {
        return CommonErrorCode.SUCCESS.code != code;
    }

    /**
     * 通用错误码
     */
    @Getter
    @AllArgsConstructor
    public enum CommonErrorCode implements ErrorCodeEnumCommon {
        SUCCESS(0, "Success"),
        ERROR_TIMEOUT(100, "Timeout"),
        ERROR_INVALID_DATA_PACKAGE(101, "Invalid data package"),
        ERROR_PACKAGE_IS_ALTERED(102, "Data package has altered"),
        ERROR_DATA_PACKAGE_MAY_LOSE(103, "Data package may lose"),
        ERROR_SERVER_BUSY(104, "Server busy"),
        ERROR_DATA_PACKAGE_IS_EXPIRED(105, "Data package has expired"),
        ERROR_ILLEGAL_SIGN(106, "Invalid sign"),
        ERROR_ILLEGAL_APPKEY(107, "Illegal appKey"),
        ERROR_TOKEN_IS_EXPRIED(108, "Token has expired"),
        ERROR_TOKEN_IS_ABSENCE(109, "Token is absence"),

        ERROR_REQUEST_PATH(301, "Request path error"),
        ERROR_REQUEST_PARAMS(302, "Params error"),
        ERROR_PARAMS_TYPE(303, "Request params type error"),
        ERROR_NOT_SUPPORT_METHOD(304, "Request method not support"),
        ERROR_REQUEST_HEADER_PARAMS(305, "Header Params error"),
        ERROR_REQUEST_PATH_NOT_OPEN(306, "Request path not open"),

        ERROR_REQUEST_FORBIDDEN(403, "Request forbidden"),

        ERROR_REQUEST_FLOW_LIMIT(429, "Too Many Requests"),

        ERROR_SERVER_INTERNAL(500, "Service impl error"),
        ERROR_PROXY(501, "Service proxy error"),
        ERROR_ILLEGAl_IP(9998, "Illegal ip"),
        ERROR_VERSION_REQUEST(9999, "Version not support");

        public final int code;
        public final String desc;
    }

    /*************其他业务模块错误码定义********/

}