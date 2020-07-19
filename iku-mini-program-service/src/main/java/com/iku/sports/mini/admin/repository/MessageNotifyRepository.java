package com.iku.sports.mini.admin.repository;

import com.google.common.collect.Lists;
import com.iku.sports.mini.admin.entity.MessageNotify;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.sql.JDBCType;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author flex
 */
@Repository
public interface MessageNotifyRepository {
    @InsertProvider(type = SQLProvider.class, method = "sendMessage")
    void sendMessage(MessageNotify messageNotify);

    @Results({
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "content", column = "content", jdbcType = JdbcType.VARCHAR),
            @Result(property = "senderId", column = "sender_id", jdbcType = JdbcType.INTEGER),
            @Result(property = "senderName", column = "sender_name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "receiverId", column = "receiver_id", jdbcType = JdbcType.INTEGER),
            @Result(property = "receiverName", column = "receiver_name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.DATE)
    })
    @SelectProvider(type = SQLProvider.class, method = "findMessageNotifyByReceiverId")
    List<MessageNotify> findMessageNotifyByReceiverId(@Param("receiverId") String receiverId, @Param("recentlyDate") Date recentlyDate);

    @UpdateProvider(type = SQLProvider.class, method = "cancelMessageNotifyByReceiverId")
    void cancelMessageNotifyByReceiverId(MessageNotify messageNotify);

    class SQLProvider {
        final String TABLE = "message";
        final List<String> COLUMNS = Lists.newArrayList("id", "title", "content",
                "sender_id", "sender_name", "receiver_id", "receiver_name", "create_time");

        public String sendMessage(final MessageNotify messageNotify) {
            return new SQL() {
                {
                    INSERT_INTO(TABLE);
                    if (messageNotify.getTitle() != null) {
                        VALUES("title", "#{title}");
                    }

                    if (messageNotify.getContent() != null) {
                        VALUES("content", "#{content}");
                    }

                    if (messageNotify.getSenderId() != null) {
                        VALUES("sender_id", "#{senderId}");
                    }

                    if (messageNotify.getSenderName() != null) {
                        VALUES("sender_name", "#{senderName}");
                    }

                    if (messageNotify.getReceiverId() != null) {
                        VALUES("receiver_id", "#{receiverId}");
                    }

                    if (messageNotify.getReceiverName() != null) {
                        VALUES("receiver_name", "#{receiverName}");
                    }

                    if (messageNotify.getBusinessId() != null) {
                        VALUES("business_id", "#{businessId}");
                    }

                    if (messageNotify.getReceiverName() != null) {
                        VALUES("type", "#{type}");
                    }
                }
            }.toString();
        }

        public String findMessageNotifyByReceiverId(final Map<String, Object> params) {
            return new SQL() {
                {
                    SELECT(COLUMNS.toArray(new String[0]));
                    FROM(TABLE);
                    WHERE("receiver_id = #{receiverId} and create_time >= #{recentlyDate} and is_deleted = 0");
                }
            }.toString();
        }

        public String cancelMessageNotifyByReceiverId(final MessageNotify messageNotify) {
            return new SQL() {
                {
                    UPDATE(TABLE);
                    SET("is_deleted = 1");
                    WHERE("receiver_id = #{receiverId} and business_id = #{businessId} and type = #{type}");
                }
            }.toString();
        }
    }
}
