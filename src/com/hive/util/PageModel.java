package com.hive.util;



public class PageModel {

	// 查询记录数
	private int totalRecords;
	// 每页多少条数据
	private int pageSize;
	// 第几页
	private int pageNo;
	//首页 
	private int topPageNo;
	//上一页  
	private int previousPageNo;
	//下一页 
	private int nextPageNo;
	//尾页
	private int bottomPageNo;
	//总页数
	private int totalPages;

	/**
	 * 
	 * 总页数
	 * 
	 * @return
	 */

	public int getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}

	/**
	 * 
	 * 取得首页
	 * 
	 * @return
	 */

	public int getTopPageNo() {
		return 1;
	}

	/**
	 * 
	 * 上一页
	 * 
	 * @return
	 */

	public int getPreviousPageNo() {
		if (pageNo <= 1) {
			return 1;
		}
		return pageNo - 1;
	}

	/**
	 * 
	 * 下一页
	 * 
	 * @return
	 */

	public int getNextPageNo() {
		if (pageNo >= getBottomPageNo()) {
			return getBottomPageNo();
		}
		return pageNo + 1;

	}

	/**
	 * 
	 * 取得尾页
	 * 
	 * @return
	 */

	public int getBottomPageNo() {
		return getTotalPages();
	}


	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setTopPageNo(int topPageNo) {
		this.topPageNo = topPageNo;
	}

	public void setPreviousPageNo(int previousPageNo) {
		this.previousPageNo = previousPageNo;
	}

	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	public void setBottomPageNo(int bottomPageNo) {
		this.bottomPageNo = bottomPageNo;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
	public PageModel pageSizeInfo(String pageIndex,int totalRecords,int pageSize){
		
		int pageNo = 1; //默认为第一页
		if(DataUtil.isEmpty(pageIndex)){
			pageNo = 1;
		}else pageNo = Integer.parseInt(pageIndex);
		if(pageNo<1) pageNo = 1;
		
		PageModel pageModel = new PageModel();
		pageModel.setTotalRecords(totalRecords); //总记录数
		pageModel.setPageNo(pageNo); //第几页
		pageModel.setPageSize(pageSize); //每页显示的条数
		//首页
		pageModel.setTopPageNo(pageModel.getTopPageNo()); 
		//上一页
		pageModel.setPreviousPageNo(pageModel.getPreviousPageNo());
		//下一页
		pageModel.setNextPageNo(pageModel.getNextPageNo());
		//尾页
		pageModel.setBottomPageNo(pageModel.getBottomPageNo());
		//总页数
		pageModel.setTotalPages(pageModel.getTotalPages());
		return pageModel;
	}
	
	
	
	
}
