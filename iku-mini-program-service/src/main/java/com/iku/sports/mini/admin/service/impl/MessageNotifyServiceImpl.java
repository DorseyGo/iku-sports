package com.iku.sports.mini.admin.service.impl;

import com.iku.sports.mini.admin.entity.MessageNotify;
import com.iku.sports.mini.admin.model.Constants;
import com.iku.sports.mini.admin.repository.MessageNotifyRepository;
import com.iku.sports.mini.admin.service.MessageNotifyService;
import com.iku.sports.mini.admin.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author flex
 */
@Service
@Slf4j
public class MessageNotifyServiceImpl implements MessageNotifyService {
    private MessageNotifyRepository messageNotifyRepository;

    @Autowired
    public MessageNotifyServiceImpl(MessageNotifyRepository messageNotifyRepository) {
        this.messageNotifyRepository = messageNotifyRepository;
    }

    @Override
    public void sendMessageNotify(MessageNotify messageNotify) {
        messageNotifyRepository.sendMessage(messageNotify);
    }

    @Override
    public List<MessageNotify> findMessageNotifyByReceiverId(String receiverId, long recentlyDays) {
        return messageNotifyRepository.findMessageNotifyByReceiverId(receiverId,
                    DateUtil.aheadOf(recentlyDays));
    }

    @Override
    public void cancelMessageNotifyByReceiverId(String receiverId, Integer arrangedId) {
        MessageNotify messageNotify = MessageNotify.builder()
                .receiverId(receiverId)
                .businessId(arrangedId)
                .type(Constants.MessageNotifyBusinessType.CLASS_APPOINTMENT.getCode())
                .build();
        messageNotifyRepository.cancelMessageNotifyByReceiverId(messageNotify);
    }
}
