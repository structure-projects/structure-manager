package cn.structure.starter.manager.core.Impl;


import cn.structure.starter.manager.common.ReqPage;
import cn.structure.starter.manager.common.ResPage;
import cn.structure.starter.manager.common.StructurePageInfo;
import cn.structure.starter.manager.core.interfaces.IBaseManager;
import cn.structure.starter.manager.mapper.PlusMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 *
 * </p>
 * @author chuck
 * @version 1.0.1
 * @since 2020/12/29 20:24
 * @param <M>
 * @param <T>
 */
public class PlusManagerImpl<M extends PlusMapper<T>, T> implements IBaseManager<T> {

    @Autowired
    protected M baseMapper;

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * 获取 SqlStatement
     *
     * @param sqlMethod ignore
     * @return ignore
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }


    @Override
    public int save(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int saveBatch(Collection<T> entityList) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        int i = 0;
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % entityList.size() == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
        return i;
    }


    @Override
    public int deleteById(Serializable id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int delete(T t) {
        return this.baseMapper.delete(new QueryWrapper<>(t));
    }

    @Override
    public int deleteByIds(Collection<? extends Serializable> idList) {
        return this.baseMapper.deleteBatchIds(idList);
    }

    @Override
    public int updateById(T entity) {
        return this.baseMapper.updateById(entity);
    }

    @Override
    public int update(T entity, T condition) {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper(condition);
        return baseMapper.update(entity,updateWrapper);
    }

    @Override
    public int saveOrUpdate(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
            return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ? save(entity) : updateById(entity);
        }
        return 0;
    }

    @Override
    public T getById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        return baseMapper.selectBatchIds(idList);
    }

    @Override
    public T getOne(T t) {
        return baseMapper.selectOne(new QueryWrapper<>(t));
    }

    @Override
    public int count(T t) {
        return baseMapper.selectCount(new QueryWrapper<>(t));
    }

    @Override
    public List<T> list(T t) {
        return baseMapper.selectList(new QueryWrapper<>(t));
    }

    @Override
    public ResPage<T> page(ReqPage<T> page) {
        return pageInfo(page).getResPage(page.getQueryTimestamp());
    }

    @Override
    public StructurePageInfo<T> pageInfo(ReqPage<T> page) {
        PageHelper.startPage(page.getPage(),page.getRows());
        List<T> list = baseMapper.selectList(new QueryWrapper<>(page.getParameter()));
        StructurePageInfo<T> structurePageInfo = new StructurePageInfo<>(list);
        return structurePageInfo;
    }

}
