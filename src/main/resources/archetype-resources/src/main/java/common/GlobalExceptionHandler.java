package ${package}.common;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import ${package}.filter.RequestIdHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理
 *
 * @author hongsheng.wei
 */
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数类型不匹配异常拦截
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MismatchedInputException.class)
    public ResponseMsg mismatchedInputExceptionHandler(MismatchedInputException ex) {
        String errorMesssage = "Param mapping exception[" +  ex.getOriginalMessage()+"]";
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRequestId(RequestIdHelper.getRequestId());
        responseMsg.setCode(ErrorCodeUtils.CommonErrorCode.ERROR_PARAMS_TYPE.code);
        responseMsg.setMessage(ErrorCodeUtils.CommonErrorCode.ERROR_PARAMS_TYPE.desc);
        responseMsg.setResult("");
        log.warn("uri:{}, mismatchedInputExceptionHandler:{}", getRequestURI(), errorMesssage, ex);
        return responseMsg;
    }

    /**
     * 参数类型不匹配异常拦截
     * @param ex
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseMsg httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        String errorMesssage = "Request method '" + ex.getMethod() + "' not supported";
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRequestId(RequestIdHelper.getRequestId());
        responseMsg.setCode(ErrorCodeUtils.CommonErrorCode.ERROR_NOT_SUPPORT_METHOD.code);
        responseMsg.setMessage(ErrorCodeUtils.CommonErrorCode.ERROR_NOT_SUPPORT_METHOD.desc);
        responseMsg.setResult("");
        log.warn("uri:{}, httpRequestMethodNotSupportedExceptionHandler:{}", getRequestURI(), errorMesssage, ex);
        return responseMsg;
    }

    /**
     * 参数类型不匹配异常拦截
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseMsg missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        String errorMesssage = "Miss request param,name is ["+ex.getParameterName()+"]";
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRequestId(RequestIdHelper.getRequestId());
        responseMsg.setCode(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_PARAMS.code);
        responseMsg.setMessage(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_PARAMS.desc);
        responseMsg.setResult("");
        log.warn("uri:{}, missingServletRequestParameterExceptionHandler:{}", getRequestURI(), errorMesssage, ex);
        return responseMsg;
    }

    /**
     * 参数类型不匹配异常拦截
     * @param ex
     * @return
     */
    @ExceptionHandler(value = TypeMismatchException.class)
    public ResponseMsg typeMismatchExceptionHandler(TypeMismatchException ex) {
        String errorMesssage = "Param type not match,param["+ex.getPropertyName()+"] type must be "+ex.getRequiredType();
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRequestId(RequestIdHelper.getRequestId());
        responseMsg.setCode(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_PARAMS.code);
        responseMsg.setMessage(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_PARAMS.desc);
        responseMsg.setResult("");
        log.warn("uri:{}, typeMismatchExceptionHandler:{}", getRequestURI(), errorMesssage, ex);
        return responseMsg;
    }


    /**
     * 参数校验异常拦截
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseMsg methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuffer errorBuffer = new StringBuffer();
        errorBuffer.append("Param not valid:");

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorBuffer.append(fieldError.getDefaultMessage()).append(", ");
        }
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRequestId(RequestIdHelper.getRequestId());
        responseMsg.setCode(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_PARAMS.code);
        responseMsg.setMessage(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_PARAMS.desc);
        responseMsg.setResult("");
        log.warn("uri:{}, methodArgumentNotValidExceptionHandler:{}", getRequestURI(), errorBuffer.toString());
        return responseMsg;
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseMsg constraintViolationExceptionHandler(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolationSet = ex.getConstraintViolations();
        StringBuffer errorBuffer = new StringBuffer();
        errorBuffer.append("Param not valid:");
        for (ConstraintViolation constraintViolation : constraintViolationSet) {
            errorBuffer.append(constraintViolation.getMessage()).append(", ");
            constraintViolation.getConstraintDescriptor();
        }
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRequestId(RequestIdHelper.getRequestId());
        responseMsg.setCode(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_PARAMS.code);
        responseMsg.setMessage(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_PARAMS.desc);
        responseMsg.setResult("");
        log.warn("uri:{}, constraintViolationExceptionHandler:{}", getRequestURI(), errorBuffer.toString(), ex);
        return responseMsg;
    }

    /**
     * 业务异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomBusinessException.class)
    public ResponseMsg customBusinessExceptionHandler(CustomBusinessException ex) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRequestId(RequestIdHelper.getRequestId());
        responseMsg.setCode(ex.getErrorCodeEnumCommon().getCode());
        // miot需要更详细错误信息
        responseMsg.setMessage(ex.getMessage());
        responseMsg.setResult("");
        log.warn("uri:{}, message:{}", getRequestURI(), ex.getMessage());
        return responseMsg;
    }

    /**
     * 流控异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = FlowException.class)
    public ResponseMsg flowLimitException(FlowException ex) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setRequestId(RequestIdHelper.getRequestId());
        responseMsg.setCode(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_FLOW_LIMIT.code);
        responseMsg.setMessage(ErrorCodeUtils.CommonErrorCode.ERROR_REQUEST_FLOW_LIMIT.desc);
        responseMsg.setResult("");
        log.warn("uri:{}, flowLimitException:{}", getRequestURI(), ex);
        return responseMsg;
    }

    /**
     * 获取uri
     * @return
     */
    private String getRequestURI(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getRequestURI();
    }

}