package com.leon.taobao.service;

import com.baimaodai.util.httputil.HttpRespParser;
import com.baimaodai.util.httputil.HttpUtil;
import com.baimaodai.util.httputil.model.StrRspWithHeaders;
import com.google.gson.Gson;
import com.leon.taobao.log.Logger;
import com.leon.taobao.model.ArticleItem;
import com.leon.taobao.model.WeChatConstant;
import com.leon.taobao.model.WxUserInfo;
import com.leon.taobao.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    private Gson gson;

    public String processRequest(HttpServletRequest request, String openId) {
        // xml格式的消息数据
        String respXml = null;
        // 默认返回的文本消息内容
        String respContent;
        try {
            // 调用parseXml方法解析请求消息
            Map<String,String> requestMap = WeChatUtil.parseXml(request);
            // 消息类型
            String msgType = requestMap.get(WeChatConstant.MsgType);
            String mes;
            // 文本消息
            if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_TEXT)) {
                mes = requestMap.get(WeChatConstant.Content);
                messageService.create(mes, openId);
                if("我的信息".equals(mes)){
                    String accessToken = WeChatUtil.getAccessToken(openId);
                    WxUserInfo userInfo = getUserInfo(accessToken, openId);
                    System.out.println(gson.toJson(userInfo));
                    String nickname = userInfo.nickname;
                    String city = userInfo.city;
                    String province = userInfo.province;
                    String country = userInfo.country;
                    String headimgurl = userInfo.headimgurl;
                    List<ArticleItem> items = new ArrayList<>();
                    ArticleItem item = new ArticleItem();
                    item.setTitle("你的信息");
                    item.setDescription("昵称:"+nickname+"  地址:"+country+" "+province+" "+city);
                    item.setPicUrl(headimgurl);
                    item.setUrl("http://www.baidu.com");
                    items.add(item);

                    respXml = WeChatUtil.sendArticleMsg(requestMap, items);
                }else {
                    List<ArticleItem> items = new ArrayList<>();
                    ArticleItem item = new ArticleItem();
                    item.setTitle("照片墙");
                    item.setDescription("阿狸照片墙");
                    item.setPicUrl("http://changhaiwx.pagekite.me/photo-wall/a/iali11.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/photowall");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("哈哈");
                    item.setDescription("一张照片");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/me.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/index");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("小游戏2048");
                    item.setDescription("小游戏2048");
                    item.setPicUrl("http://changhaiwx.pagekite.me/images/2048.jpg");
                    item.setUrl("http://changhaiwx.pagekite.me/page/game2048");
                    items.add(item);

                    item = new ArticleItem();
                    item.setTitle("百度");
                    item.setDescription("百度一下");
                    item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505100912368&di=69c2ba796aa2afd9a4608e213bf695fb&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F0634355517-9.jpg");
                    item.setUrl("http://www.baidu.com");
                    items.add(item);

                    respXml = WeChatUtil.sendArticleMsg(requestMap, items);
                }
            }
            // 图片消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 语音消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 视频消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 地理位置消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 链接消息
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
                respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
            }
            // 事件推送
            else if (msgType.equals(WeChatConstant.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get(WeChatConstant.Event);
                // 关注
                if (eventType.equals(WeChatConstant.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                    respXml = WeChatUtil.sendTextMsg(requestMap, respContent);
                    userService.focus(openId);
                }
                // 取消关注
                else if (eventType.equals(WeChatConstant.EVENT_TYPE_UNSUBSCRIBE)) {
                    userService.cancelFocus(openId);
                }
                // 扫描带参数二维码
                else if (eventType.equals(WeChatConstant.EVENT_TYPE_SCAN)) {
                    // TODO 处理扫描带参数二维码事件
                }
                // 上报地理位置
                else if (eventType.equals(WeChatConstant.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(WeChatConstant.EVENT_TYPE_CLICK)) {
                    // TODO 处理菜单点击事件
                }
            }
            return respXml;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public WxUserInfo getUserInfo(String accessToken, String openId){
        try {
            StrRspWithHeaders strRspWithHeaders = HttpUtil.url("https://api.weixin.qq.com/cgi-bin/user/info")
                    .param("access_token", accessToken)
                    .param("openId", openId)
                    .param("lang", "zh_CN")
                    .doGet(HttpRespParser.PARSE2RspWithHeaders);
            return gson.fromJson(strRspWithHeaders.respContent, WxUserInfo.class);
        } catch (Exception e) {
            Logger.errorLog.error(e);
        }
        return null;
    }
}
