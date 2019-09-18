package ${package}.common;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: hongsheng.wei
 * @Description: 自定义业务异常类
 */
@Getter
public class CustomBusinessException extends RuntimeException{

    private ErrorCodeEnumCommon errorCodeEnumCommon;

    public CustomBusinessException(ErrorCodeEnumCommon errorCodeEnumCommon) {
        super(errorCodeEnumCommon.getDesc());
        this.errorCodeEnumCommon = errorCodeEnumCommon;
    }

    public CustomBusinessException(int code) {
        super(ErrorCodeUtils.getErrorCodeEum(code).getDesc());
        this.errorCodeEnumCommon = ErrorCodeUtils.getErrorCodeEum(code);
    }

    public CustomBusinessException(ErrorCodeEnumCommon errorCodeEnumCommon, String message) {
        super(StringUtils.isBlank(message) ? errorCodeEnumCommon.getDesc() : message);
        this.errorCodeEnumCommon = errorCodeEnumCommon;
    }

}