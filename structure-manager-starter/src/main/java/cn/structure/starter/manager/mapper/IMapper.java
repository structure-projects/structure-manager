package cn.structure.starter.manager.mapper;


import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author chuck
 * @version 1.0.1
 * @since 2020/12/29 22:33
 */
public interface IMapper<T> {

    int insert(T record);

    int insertSelective(T record);

    int updateByPrimaryKey(T record);

    int updateByPrimaryKeySelective(T record);
    int deleteByPrimaryKey(Serializable id);

    void batchInsert(@Param("items") List<T> items);

    T selectByPrimaryKey(Serializable id);
}
