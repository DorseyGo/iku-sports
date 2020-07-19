package com.iku.sports.mini.admin.service;

import com.iku.sports.mini.admin.entity.MessageNotify;

import java.util.List;

/**
 * @author flex
 */
public interface MessageNotifyService {
    /**
     * 发送消息通知
     * @param messageNotify
     */
    void sendMessageNotify(MessageNotify messageNotify);

    /**
     * 获取用户的消息通知
     * @param receiverId 消息接收用户 id
     * @param recentlyDays 指定获取用户最近几天之内的消息通知
     * @return
     */
    List<MessageNotify> findMessageNotifyByReceiverId(String receiverId, long recentlyDays);

    /**
     * 取消通知
     * @param receiverId
     * @param arrangedId 课程安排主键 id
     */
    void cancelMessageNotifyByReceiverId(String receiverId, Integer arrangedId);
}
