package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import message.Message;

import java.util.List;

public class JsonListEncoder extends MessageToMessageEncoder <Message> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        String str = OBJECT_MAPPER.writeValueAsString(msg);
        out.add(str);
    }
}
