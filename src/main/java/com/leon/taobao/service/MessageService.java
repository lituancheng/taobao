package com.leon.taobao.service;

import com.leon.taobao.dao.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lituancheng on 2018/9/19
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public Integer create(String content, String openId){
        return messageDao.insert(content, openId);
    }
}
