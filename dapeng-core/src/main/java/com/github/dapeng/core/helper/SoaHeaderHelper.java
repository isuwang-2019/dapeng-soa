package com.github.dapeng.core.helper;

import com.github.dapeng.core.*;
import com.github.dapeng.core.SoaHeader;
import com.github.dapeng.core.TransactionContext;

import java.util.Optional;
import java.util.UUID;

/**
 * @author tangliu
 * @date 2016/5/16
 */
public class SoaHeaderHelper {

    /**
     * 服务端获取soaHeader
     *
     * @param setDefaultIfEmpty 是否需要设置默认的customer和operator，如果header中没有值
     * @return
     */
    public static SoaHeader getSoaHeader(boolean setDefaultIfEmpty) {
        TransactionContext context = TransactionContext.Factory.currentInstance();

        if (context.getHeader() == null) {
            SoaHeader header = new SoaHeader();
            ((TransactionContextImpl) context).setHeader(header);
        }

        if (setDefaultIfEmpty) {
            resetSoaHeader(context.getHeader());
        }

        return context.getHeader();
    }

    /**
     * 设置默认的customer和operator
     *
     * @param header
     */
    public static void resetSoaHeader(SoaHeader header) {
        //TODO
        if (!header.getOperatorId().isPresent()) {
            header.setOperatorId(Optional.of(0));
        }
    }

    public static SoaHeader buildHeader(String serviceName, String version, String methodName) {
        InvocationContext invocationContext = InvocationContextImpl.Factory.currentInstance();

        InvocationContextImpl.InvocationContextProxy invocationCtxProxy = InvocationContextImpl.Factory.getInvocationContextProxy();

        SoaHeader header = new SoaHeader();

        header.setServiceName(serviceName);
        header.setVersionName(version);
        header.setMethodName(methodName);

        header.setCallerIp(IPUtils.localIpAsInt());

        //设置慢服务检测阈值
        if (invocationContext.maxProcessTime().isPresent()) {
            header.setMaxProcessTime(invocationContext.maxProcessTime());
        }

        if (invocationContext.callerTid() != 0) {
            header.setCallerTid(Optional.of(invocationContext.callerTid()));
        }

        header.setCustomerId(invocationContext.customerId());
        header.setCustomerName(invocationContext.customerName());

        header.setOperatorId(invocationContext.operatorId());
        header.setOperatorName(invocationContext.operatorName());

        /**
         * 如果有invocationCtxProxy(一般在web或者三方系统)
         */
        if (invocationCtxProxy != null) {
            header.setSessionTid(invocationCtxProxy.sessionTid());
            header.setUserIp(invocationCtxProxy.userIp());
            header.setUserId(invocationCtxProxy.userId());
            header.setOperatorId(invocationCtxProxy.operatorId());
            header.setOperatorName(invocationCtxProxy.operatorName());
            header.setCustomerId(invocationCtxProxy.customerId());
            header.setCustomerName(invocationCtxProxy.customerName());
            header.setCallerFrom(invocationCtxProxy.callerFrom());
            header.setCallerMid(invocationCtxProxy.callerMid());

            header.addCookies(invocationCtxProxy.cookies());

        }

        header.addCookies(invocationContext.cookies());

        if (invocationContext.callerMid().isPresent()) {
            header.setCallerMid(invocationContext.callerMid());
        }

        if (invocationContext.operatorId().isPresent()) {
            header.setOperatorId(invocationContext.operatorId());
        }
        if (invocationContext.operatorName().isPresent()) {
            header.setOperatorName(invocationContext.operatorName());
        }

        if (invocationContext.customerId().isPresent()) {
            header.setCustomerId(invocationContext.customerId());
        }

        if (invocationContext.customerName().isPresent()) {
            header.setCustomerName(invocationContext.customerName());
        }
        if (invocationContext.userId().isPresent()) {
            header.setUserId(invocationContext.userId());
        }
        if (invocationContext.userIp().isPresent()) {
            header.setUserIp(invocationContext.userIp());
        }
        if (invocationContext.sessionTid().isPresent()) {
            header.setSessionTid(invocationContext.sessionTid());
        }

        header.setTransactionId(invocationContext.transactionId());
        header.setTransactionSequence(invocationContext.transactionSequence());

        /**
         * 如果容器内调用其它服务, 将原始的调用者信息传递
         */
        if (TransactionContext.hasCurrentInstance()) {
            TransactionContext transactionContext = TransactionContext.Factory.currentInstance();
            SoaHeader oriHeader = transactionContext.getHeader();

            // 部分场景下(例如定时任务, 事件等容器发起的请求)
            if (oriHeader != null) {
                if (!header.getOperatorId().isPresent()) {
                    header.setOperatorId(oriHeader.getOperatorId());
                }
                if (!header.getUserId().isPresent()) {
                    header.setUserId(oriHeader.getUserId());
                }
                if (!header.getUserIp().isPresent()) {
                    header.setUserIp(oriHeader.getUserIp());
                }
                if (!oriHeader.getCookies().isEmpty()) {
                    header.addCookies(oriHeader.getCookies());
                }
                if (!header.getCallerFrom().isPresent()){
                    header.setCallerFrom(oriHeader.getCallerFrom());
                }
                if (!header.getOperatorName().isPresent()){
                    header.setOperatorName(oriHeader.getOperatorName());
                }
                if (!header.getCustomerId().isPresent()){
                    header.setCustomerId(oriHeader.getCustomerId());
                }
                if (!header.getCustomerName().isPresent()){
                    header.setCustomerName(oriHeader.getCustomerName());
                }
                if (!header.getTransactionId().isPresent()){
                    header.setTransactionId(oriHeader.getTransactionId().orElse(null));
                }
                if (!header.getTransactionSequence().isPresent()){
                    header.setTransactionId(oriHeader.getTransactionSequence().orElse(null));
                }

            }
            // 传递tid
            header.setSessionTid(transactionContext.sessionTid());
            invocationContext.callerTid(transactionContext.calleeTid());
            header.setCallerTid(Optional.of(transactionContext.calleeTid()));

            header.setCallerPort(Optional.of(SoaSystemEnvProperties.SOA_CONTAINER_PORT));

        }
        if (!header.getCallerFrom().isPresent())
            header.setCallerFrom(Optional.of(SoaSystemEnvProperties.SOA_SERVICE_CALLERFROM));


        if (!header.getSessionId().isPresent()) {
            header.setSessionId(Optional.of(UUID.randomUUID().toString()));
        }
        return header;
    }
}
