package cn.structure.starter.manager.core.Impl;

import cn.structure.starter.manager.common.ReqPage;
import cn.structure.starter.manager.common.ResPage;
import cn.structure.starter.manager.common.StructurePageInfo;
import cn.structure.starter.manager.core.interfaces.IBaseManager;
import cn.structure.starter.manager.exception.NotRealizeException;
import cn.structure.starter.manager.mapper.IMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 *  manager
 * </p>
 *
 * @author chuck
 * @version 1.0.1
 * @since 2020/12/29 20:24
 */
public class DefaultManagerImpl<M extends IMapper<T>, T> implements IBaseManager<T> {

    @Autowired
    protected M baseMapper;

    @Override
    public int save(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    public int saveBatch(Collection<T> entityList) {
        List<T> list = new ArrayList<>();
        entityList.iterator().forEachRemaining(i ->{
            list.add(i);
        });
        baseMapper.batchInsert(list);
        return list.size();
    }

    @Override
    public int saveOrUpdate(T entity) {
        Field[] fields = entity.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {
            Id annotation = fields[i].getAnnotation(Id.class);
            if (annotation!= null) {
                try {
                    Object id = fields[i].get(entity);
                    if (null == id) {
                        return baseMapper.insertSelective(entity);
                    }else {
                        return baseMapper.updateByPrimaryKeySelective(entity);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return 0;
    }

    @Override
    public int deleteById(Serializable id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delete(T t) {
        throw new NotRealizeException();
    }

    @Override
    public int deleteByIds(Collection<? extends Serializable> idList) {
        throw new NotRealizeException();
    }

    @Override
    public int updateById(T entity) {
        return this.baseMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int update(T entity, T condition) {
        throw new NotRealizeException();
    }

    @Override
    public T getById(Serializable id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        throw new NotRealizeException();
    }

    @Override
    public T getOne(T t) {
        throw new NotRealizeException();
    }

    @Override
    public int count(T t) {
        throw new NotRealizeException();
    }

    @Override
    public List<T> list(T t) {
        throw new NotRealizeException();
    }

    @Override
    public ResPage<T> page(ReqPage<T> page) {
        throw new NotRealizeException();
    }

    @Override
    public StructurePageInfo<T> pageInfo(ReqPage<T> page) {
        throw new NotRealizeException();
    }
}
