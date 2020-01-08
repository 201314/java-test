package com.gitee.linzl.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页
 *
 * @author linzhenlie
 * @date 2019/10/14
 */
@Setter
@Getter
public class Page<T> {
    /**
     * 需要分页的结果集
     */
    private List<T> data;
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页行数,默认每页10行
     */
    private int pageSize = 15;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 总行数
     */
    private int total;
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
     * 当前页，起始记录行号,用于mysql的limit查询
     */
    private int startRecord;

    /**
     * 当点击了上一页或者下一页或者其他点击页数操作后，再点击条件查询，要将当前页currentPage=1传入
     *
     * @param data     需要分页的结果集
     * @param pageSize 每页行数
     * @param page     当前页 表示点击上一页的页码，下一页的页码，或者是点击某一页的页码
     * @param total    总页数
     */
    public Page(List<T> data, int pageSize, int page, int total) {
        this.data = data;
        this.pageSize = pageSize > 0 ? pageSize : this.pageSize;

        // 计算总行数
        this.total = total;

        // 计算当前页,当前页小于1,则显示第1页;当前页大于totalPage,则显示totalPage
        this.page = page < this.firstPage ? this.firstPage
                : (page > this.pages ? this.pages : page);

        // 计算起始记录行
        this.startRecord = (this.page - 1) * this.pageSize;
    }

    /**
     * 总页数
     *
     * @return
     */
    public int getPages() {
        this.pages = (this.total + this.pageSize - 1) / this.pageSize;
        return this.pages;
    }

    /**
     * 上一页
     *
     * @return
     */
    public int getPrePage() {
        this.prePage = this.page <= this.firstPage ? this.firstPage : (this.page - 1);
        return this.prePage;
    }

    /**
     * 下一页
     *
     * @return
     */
    public int getNextPage() {
        this.nextPage = this.page >= this.pages ? this.pages : (this.page + 1);
        return this.nextPage;
    }
}
