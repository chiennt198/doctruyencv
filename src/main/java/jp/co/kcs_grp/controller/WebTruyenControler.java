package jp.co.kcs_grp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import jp.co.kcs_grp.base.DBAccess;
import jp.co.kcs_grp.base.json.ObjectResponse;
import jp.co.kcs_grp.common.Constants;
import jp.co.kcs_grp.dao.M_AdminDao;
import jp.co.kcs_grp.dao.M_CategoryDao;
import jp.co.kcs_grp.dao.M_WideDao;
import jp.co.kcs_grp.dao.T_ChaptersDao;
import jp.co.kcs_grp.dao.T_StoryDao;
import jp.co.kcs_grp.utils.AppParams;

public class WebTruyenControler {
	
	private final Logger logger = Logger.getLogger(WebTruyenControler.class);
	
	private M_CategoryDao categoryDao = new M_CategoryDao();
	private T_StoryDao storyDao = new T_StoryDao();
	M_WideDao wideDao = new M_WideDao();
	private T_ChaptersDao chapterDao = new T_ChaptersDao();
	private M_AdminDao adminDao = new M_AdminDao();
	public ObjectResponse getList() {
		ObjectResponse objectResponse = new ObjectResponse();
        try {
        	objectResponse.setDataInfo(categoryDao.getList());
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse adminRegistStory(Map<String,String> map) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
		DBAccess db = null;
        try {
        	// データベース接続
			db = new DBAccess();
			if (!db.dbConnection()) {
				db.DBClose();
				throw new Exception("データベース接続が失敗です。");
			}
			db.DBTranStart();
			if("1".equals(map.get("registType"))) {
				storyDao.update(map, db);
			} else {
				storyDao.insert(map, db);
			}
        	
        	db.DBTranEnd(true);
        	objectResponse.setStatus(Constants.RESPONSE_STATUS_NORMAL);
        } catch (Exception e) {
        	db.DBTranEnd(false);
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        } finally {
			db.DBClose();
		}
        logger.info("end");
        return objectResponse;
    }
	public ObjectResponse adminGetListStory(Map<String,String> cond) {
		ObjectResponse objectResponse = new ObjectResponse();
		 logger.info("start");
        try {
        	objectResponse.setDataInfo(storyDao.search(cond));
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse getMWideList(String idx) {
		ObjectResponse objectResponse = new ObjectResponse();
		 logger.info("start");
        try {
        	objectResponse.setDataInfo(wideDao.getWideList(idx));
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse adminRegistChapter(Map<String,String> map) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
		DBAccess db = null;
        try {
        	// データベース接続
			db = new DBAccess();
			if (!db.dbConnection()) {
				db.DBClose();
				throw new Exception("データベース接続が失敗です。");
			}
			db.DBTranStart();
			if("0".equals(map.get("registType"))) {
				String id = chapterDao.getNewChapterId(map.get("storyId"));
				String chCount = chapterDao.countChapter(map.get("storyId"));
				map.put("id", id);
				chapterDao.insert(map, db);
				map.put("newestChapterId", id);
				map.put("chapterCount", String.valueOf(Integer.parseInt(chCount) + 1));
				storyDao.updateNewInfo(map, db);
			} else {
				chapterDao.update(map, db);
			}
			
        	objectResponse.setStatus(Constants.RESPONSE_STATUS_NORMAL);
        	db.DBTranEnd(true);
        } catch (Exception e) {
        	db.DBTranEnd(false);
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        } finally {
			db.DBClose();
		}
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse getStoryDetail(String id) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
        try {
        	objectResponse.setDataInfo(storyDao.getByKey(null,id));
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse seachChapters(Map<String,String > cond) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
        try {
        	objectResponse.setDataInfo(chapterDao.search(cond));
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse getChapterDetail(String storyId, String chapterId) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
        try {
        	objectResponse.setDataInfo(chapterDao.getByKey(null, null, storyId, chapterId));
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse login(String userId, String password) {
		ObjectResponse objectResponse = new ObjectResponse();
		 logger.info("start");
        try {
        	objectResponse.setDataInfo(adminDao.login(userId, password));
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse getStoryItems(Map<String,String> cond) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
        try {
        	
        	
        	if ( StringUtils.equals("1", cond.get("pagingFlg")) ) {
    			objectResponse.setDataInfo(storyDao.getList(cond));
    			return objectResponse;
    		} 
        	
        	long totalStory = storyDao.getTotalStory();
        	
        	if (totalStory > 0 ) {
        		
        		Map<String, Object> storyItem = new HashMap<>();
        		
        		long totalPage = 0;
    			int itemsPerPage = Integer.valueOf(AppParams.getValue("parameterpath", "ITEMS_PER_PAGE"));
    			if ( itemsPerPage == 1 ) {
    				totalPage = totalStory;
    			} else {
    				totalPage = totalStory / itemsPerPage;
            		
            		if (totalStory % itemsPerPage != 0) {
            			totalPage += 1;
            		}
    			}
        		
        		Map<String,String> condRand = new HashMap<>();
        		condRand.put("randomType", "1");
        		storyItem.put("randomLst", storyDao.getList(condRand));
        		storyItem.put("storyCnt", String.valueOf(totalStory));
        		storyItem.put("totalPages", String.valueOf(totalPage));
        		objectResponse.setDataInfo(storyItem);
        		
        	} else {
        		objectResponse.setStatus(Constants.RESPONSE_STATUS_NO_DATA);
        	}
        	
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse getChapterList(String storyId) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
        try {
        	
        	if ( StringUtils.isEmpty(storyId) ) {
        		objectResponse.setStatus(Constants.RESPONSE_STATUS_URI_PARAMS_ERROR);
        		return objectResponse;
        	}
        	
        	Map<String,String> cond = new HashMap<>();
        	cond.put("storyId", storyId);
        	
        	objectResponse.setDataInfo(chapterDao.search(cond));
        	
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse getStoryInfo(String storyKey, String currentPage, String pagingFlg, String orderBy) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
        try {

        	if ( StringUtils.isEmpty(storyKey) ) {
        		objectResponse.setStatus(Constants.RESPONSE_STATUS_URI_PARAMS_ERROR);
        		return objectResponse;
        	}
        	
        	Map<String,String> storyInfo = storyDao.getByKey(storyKey,null);
        	
        	if (storyInfo == null || ( storyInfo != null && storyInfo.isEmpty()) ) {
        		objectResponse.setStatus(Constants.RESPONSE_STATUS_URI_PARAMS_ERROR);
        		return objectResponse;
        	}
        	
        	Map<String,Object> rtnMap = new HashMap<>();
        	
        	if ( StringUtils.equals("1", pagingFlg) ) {
    			Map<String,String> cond = new HashMap<>();
            	cond.put("storyId", storyInfo.get("id"));
            	cond.put("currentPage", currentPage);
            	cond.put("orderBy", orderBy);
            	objectResponse.setDataInfo(chapterDao.search(cond));
            	return objectResponse;
    		} 
        	
        	rtnMap.put("storyInfo", storyInfo);

        	long dataCnt = chapterDao.totalChapterInStories(storyInfo.get("id"));
			long totalPage = 0;
			int itemsPerPage = Integer.valueOf(AppParams.getValue("parameterpath", "ITEMS_PER_PAGE"));
			if ( itemsPerPage == 1 ) {
				totalPage = dataCnt;
			} else {
				totalPage = dataCnt / itemsPerPage;
        		
        		if (dataCnt % itemsPerPage != 0) {
        			totalPage += 1;
        		}
			}
    		
			rtnMap.put("dataCnt", String.valueOf(dataCnt));
			rtnMap.put("totalPages", String.valueOf(totalPage));
        	
        	objectResponse.setDataInfo(rtnMap);
        	
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse getChapterInfo(String storyKey, String chapterKey) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
        try {
        	
        	if ( StringUtils.isEmpty(storyKey) || StringUtils.isEmpty(chapterKey)) {
        		objectResponse.setStatus(Constants.RESPONSE_STATUS_URI_PARAMS_ERROR);
        		return objectResponse;
        	}
        	
        	objectResponse.setDataInfo(chapterDao.getByKey(storyKey, chapterKey, null, null));
        	
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse getStoryList(Map<String, String> mapCond) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
        try {
        	
        	if ( StringUtils.equals("1", mapCond.get("pagingFlg")) ) {
    			objectResponse.setDataInfo(storyDao.getList(mapCond));
    			return objectResponse;
    		} 
        	
        	long totalStory = storyDao.getTotalStory(mapCond.get("categoryId"), mapCond.get("key"));
        	
        	if (totalStory > 0 ) {
        		
        		Map<String, Object> storyItem = new HashMap<>();
        		
        		long totalPage = 0;
    			int itemsPerPage = Integer.valueOf(AppParams.getValue("parameterpath", "ITEMS_PER_PAGE"));
    			if ( itemsPerPage == 1 ) {
    				totalPage = totalStory;
    			} else {
    				totalPage = totalStory / itemsPerPage;
            		
            		if (totalStory % itemsPerPage != 0) {
            			totalPage += 1;
            		}
    			}
        		
        		storyItem.put("dataCnt", String.valueOf(totalStory));
        		storyItem.put("totalPages", String.valueOf(totalPage));
        		objectResponse.setDataInfo(storyItem);
        		
        	} else {
        		objectResponse.setStatus(Constants.RESPONSE_STATUS_NO_DATA);
        	}
        	
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse adminLogin(String userId, String password) {
		ObjectResponse objectResponse = new ObjectResponse();
		 logger.info("start");
        try {
        	objectResponse.setDataInfo(adminDao.login(userId, password));
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
	
	public ObjectResponse updateWatchCountStories(String storyKey) {
		logger.info("start");
		ObjectResponse objectResponse = new ObjectResponse();
        try {
        	
        	if ( StringUtils.isEmpty(storyKey)) {
        		objectResponse.setStatus(Constants.RESPONSE_STATUS_URI_PARAMS_ERROR);
        		return objectResponse;
        	}
        	storyDao.updateWatchCountStories(storyKey);
        	objectResponse.setDataInfo(Constants.RESPONSE_STATUS_NORMAL);
        	
        } catch (Exception e) {
        	StringWriter stack = new StringWriter();
        	e.printStackTrace(new PrintWriter(stack));
            logger.error(stack.toString());
            objectResponse.setStatus(Constants.RESPONSE_STATUS_DB_ERROR);
        }
        logger.info("end");
        return objectResponse;
    }
}
