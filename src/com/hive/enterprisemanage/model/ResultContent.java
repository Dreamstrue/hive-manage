package com.hive.enterprisemanage.model;
import java.util.List;



public class ResultContent {
	
	private String status;
	private String message;
	private int total;
	private List<CoorDinate> results;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<CoorDinate> getResults() {
		return results;
	}
	public void setResults(List<CoorDinate> results) {
		this.results = results;
	}
	
	
	
	

}
