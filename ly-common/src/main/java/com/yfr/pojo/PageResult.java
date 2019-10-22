package com.yfr.pojo;

import java.util.List;

//因为可能其他项目也需要这个类来进行分页，所以设为泛型
public class PageResult<T> {

    //注意这里的属性名和前端的对象的属性名一致，不然前端获取不到数据

    private Long total;//总记录条数
    private Long totalPage;//总页面数
    private List<T> items;//当前页面显示的数据

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
