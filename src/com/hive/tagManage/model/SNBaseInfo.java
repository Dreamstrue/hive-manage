package com.hive.tagManage.model;

import java.util.Date;

import com.hive.tagManage.entity.SNBase;
import com.hive.tagManage.entity.SNBatch;
import com.hive.util.DateUtil;


public class SNBaseInfo extends SNBase{

	private SNBatch snBatchInfo;
	private String allotTimeStr;
	private String statusStr;
	
	
	public SNBatch getSnBatchInfo() {
		return snBatchInfo;
	}
	public void setSnBatchInfo(SNBatch snBatchInfo) {
		this.snBatchInfo = snBatchInfo;
	}
	public String getAllotTimeStr() {
		return allotTimeStr;
	}
	public void setAllotTimeStr(Date allotTime) {
		this.allotTimeStr = DateUtil.formatDateTime(allotTime);
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String status) {
		if(status.equals("0")){
			this.statusStr = "未印刷";
		}else if(status.equals("1")){
			this.statusStr = "入库";
		}else if(status.equals("2")){
			this.statusStr = "出库";
		}else if(status.equals("3")){
			this.statusStr = "绑定产品";
		}else if(status.equals("4")){
			this.statusStr = "作废";
		}else if(status.equals("5")){//20150921 yf add
			this.statusStr = "印刷中";
		}else if(status.equals("6")){//20150921 yf add
			this.statusStr = "已印刷";
		}else{
			this.statusStr = "空";
		}
	}
	
	
}
