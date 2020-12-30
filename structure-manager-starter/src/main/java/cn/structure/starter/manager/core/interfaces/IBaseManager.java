package cn.structure.starter.manager.core.interfaces;


import cn.structure.starter.manager.common.ReqPage;
import cn.structure.starter.manager.common.ResPage;
import cn.structure.starter.manager.common.StructurePageInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @param <T>
 */
public interface IBaseManager<T> {

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    int save(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    int saveBatch(Collection<T> entityList);

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     */
    int saveOrUpdate(T entity);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    int deleteById(Serializable id);

    /**
     * 根据 entity 条件，删除记录
     *
     * @param t 实体类
     */
    int delete(T t);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    int deleteByIds(Collection<? extends Serializable> idList);

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    int updateById(T entity);

    /**
     * 根据 condition 条件，更新记录
     *
     * @param entity        实体对象
     * @param condition     实体条件对象
     */
    int update(T entity,T condition );

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    T getById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    Collection<T> listByIds(Collection<? extends Serializable> idList);

    /**
     * 根据 t 查询一条记录
     *
     * @param t 实体对象类
     */
    T getOne(T t);

    /**
     * 根据 t 条件，查询总记录数
     *
     * @param t 实体类
     */
    int count(T t);

    /**
     * 查询列表
     *
     * @param t 实体类
     */
    List<T> list(T t);


    /**
     * 返回分页实体
     *
     * @param page 分页参数
     */
    ResPage<T> page(ReqPage<T> page);

    /**
     * 返回分页信息
     * @param page 分页参数
     * @return
     */
    StructurePageInfo<T> pageInfo(ReqPage<T> page);

}
