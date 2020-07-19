package com.iku.sports.mini.admin.controller;

import com.iku.sports.mini.admin.entity.MessageNotify;
import com.iku.sports.mini.admin.model.Response;
import com.iku.sports.mini.admin.service.MessageNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class MessageNotifyController {
    private MessageNotifyService messageNotifyService;

    @Autowired
    public MessageNotifyController(MessageNotifyService messageNotifyService) {
        this.messageNotifyService = messageNotifyService;
    }

    @GetMapping("/massage/notify/history/{receiverId}")
    public Response<List<MessageNotify>> historyMessagesNotify(@NotNull @NotEmpty
                                                               @PathVariable("receiverId") String receiverId) {
        List<MessageNotify> historyMessagesNotify = messageNotifyService.findMessageNotifyByReceiverId(receiverId, 3);
        return Response.ok(historyMessagesNotify);
    }

    @GetMapping("/massage/notify/count/{receiverId}")
    public Response<Integer> countMessagesNotify(@NotNull @NotEmpty
                                                     @PathVariable("receiverId") String receiverId) {
        List<MessageNotify> historyMessagesNotify = messageNotifyService.findMessageNotifyByReceiverId(receiverId, 3);
        return Response.ok(historyMessagesNotify.size());
    }
}
