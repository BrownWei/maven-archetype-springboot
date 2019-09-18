package ${package}.filter;


import org.apache.skywalking.apm.toolkit.trace.TraceContext;

/**
 * @Author: hongsheng.wei
 * @Description:
 */
public class RequestIdHelper {

    /**
     *
     * @return
     */
    public static String getRequestId() {
        return TraceContext.traceId();
    }

}