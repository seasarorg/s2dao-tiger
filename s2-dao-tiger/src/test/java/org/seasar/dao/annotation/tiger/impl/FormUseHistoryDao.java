package org.seasar.dao.annotation.tiger.impl;

import java.util.List;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

/**
 * WEB 画面利用履歴Daoクラス<br>
 * <br>
 * File Information :<br>
 * 	$Header: /cvsroot/seasar/s2-dao/src/test/org/seasar/dao/impl/FormUseHistoryDao.java,v 1.1 2005/01/18 10:42:18 higa Exp $<br>
 *
 * @author ARGO21
 * @version 1.0
 */
@S2Dao(bean=FormUseHistory.class)
public interface FormUseHistoryDao {
	
	//
	// インスタンスメソッド
	//
	
	/**
	 * インサート 
	 * @param formUseHistory WEB 画面利用履歴
	 * @return 登録した数
	 */
	int insert(FormUseHistory formUseHistory);
	
	/**
	 * エンティティ取得
	 * @param webUserCode
	 * @param webFormId
	 * @return WEB 画面利用履歴
	 */
	@Arguments({"W_USER_CD","W_FORM_ID"})
	FormUseHistory getEntity(String webUserCode,String webFormId);
	
	/**
	 * リスト取得
	 * @return WEB 画面利用履歴のリスト
	 */
	List getList();
	
	//
	// 追加メソッド
	//
	
}

