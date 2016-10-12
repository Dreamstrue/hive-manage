package com.hive.push;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushBroadcastMessageRequest;
import com.baidu.yun.channel.model.PushBroadcastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.google.gson.Gson;

public class AndroidPushBroadcastMessageSample {

    public static void main(String[] args) {

        /*
         * @brief 推送广播消息(消息类型为透传，由开发方应用自己来解析消息内容) message_type = 0 (默认为0)
         */

        // 1. 设置developer平台的ApiKey/SecretKey
        String apiKey = PushConstants.BAIDU_PUSH_API_KEY;
        String secretKey = PushConstants.BAIDU_PUSH_SECRET_KEY;
        ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);

        // 2. 创建BaiduChannelClient对象实例
        BaiduChannelClient channelClient = new BaiduChannelClient(pair);

        // 3. 若要了解交互细节，请注册YunLogHandler类
        channelClient.setChannelLogHandler(new YunLogHandler() {
            
            public void onHandle(YunLogEvent event) {
                System.out.println(event.getMessage());
            }
        });

        try {

            // 4. 创建请求类对象
            PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();
            request.setDeviceType(PushConstants.BAIDU_PUSH_DEVICE_TYPE_ANDROID); // device_type => 1: web 2: pc 3:android
                                      // 4:ios 5:wp

            // 若是消息，
            //request.setMessage("Hello Channel");
            // 若要通知，
			request.setMessageType(PushConstants.BAIDU_PUSH_MESSAGE_TYPE_NOTIFICATION);
			//request.setMessage("{\"title\":\"通知标题\",\"description\":\"这是一个通知的内容信息\"}");
			
			
			// ======== 构造自定义消息对象===================
			PushParamMessage pushParamMessage = new PushParamMessage();
			pushParamMessage.setTitle("消费资讯的通知测试");
			pushParamMessage.setDescription("这是一个消费资讯模块的通知测试内容");
			PushCustomContent customContent = new PushCustomContent();
			customContent.setBehavior(PushConstants.BAIDU_PUSH_BEHAVIOR_NEWS_DETAIL);
			customContent.setId("138");
			pushParamMessage.setCustom_content(customContent);
			
			// ======== 将自定义消息对象转换成Json字符串===================
			Gson gson = new Gson();
			String jsonStr = gson.toJson(pushParamMessage);
			request.setMessage(jsonStr);
			//request.setMessage("{ \"title\": \"消费资讯的通知测试\", \"description\": \"这是一个消费资讯模块的通知测试内容\", \"custom_content\": { \"behavior\": \"news_detail\", \"id\": \"138\" } }");

            // 5. 调用pushMessage接口
            PushBroadcastMessageResponse response = channelClient
                    .pushBroadcastMessage(request);

            // 6. 认证推送成功
            System.out.println("push amount : " + response.getSuccessAmount());

        } catch (ChannelClientException e) {
            // 处理客户端错误异常
            e.printStackTrace();
        } catch (ChannelServerException e) {
            // 处理服务端错误异常
            System.out.println(String.format(
                    "request_id: %d, error_code: %d, error_message: %s",
                    e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
        }

    }

}
