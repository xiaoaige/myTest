package com.wja.myapplication.entity;

import java.util.List;

/**
 * Created by hanke on 2018/2/9.
 */

public class NewsResponse {


    public String message;
    public List<NewsData> data;
    public int total_number;
    public boolean has_more;
    public String login_status;
    public String show_et_status;
    public String post_content_hint;
    public boolean has_more_to_refresh;
    public Tips tips;

}
