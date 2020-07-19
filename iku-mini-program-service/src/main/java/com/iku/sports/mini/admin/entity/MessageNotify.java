package com.iku.sports.mini.admin.entity;

import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author flex
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageNotify implements Serializable {
    private Integer id;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息发送者
     */
    private String senderId;
    private String senderName;
    /**
     * 消息接收者
     */
    private String receiverId;
    private String receiverName;

    /**
     * 通知业务 id
     */
    private Integer businessId;
    /**
     * 通知业务类型
     */
    private Integer type;
    private Date createTime;

    public static MessageNotify appointmentMessageNotify(String receiverId, Date date, String address,
                                                         Integer arrangedId) {
        return MessageNotify.builder()
                .title("系统通知")
                .content(String.format("恭喜您，课程预约成功！请记得于 %s 在 %s 上课。", DateUtil.format(date), address))
                .receiverId(receiverId)
                .businessId(arrangedId)
                .type(Constants.MessageNotifyBusinessType.CLASS_APPOINTMENT.getCode())
                .build();
    }
}
