package com.leon.taobao.service;

import com.leon.taobao.dao.UserDao;
import com.leon.taobao.model.taobao.tables.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lituancheng on 2018/9/19
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 用户关注
     */
    public Integer focus(String openId){
        User user = userDao.getByOpenId(openId);
        if(user != null)
            return userDao.reFocus(openId);    //重新关注
        else
            return userDao.create(openId); //创建新用户
    }

    /**
     * 取消关注
     */
    public boolean cancelFocus(String openId){
        User user = userDao.getByOpenId(openId);
        if(user != null)
            return userDao.cancelFocus(openId);
        return false;
    }
}
