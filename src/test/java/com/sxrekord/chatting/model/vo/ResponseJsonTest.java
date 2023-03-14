package com.sxrekord.chatting.model.vo;

import com.sxrekord.chatting.util.ChatType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author Rekord
 * @date 2022/10/10 8:57
 */
@SpringBootTest
public class ResponseJsonTest {

    ResponseJson responseJson = new ResponseJson();

    @Test
    public void setDataTest() {
        responseJson.setData("fromUserId", 501)
                .setData("content", "addToMessageTest")
                .setData("type", ChatType.SINGLE_SENDING)
                .toString();

        System.out.println(responseJson);
    }

    @Test
    public void addToCollectionTest() {
        responseJson.setData("field1", "field1")
                .setData("field2", "field2")
                .setData("field3", "field3")
                .toString();
        responseJson.addToCollection("messages");

        responseJson.setData("field4", "field4")
                .setData("field5", "field5")
                .setData("field6", "field6")
                .toString();
        responseJson.addToCollection("messages");

        Assert.isTrue(((List<Map<String, Object>>)responseJson.get("messages")).size() == 2, "addToMessage error!");
        System.out.println(responseJson);
    }
}
