package com.hive.complain.model;


public class TradeBean {
	
	private String tradeId;
	private String tradeName;
	private int comNumber;
	private float rate;
	
	
	
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public int getComNumber() {
		return comNumber;
	}
	public void setComNumber(int comNumber) {
		this.comNumber = comNumber;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	
	

}
