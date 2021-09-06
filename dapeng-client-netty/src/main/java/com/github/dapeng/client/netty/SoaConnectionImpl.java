package com.github.dapeng.client.netty;

import com.github.dapeng.core.BeanSerializer;
import com.github.dapeng.core.SoaException;
import com.github.dapeng.core.SoaHeader;
import com.github.dapeng.core.helper.SoaHeaderHelper;
import com.github.dapeng.org.apache.thrift.TException;
import com.github.dapeng.util.SoaMessageBuilder;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoaConnectionImpl extends SoaBaseConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoaConnectionImpl.class);

    SoaConnectionImpl(String host, int port) {
        super(host, port);
    }

    @Override
    protected <REQ> void buildRequestBuf(ByteBuf requestBuf, String service, String version, String method, int seqid, REQ request, BeanSerializer<REQ> requestSerializer) throws SoaException {

        SoaMessageBuilder<REQ> builder = new SoaMessageBuilder<>();

        try {
            SoaHeader header = SoaHeaderHelper.buildHeader(service, version, method);

            builder.buffer(requestBuf)
                    .header(header)
                    .body(request, requestSerializer)
                    .seqid(seqid)
                    .build();
        } catch (TException e) {
            LOGGER.error(e.getMessage(), e);
            if (e instanceof SoaException) {
                throw (SoaException)e;
            } else {
                throw new SoaException(e);
            }
        }
    }
}
