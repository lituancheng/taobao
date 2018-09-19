package com.leon.taobao.dao;

import com.leon.taobao.model.taobao.Tables;
import org.springframework.stereotype.Repository;

/**
 * Created by lituancheng on 2018/9/19
 */
@Repository
public class MessageDao extends BaseDao {

    public Integer insert(String content, String openId){
        return taobaoDsl.insertInto(
                    Tables.MESSAGE,
                    Tables.MESSAGE.CONTENT,
                    Tables.MESSAGE.OPEN_ID
                )
                .values(content, openId)
                .returning(Tables.MESSAGE.ID)
                .fetchOne()
                .getId();
    }
}
