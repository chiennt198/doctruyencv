/**
 * ファイル名: WebMemberPage.java
 * バージョン: 1.0.0
 * 作成日付: Jan 11, 2017
 * 最終更新日付: Jan 11, 2017
 * 作成者: KCS
 * 更新履歴: Jan 11, 2017 : 新規作成
 */

package jp.co.kcs_grp.page;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jp.co.kcs_grp.controller.WebTruyenControler;
import jp.co.kcs_grp.utils.BeanUtils;
import spark.Request;
import spark.Response;
import spark.Route;

public class WebTruyenPage {

    private WebTruyenControler webTruyenControler =  new WebTruyenControler();

    
    public Route getCategoryList() {
        return new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                return webTruyenControler.getList();
            }
        };
    }
    
    
    public Route adminRegistStory() {
        return new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
            	 String jsonData = request.queryParams("json");
                 Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
     			Map<String, String> dataMap = new Gson().fromJson(jsonData, mapType);
                return webTruyenControler.adminRegistStory(dataMap);
            }
        };
    }
    
    public Route adminGetListStory() {
        return new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
            	 String jsonData = request.queryParams("json");
                 Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
     			Map<String, String> dataMap = new Gson().fromJson(jsonData, mapType);
                return webTruyenControler.adminGetListStory(dataMap);
            }
        };
    }
    
    public Route getMWideList() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return webTruyenControler.getMWideList(request.params("idx"));
            }
        };
    }
    
    public Route adminRegistChapter() {
        return new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
            	 String jsonData = request.queryParams("json");
                 Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
     			Map<String, String> dataMap = new Gson().fromJson(jsonData, mapType);
                return webTruyenControler.adminRegistChapter(dataMap);
            }
        };
    }
    
    public Route getStoryDetail() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return webTruyenControler.getStoryDetail(request.params("id"));
            }
        };
    }
    
    public Route adminGetListChapters() {
        return new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
            	 String jsonData = request.queryParams("json");
                 Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
     			Map<String, String> dataMap = new Gson().fromJson(jsonData, mapType);
                return webTruyenControler.seachChapters(dataMap);
            }
        };
    }
    
    public Route getChapterDetail() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return webTruyenControler.getChapterDetail(request.queryParams("storyId"), request.queryParams("chapterId"));
            }
        };
    }
    
    public Route getStoryItems() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return webTruyenControler.getStoryItems(BeanUtils.mapBeanData(request.queryMap().toMap()));
            }
        };
    }
    
    public Route getChapterList() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return webTruyenControler.getChapterList(request.params("storyId"));
            }
        };
    }
    
    public Route getStoryInfo() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return webTruyenControler.getStoryInfo(request.params("storyId"));
            }
        };
    }
    
    public Route getChapterInfo() {
        return new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return webTruyenControler.getChapterInfo(request.params("storyId"), request.params("chapterId"));
            }
        };
    }
    
}
