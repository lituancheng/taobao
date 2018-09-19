package com.leon.taobao.service;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.leon.taobao.model.TbkItem;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.WirelessShareTpwdCreateRequest;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.WirelessShareTpwdCreateResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lituancheng on 2018/9/19
 */
@Service
public class TbkService {

    private static final Long SELF_TB_USER_ID = 1809225153L;

//    @Autowired
//    private TaobaoClient client;
//
//    @Autowired
//    private Gson gson;

    private TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "24809138", "fe60df0386813b642db343c9f9ee3ec5");
    private Gson gson = new Gson();

    /**
     * 获取商品列表
     * @param key   关键字
     * @return
     */
    public List<TbkItem> getTbkItemList(String key) throws ApiException {
        List<TbkItem> resultList = Lists.newArrayList();
        TbkItemGetRequest req = new TbkItemGetRequest();
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        req.setQ(key);
        req.setSort("total_sales_des");
        req.setPlatform(2L);    //1 PC端 2 无线端
        req.setPageSize(20L);   //取20个
        TbkItemGetResponse rsp = client.execute(req);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(rsp.getBody(), JsonObject.class);
        JsonObject tbkItemGetResponse = jsonObject.getAsJsonObject("tbk_item_get_response");
        int totalResults = tbkItemGetResponse.getAsJsonPrimitive("total_results").getAsInt();
        if(totalResults > 0){
            String results = tbkItemGetResponse.getAsJsonObject("results").getAsJsonArray("n_tbk_item").toString();
            List<TbkItem> tbkItemList = gson.fromJson(results, new TypeToken<List<TbkItem>>(){}.getType());
            List<TbkItem> discountList = tbkItemList.stream().filter(t -> (t.reserve_price - t.zk_final_price) > 1).collect(Collectors.toList());
            if(discountList.size() > 3)
                resultList = discountList.subList(0, 3);
            else
                resultList = discountList;
        }
        return resultList;
    }

    /**
     * 获取淘口令List
     * @param tbkItemList
     * @return
     * @throws ApiException
     */
    public List<String> getTklList(List<TbkItem> tbkItemList) throws ApiException {
        System.out.println(gson.toJson(tbkItemList));
        List<String> tklList = Lists.newArrayList();
        for(TbkItem item : tbkItemList){
            WirelessShareTpwdCreateRequest req = new WirelessShareTpwdCreateRequest();
            WirelessShareTpwdCreateRequest.GenPwdIsvParamDto obj = new WirelessShareTpwdCreateRequest.GenPwdIsvParamDto();
            obj.setLogo(item.pict_url);
            obj.setUrl(item.item_url);
            obj.setText(item.title);
            obj.setUserId(SELF_TB_USER_ID);
            req.setTpwdParam(obj);
            WirelessShareTpwdCreateResponse rsp = client.execute(req);
            JsonObject jsonObject = gson.fromJson(rsp.getBody(), JsonObject.class);
            String tkl = jsonObject.getAsJsonObject("wireless_share_tpwd_create_response").getAsJsonPrimitive("model").toString();
            tklList.add(tkl);
        }
        return tklList;
    }
}
