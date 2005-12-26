package org.seasar.dao.annotation.tiger.impl;

import java.io.Serializable;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Column;

/**
 * WEB 画面利用履歴クラス<br>
 * <br>
 * File Information :<br>
 * 	$Header: /cvsroot/seasar/s2-dao/src/test/org/seasar/dao/impl/FormUseHistory.java,v 1.1 2005/01/18 10:42:18 higa Exp $<br>
 * @author ARGO21
 * @version 1.0
 */
@Bean(table="CWEB_FORM_HIST")
public class FormUseHistory implements Serializable {
	//
	// 定数
	//
	
	
	
	
	
	
	//
	// インスタンスフィールド
	//
	
	/** WEBユーザコード */
	private String webUserCode;
	
	/** WEB画面ID */
	private String webFormId;
	
	/** 参照タイムスタンプ */
	private java.sql.Timestamp referenceTimestamp;
	
	/** 参照ホストIP */
	private String referenceHostIp;
	
	//
	// インスタンスメソッド
	//
	
	/**
	 * WEBユーザコード取得
	 * @return String
	 */
	@Column("W_USER_CD")
	public String getWebUserCode() {
		return this.webUserCode;
	}
	/**
	 * WEBユーザコード設定
	 * @param webUserCode
	 */
	public void setWebUserCode(String webUserCode) {
		this.webUserCode = webUserCode;
	}
	/**
	 * WEB画面ID取得
	 * @return String
	 */
	public String getWebFormId() {
		return this.webFormId;
	}

	/**
	 * WEB画面ID設定
	 * @param webFormId
	 */
	@Column("W_FORM_ID")
	public void setWebFormId(String webFormId) {
		this.webFormId = webFormId;
	}
	/**
	 * 参照タイムスタンプ取得
	 * @return java.sql.Timestamp
	 */
	public java.sql.Timestamp getReferenceTimestamp() {
		return this.referenceTimestamp;
	}

	/**
	 * 参照タイムスタンプ設定
	 * @param referenceTimestamp
	 */
	@Column("REF_TIMESTAMP")
	public void setReferenceTimestamp(java.sql.Timestamp referenceTimestamp) {
		this.referenceTimestamp = referenceTimestamp;
	}
	/**
	 * 参照ホストIP取得
	 * @return String
	 */
	public String getReferenceHostIp() {
		return this.referenceHostIp;
	}

	/**
	 * 参照ホストIP設定
	 * @param referenceHostIp
	 */
	@Column("REF_HOST_IP")
	public void setReferenceHostIp(String referenceHostIp) {
		this.referenceHostIp = referenceHostIp;
	}
	/**
	 * 文字列化
	 * @return 文字列
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("webUserCode[").append(this.webUserCode).append("]");
		buffer.append("webFormId[").append(this.webFormId).append("]");
		buffer.append("referenceTimestamp[").append(this.referenceTimestamp).append("]");
		buffer.append("referenceHostIp[").append(this.referenceHostIp).append("]");
		return buffer.toString();
	}
}

