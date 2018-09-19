package com.leon.taobao.dao;

import com.leon.taobao.model.taobao.Tables;
import com.leon.taobao.model.taobao.tables.pojos.User;
import org.springframework.stereotype.Repository;

/**
 * Created by lituancheng on 2018/9/19
 */
@Repository
public class UserDao extends BaseDao {

    public User getByOpenId(String openId){
        return taobaoDsl.selectFrom(Tables.USER).where(Tables.USER.OPEN_ID.eq(openId)).fetchOneInto(User.class);
    }

    public Integer create(String openId){
        return taobaoDsl.insertInto(
                Tables.USER,
                Tables.USER.OPEN_ID
        )
                .values(openId)
                .returning(Tables.USER.ID)
                .fetchOne()
                .getId();
    }

    public Integer reFocus(String openId){
        return taobaoDsl.update(Tables.USER)
                .set(Tables.USER.ENABLE, NORMAL)
                .where(Tables.USER.OPEN_ID.eq(openId))
                .returning(Tables.USER.ID)
                .fetchOne()
                .getId();
    }

    public boolean cancelFocus(String openId){
        int execute = taobaoDsl.update(Tables.USER)
                .set(Tables.USER.ENABLE, NO_NORMAL)
                .where(Tables.USER.OPEN_ID.eq(openId))
                .execute();
        return execute > 0;
    }
}
