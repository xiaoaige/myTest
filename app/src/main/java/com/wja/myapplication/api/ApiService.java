package com.wja.myapplication.api;

import com.wja.myapplication.entity.NewsResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hanke on 2018/2/9.
 */

public interface ApiService {
    String GET_ARTICLE_LIST = "api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752";
    String GET_COMMENT_LIST = "article/v2/tab_comments/";


    @GET(GET_ARTICLE_LIST)
    Observable<NewsResponse> getNewsList(@Query("category") String category,@Query("min_behot_time") String min_behot_time,@Query("last_refresh_sub_entrance_interval") String last_refresh_sub_entrance_interval);

}
