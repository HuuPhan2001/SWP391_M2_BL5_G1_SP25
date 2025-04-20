/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.fpt.common;

/**
 *
 * @author MTTTT
 */
public class Pagination {

    private int page = 1;
    private int size = 10;
    private int totalPages;
    private long totalElements;
    private String sortBy = "";
    private String sortDirection = "DESC";
    private long currentElements;

    public Pagination() {
    }

    public Pagination(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages(int totalPage) {
        return totalPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getOffset() {
        int offset = (page - 1) * size;
        return Math.max(offset, 0);
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public long getCurrentElements() {
        return currentElements;
    }

    public void setCurrentElements(int currentElements) {
        this.currentElements = currentElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setCurrentElements(long currentElements) {
        this.currentElements = currentElements;
    }

    @Override
    public String toString() {
        return "Pagination{" + "page=" + page + ", size=" + size + ", totalPages=" + totalPages + ", totalElements=" + totalElements + ", sortBy=" + sortBy + ", sortDirection=" + sortDirection + ", currentElements=" + currentElements + '}';
    }

}
