package com.leon.taobao.model;

import java.util.List;

/**
 * Created by lituancheng on 2018/9/19
 */
public class TbkItem {

    public String item_url;
    public String nick;
    public Long num_iid;
    public String pict_url;
    public String provcity;
    public Double reserve_price;
    public Long seller_id;
    public SmallImages small_images;
    public String title;
    public Integer user_type;
    public Integer volume;
    public Double zk_final_price;

    public static class SmallImages{
        public List<String> string;
    }
}
