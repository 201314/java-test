package com.gitee.linzl.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Page<T> {
    /**
     * 需要分页的结果集
     */
    private List<T> data;
    /**
     * 每页行数,默认每页10行
     */
    private int pageSize = 15;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 总行数
     */
    private int totalRecords;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 第一页
     */
    private int firstPage = 1;
    /**
     * 上一页
     */
    private int prePage;
    /**
     * 下一页
     */
    private int nextPage;
    /**
     * 最后一页,就是总页数
     */
    private int endPage;

    /**
     * 当前页，起始记录行号
     */
    private int startRecord;
    /**
     * 终点记录行
     */
    private int endRecord;

    /**
     * 当点击了上一页或者下一页或者其他点击页数操作后，再点击条件查询，要将当前页currentPage=1传入
     *
     * @param data        需要分页的结果集
     * @param pageSize    每页行数
     * @param currentPage 当前页 表示点击上一页的页码，下一页的页码，或者是点击某一页的页码
     * @param total       总页数
     */
    public Page(List<T> data, int pageSize, int currentPage, int total) {
        this.data = data;
        this.pageSize = pageSize > 0 ? pageSize : this.pageSize;

        // 计算总行数
        this.totalRecords = total;

        // 计算当前页,当前页小于1,则显示第1页;当前页大于totalPage,则显示totalPage
        this.currentPage = currentPage < this.firstPage ? this.firstPage
                : (currentPage > this.totalPages ? this.totalPages : currentPage);

        // 计算起始记录行
        this.startRecord = (this.currentPage - 1) * this.pageSize;
    }

    public int getTotalPages() {
        // 计算总页数
        this.totalPages = (this.totalRecords + this.pageSize - 1) / this.pageSize;
        return this.totalPages;
    }

    public int getPrePage() {
        // 计算上一页
        this.prePage = this.currentPage <= this.firstPage ? this.firstPage : (this.currentPage - 1);
        return this.prePage;
    }

    public int getNextPage() {
        // 计算下一页
        this.nextPage = this.currentPage >= this.totalPages ? this.totalPages : (this.currentPage + 1);
        return this.nextPage;
    }
}
