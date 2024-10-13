package com.ruoyi.project.socket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ruoyi.common.utils.StringUtils.isEmpty;

/**
 * 评委端WebSocket
 */
@ServerEndpoint("/judgeWebsocket/{judgeId}")
@Component
public class JudgeWebSocket {

    private String judgeId;
    private Session session;
    private static Map<String, JudgeWebSocket> client = new ConcurrentHashMap<>();


    @OnOpen
    public void onOpen(@PathParam("judgeId") String userId, Session session) {
//        log.info("新连接：{}", userId);
        if (!isEmpty(userId)) {
            this.judgeId = userId;
            this.session = session;
            client.put(userId, this);
//            log.info("现在连接的客户编码为：" + userId);
        }
    }

    /**
     * 关闭时执行
     */
    @OnClose
    public void onClose() {
//        log.info("连接：{} 关闭", this.userId);
    }
}
