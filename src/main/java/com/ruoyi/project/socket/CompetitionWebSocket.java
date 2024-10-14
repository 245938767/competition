package com.ruoyi.project.socket;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.project.sc.competition.service.IScCompetitionService;
import com.sun.jna.platform.win32.Guid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 评委端WebSocket
 */
@ServerEndpoint(value = "/ws/competition")
@Component
@CrossOrigin
public class CompetitionWebSocket {

    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    private static IScCompetitionService scCompetitionService;

    @Autowired
    public void setScCompetitionService(IScCompetitionService scCompetitionService) {
        CompetitionWebSocket.scCompetitionService = scCompetitionService;
    }

    @OnOpen
    public void onOpen(Session session) {
        String sessionId = session.getId();
        System.out.printf("新连接：%s%n", sessionId);
        clients.put(sessionId, session);

        System.out.printf("当前连接数：%d%n", clients.size());

        sendMessage();
    }

    /**
     * 关闭时执行
     */
    @OnClose
    public void onClose(Session session) {
        String sessionId = session.getId();
        closeSession(sessionId);
        System.out.printf("连接关闭：%s，当前连接数：%d%n", sessionId, clients.size());
    }

    public void closeSession(String sid) {
        Session s = clients.remove(sid);
        if (s != null) {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("连接关闭：%s，当前连接数：%d%n", sid, clients.size());
    }

    public void sendMessage() {
        NoticeWebsocketResp noticeWebsocketResp = scCompetitionService.getCurrentCompetitionData(1L);
        sendMessage(noticeWebsocketResp);
    }

    /**
     * 发送给所有用户
     *
     * @param noticeWebsocketResp
     */
    public static void sendMessage(NoticeWebsocketResp noticeWebsocketResp) {
        // 使用 JSON.toJSONString 替代 JSONObject.toJSONString
        // 添加 SerializerFeature.DisableCircularReferenceDetect 来处理循环引用
        String message = JSON.toJSONString(noticeWebsocketResp, SerializerFeature.DisableCircularReferenceDetect);
        for (Session session1 : clients.values()) {
            try {
                session1.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据用户id发送给某一个用户
     **/

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

}
