package com.leon.taobao.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.response.TbkItemGetResponse;
import org.jooq.tools.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by lituancheng on 2018/9/19
 */
@Service
public class TbkService {

    private static final String URL = "";

    public static void main(String[] args) throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "24809138", "fe60df0386813b642db343c9f9ee3ec5");
        TbkItemGetRequest req = new TbkItemGetRequest();
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        req.setQ("12qfwvas12fqwa");
        req.setSort("total_sales_des");
        req.setPlatform(2L);
        req.setPageSize(20L);
        TbkItemGetResponse rsp = client.execute(req);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(rsp.getBody(), JsonObject.class);
        System.out.println(jsonObject);
        /*Gson gson = new Gson();
        gson.fromJson(rsp.getBody(), )
        rsp.getBody()
        System.out.println(rsp.getBody());*/
    }
}
