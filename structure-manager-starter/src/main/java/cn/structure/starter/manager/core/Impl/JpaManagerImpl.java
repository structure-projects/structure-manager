package cn.structure.starter.manager.core.Impl;

import cn.structure.starter.manager.common.ReqPage;
import cn.structure.starter.manager.common.ResPage;
import cn.structure.starter.manager.common.StructurePageInfo;
import cn.structure.starter.manager.core.interfaces.IBaseManager;
import cn.structure.starter.manager.mapper.JpaMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
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
public class JpaManagerImpl<M extends JpaMapper<T>, T> implements IBaseManager<T> {

    @Autowired
    protected M baseMapper;


    @Override
    public int save(T entity) {
        T save = baseMapper.save(entity);
        if (null == save) {
            return 0;
        }
        return 1;
    }

    @Override
    public int saveBatch(Collection<T> entityList) {
        List<T> list = baseMapper.saveAll(entityList);
        return list.size();
    }

    @Override
    public int saveOrUpdate(T entity) {
        T save = baseMapper.save(entity);
        if (null == save) {
            return 0;
        }
        return 1;
    }

    @Override
    public int deleteById(Serializable id) {
        baseMapper.deleteById(id);
        return 1;
    }

    @Override
    public int delete(T t) {
        baseMapper.delete(t);
        return 1;
    }

    @Override
    public int deleteByIds(Collection<? extends Serializable> idList) {
        List<T> list = new ArrayList<>();

        baseMapper.deleteAll();
        return 0;
    }

    @Override
    public int updateById(T entity) {
        T save = baseMapper.save(entity);
        if (null == save) {
            return 0;
        }
        return 1;
    }

    @Override
    public int update(T entity, T condition) {
        T save = this.baseMapper.save(entity);
        if (null == save) {
            return 0;
        }
        return 1;
    }

    @Override
    public T getById(Serializable id) {
        return this.baseMapper.findById(id).get();
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        return null;
    }

    @Override
    public T getOne(T t) {
        Field[] fields = t.getClass().getFields();

        for (int i = 0; i < fields.length; i++) {
            Id annotation = fields[i].getAnnotation(Id.class);
            if (null != annotation) {
                try {
                    Object o = fields[i].get(t);
                    return this.baseMapper.getOne(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return null;
    }

    @Override
    public int count(T t) {
        return  1;
    }

    @Override
    public List<T> list(T t) {
        return this.baseMapper.findAll();
    }

    @Override
    public ResPage<T> page(ReqPage<T> page) {
        return null;
    }

    @Override
    public StructurePageInfo<T> pageInfo(ReqPage<T> page) {
        return null;
    }
}
