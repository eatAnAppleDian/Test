package com.orange.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Orange
 * @version 1.0.0
 * @Description TODO
 * @createTime 2022年04月17日 14:24:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageVO<T> {
    private List<T> dataList;
    /**
     * 总页数
     */
    private Integer pageTotal;
    /**
     * 总条数
     */
    private Integer count;
    private Integer currentPage;
    private Boolean isFirstPage;
    private Boolean isLastPage;
}
