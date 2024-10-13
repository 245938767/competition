package com.ruoyi.project.socket;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.project.sc.competition.service.IScCompetitionService;
import com.sun.jna.platform.win32.Guid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


/**
 * 评委端WebSocket
 */
@ServerEndpoint("/competitionWebSocket")
@Component
public class CompetitionWebSocket {

    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    @Autowired
    private IScCompetitionService scCompetitionService;


    @OnOpen
    public void onOpen(Session session) {
//        log.info("新连接：{}", userId);
        System.out.printf("新连接：{}%n", session.getId());
        clients.put(session.getId(), session);
        sendMessage();
//            log.info("现在连接的客户编码为：" + userId);
    }

    /**
     * 关闭时执行
     */
    @OnClose
    public void onClose(Session session) {
//        log.info("连接：{} 关闭", this.userId);
        closeSession(session.getId());
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
        System.out.printf("DeviceWebsocket---onClose===>--{}--连接数：{}%n", sid, clients.size());
        //log.error("在线人数===="+clients.size());
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
        String message = JSONObject.toJSONString(noticeWebsocketResp);
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
