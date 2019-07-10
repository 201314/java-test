package com.gitee.linzl.util;

import java.util.List;

public class PageUtil {
	// 需要分页的结果集
	private List<?> list;
	// 每页行数,默认每页10行
	private int pagesize = 10;
	// 总页数
	private int totalPage;
	// 总行数
	private int totalRecords;

	// 当前页
	private int currentPage;
	// 第一页
	private int firstPage = 1;
	// 上一页
	private int prePage;
	// 下一页
	private int nextPage;
	// 最后一页,就是总页数
	private int endPage;

	// 对list分页使用
	// 起始记录行
	private int startRecord;
	// 终点记录行
	private int endRecord;

	/**
	 * 当点击了上一页或者下一页或者其他点击页数操作后，再点击条件查询，要将当前页currentPage=1传入
	 * 
	 * @param list        需要分页的结果集
	 * @param pagesize    每页行数
	 * @param currentPage 当前页 表示点击上一页的页码，下一页的页码，或者是点击某一页的页码
	 */
	public void getPageUtilResult(List<?> list, int pagesize, int currentPage) {
		this.pagesize = pagesize > 0 ? pagesize : this.pagesize;
		if (list != null && list.size() > 0) {
			// 计算总行数
			this.totalRecords = list.size();

			// 计算总页数
			this.totalPage = (this.totalRecords + this.pagesize - 1) / this.pagesize;

			// 计算当前页,当前页小于1,则显示第1页;当前页大于totalPage,则显示totalPage
			this.currentPage = currentPage < this.firstPage ? this.firstPage
					: (currentPage > this.totalPage ? this.totalPage : currentPage);
			// 计算上一页
			this.prePage = this.currentPage <= this.firstPage ? this.firstPage : (this.currentPage - 1);

			// 计算下一页
			this.nextPage = this.currentPage >= this.totalPage ? this.totalPage : (this.currentPage + 1);

			// 计算起始记录行
			this.startRecord = (this.currentPage - 1) * this.pagesize;

		}
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getEndRecord() {
		return endRecord;
	}

	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}
}
