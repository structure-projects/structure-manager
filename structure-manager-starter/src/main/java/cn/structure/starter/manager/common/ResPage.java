package cn.structure.starter.manager.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *     分页出参
 * </p>
 */
@Getter
@Setter
public class ResPage<T> {
    private Integer page;
    private Integer rows;
    private Long total;
    private Date queryTimestamp;
    private List<T> list;

    public <N> ResPage<N> copyResPage(List<T> list){
        ResPage resPage = new ResPage<N>();
        resPage.setPage(this.page);
        resPage.setRows(this.rows);
        resPage.setTotal(this.total);
        resPage.setQueryTimestamp(this.queryTimestamp);
        resPage.setList(list);
        return resPage;
    }

}
