package com.hive.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SEQUENCE_ZXT")
public class Sequence {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid") // 通过注解方式生成一个generator
	@GeneratedValue(generator = "idGenerator") // 使用generator
	private String id;	//标识编号
	@Column(name = "SEQ_TYPE",nullable=false,unique=true, length = 100)//序号类型，确定类型唯一的标示
	private String seqType;
	@Column(name = "SEQ_HEAD", length = 32)//序号头部，可为空，比如ZZ001201602001000001的头部为ZZ
	private String seqHead;
	@Column(name = "SEQ_CODE", length = 32)//类型代码，可为空，比如上例中ZZ后的001，而001具有特定的含义，比如它可以代表家具、珠宝或者红酒什么的
	private String seqCode;
	@Column(name = "SEQ_NAME", length = 32)//此序号名称，即此序号用于何种场景
	private String seqName;
	@Column(name = "CURRENT_VAL",nullable=false, length = 32)//序号当前值
	private String currentVal;
	@Column(name = "INCREMENT_VAL", length = 32)//序号递增区间
	private String incrementVal;
	@Column(name = "T_FORMAT", length = 20)//序号生成的时间格式
	private String tFormat;
	@Column(name = "DIGIT_NUMBER", length = 10)//序号最后的可用位数和初始值，比如0001表示可用位数为4，最大值为9999，而0001为起始值
	private String digitNumber;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeqType() {
		return seqType;
	}
	public void setSeqType(String seqType) {
		this.seqType = seqType;
	}
	public String getSeqName() {
		return seqName;
	}
	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}
	public String getCurrentVal() {
		return currentVal;
	}
	public void setCurrentVal(String currentVal) {
		this.currentVal = currentVal;
	}
	public String getIncrementVal() {
		return incrementVal;
	}
	public void setIncrementVal(String incrementVal) {
		this.incrementVal = incrementVal;
	}
	public String gettFormat() {
		return tFormat;
	}
	public void settFormat(String tFormat) {
		this.tFormat = tFormat;
	}
	public String getDigitNumber() {
		return digitNumber;
	}
	public void setDigitNumber(String digitNumber) {
		this.digitNumber = digitNumber;
	}
	public String getSeqCode() {
		return seqCode;
	}
	public void setSeqCode(String seqCode) {
		this.seqCode = seqCode;
	}
	public String getSeqHead() {
		return seqHead;
	}
	public void setSeqHead(String seqHead) {
		this.seqHead = seqHead;
	}
	
	
}