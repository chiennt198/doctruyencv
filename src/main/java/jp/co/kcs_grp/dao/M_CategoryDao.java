package jp.co.kcs_grp.dao;

import java.util.List;

import jp.co.kcs_grp.dao.bean.M_Category;

public interface M_CategoryDao {

	public List<M_Category> getList() throws Exception;

}
