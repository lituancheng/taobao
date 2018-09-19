package com.leon.taobao.service;

import com.google.gson.Gson;
import com.leon.taobao.model.TbkItem;
import com.leon.taobao.model.WeChatConstant;
import com.leon.taobao.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by lituancheng on 2018/9/13
 */
@Service
public class WeChatService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private TbkService tbkService;

    @Autowired
    private Gson gson;

    private String tklTemplate = "小淘推荐，必属精选\n" +
            "-----------------\n" +
            "%s\n" +
            "     【原价】%.2f元\n" +
            "     【折后价】%.2f元\n" +
            "-----------------\n" +
            "\uD83D\uDCB0复制这条信息，\uD83D\uDCB0\n" +
            "%s ，打开【手机淘宝】即可查看";

    public String processRequest(HttpServletRequest request, String openId) {
        // xml格式的消息数据
        String respXml = "";
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = WeChatUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get(WeChatConstant.MsgType);
            String mes;
            // 文本消息
            switch (msgType) {
                case WeChatConstant.REQ_MESSAGE_TYPE_TEXT:
                    mes = requestMap.get(WeChatConstant.Content);
                    messageService.create(mes, openId);
                    /*List<ArticleItem> items = new ArrayList<>();

                    List<TbkItem> tbkItemList = tbkService.getTbkItemList(mes);
                    for(TbkItem item : tbkItemList){
                        items.add(new ArticleItem(item.nick, item.title, item.pict_url, item.item_url));
                    }

                    respXml = WeChatUtil.sendArticleMsg(requestMap, items);*/

                    List<TbkItem> tbkItemList = tbkService.getTbkItemList(mes);
                    TbkItem item = tbkItemList.get(0);
                    List<String> tklList = tbkService.getTklList(tbkItemList.subList(0, 1));
                    respContent = String.format(tklTemplate, item.title, item.reserve_price, item.zk_final_price, tklList.get(0));
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 图片消息
                case WeChatConstant.REQ_MESSAGE_TYPE_IMAGE:
                    respContent = "您发送的是图片消息！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 语音消息
                case WeChatConstant.REQ_MESSAGE_TYPE_VOICE:
                    respContent = "您发送的是语音消息！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 视频消息
                case WeChatConstant.REQ_MESSAGE_TYPE_VIDEO:
                    respContent = "您发送的是视频消息！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 地理位置消息
                case WeChatConstant.REQ_MESSAGE_TYPE_LOCATION:
                    respContent = "您发送的是地理位置消息！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 链接消息
                case WeChatConstant.REQ_MESSAGE_TYPE_LINK:
                    respContent = "您发送的是链接消息！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                    break;
                // 事件推送
                case WeChatConstant.REQ_MESSAGE_TYPE_EVENT:
                    // 事件类型
                    String eventType = requestMap.get(WeChatConstant.Event);
                    // 关注
                    switch (eventType) {
                        case WeChatConstant.EVENT_TYPE_SUBSCRIBE:
                            respContent = "谢谢您的关注！";
                            respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                            userService.focus(openId);
                            break;
                        // 取消关注
                        case WeChatConstant.EVENT_TYPE_UNSUBSCRIBE:
                            userService.cancelFocus(openId);
                            break;
                        // 扫描带参数二维码
                        case WeChatConstant.EVENT_TYPE_SCAN:
                            // TODO 处理扫描带参数二维码事件
                            break;
                        // 上报地理位置
                        case WeChatConstant.EVENT_TYPE_LOCATION:
                            // TODO 处理上报地理位置事件
                            break;
                        // 自定义菜单
                        case WeChatConstant.EVENT_TYPE_CLICK:
                            // TODO 处理菜单点击事件
                            break;
                    }
                    break;
            }
            System.out.println(respXml);
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
}
