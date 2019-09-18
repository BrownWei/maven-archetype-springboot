package ${package}.config;

import ${package}.filter.RequestIdHelper;
import ${package}.common.ErrorCodeUtils;
import ${package}.common.ResponseMsg;
import ${package}.common.ErrorCodeEnumCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * Controller公共类
 */
@Component
@Slf4j
public class BaseController {

    /**
     * error result
     * @return
     */
    protected ResponseMsg success() {
        ResponseMsg responseMsg = getResult(ErrorCodeUtils.CommonErrorCode.SUCCESS);
        return responseMsg;
    }


    protected ResponseMsg success(Object data) {
        ResponseMsg responseMsg = getResult(ErrorCodeUtils.CommonErrorCode.SUCCESS, data);
        return responseMsg;
    }


    protected ResponseMsg failed(ErrorCodeEnumCommon errorCodeEnumCommon) {
        ResponseMsg responseMsg = getResult(errorCodeEnumCommon);
        return responseMsg;
    }

    protected ResponseMsg failed(ErrorCodeEnumCommon errorCodeEnumCommon, Object data) {
        return getResult(errorCodeEnumCommon, data);
    }

    protected ResponseMsg failed(int code) {
        return failed(ErrorCodeUtils.getErrorCodeEum(code));
    }

    protected ResponseMsg failed(int code, Object data) {
        return failed(ErrorCodeUtils.getErrorCodeEum(code), data);
    }

    protected ResponseMsg getResult(ErrorCodeEnumCommon errorCodeEnumCommon, Object result) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRequestId(RequestIdHelper.getRequestId());
        responseMsg.setCode(errorCodeEnumCommon.getCode());
        responseMsg.setMessage(errorCodeEnumCommon.getDesc());
        if(null == result){
            result = "";
        }
        responseMsg.setResult(result);
        return responseMsg;
    }

    protected ResponseMsg getResult(ErrorCodeEnumCommon errorCodeEnumCommon) {
        return getResult(errorCodeEnumCommon, "");
    }

}