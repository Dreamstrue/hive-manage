package com.hive.defectmanage.model;



public class ComparatorDefect implements java.util.Comparator<DefectRecordBean>{

	
	public int compare(DefectRecordBean bean1, DefectRecordBean bean2) {
		int flag = 0;
		flag = bean2.getReportdate().compareTo(bean1.getReportdate());
		return flag;
	}
}
