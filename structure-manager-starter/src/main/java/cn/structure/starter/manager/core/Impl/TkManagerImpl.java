package cn.structure.starter.manager.core.Impl;


import cn.structure.starter.manager.common.ReqPage;
import cn.structure.starter.manager.common.ResPage;
import cn.structure.starter.manager.common.StructurePageInfo;
import cn.structure.starter.manager.core.interfaces.IBaseManager;
import cn.structure.starter.manager.mapper.TkMapper;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * <p>
 *
 * </p>
 * @author chuck
 * @version 1.0.1
 * @since 2020/12/29 20:24
 * @param <M>
 * @param <T>
 */
public class TkManagerImpl<M extends TkMapper<T>, T> implements IBaseManager<T> {

    @Autowired
    protected M baseMapper;

    @Override
    public int save(T entity) {
        return baseMapper.insertSelective(entity);
    }

    @Override
    public int saveBatch(Collection<T> entityList) {
        List<T> list = new ArrayList<>();
        entityList.iterator().forEachRemaining(it ->{
            list.add(it);
        });
        return baseMapper.insertList(list);
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
        return this.baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delete(T t) {
        return this.baseMapper.delete(t);
    }

    @Override
    public int deleteByIds(Collection<? extends Serializable> idList) {
        String ids = idList.stream().map(i -> (i).toString()).collect(Collectors.joining(","));
        return this.baseMapper.deleteByIds(ids);
    }

    @Override
    public int updateById(T entity) {
        return this.baseMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int update(T entity, T condition) {
        Example example = new Example(condition.getClass());
        Example.Criteria criteria = example.createCriteria();
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(condition));
        for (String key :jsonObject.keySet()) {
            criteria.andEqualTo(key,jsonObject.get(key));
        }
        return this.baseMapper.updateByConditionSelective(entity,example);
    }

    @Override
    public T getById(Serializable id) {
        return this.baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        String ids = idList.stream().map(i -> (i).toString()).collect(Collectors.joining(","));
        return this.baseMapper.selectByIds(ids);
    }


    @Override
    public T getOne(T t) {
        return this.baseMapper.selectOne(t);
    }

    @Override
    public int count(T t) {
        return this.baseMapper.selectCount(t);
    }

    @Override
    public List<T> list(T t) {
        return this.baseMapper.select(t);
    }

    @Override
    public ResPage<T> page(ReqPage<T> page) {
        return pageInfo(page).getResPage(page.getQueryTimestamp());
    }

    @Override
    public StructurePageInfo<T> pageInfo(ReqPage<T> page) {
        T parameter = page.getParameter();
        PageHelper.startPage(page.getPage(),page.getRows());
        List<T> list ;
        if (null != parameter) {
            list = this.baseMapper.select(parameter);
        } else {
            list = this.baseMapper.selectAll();
        }
        StructurePageInfo<T> structurePageInfo = new StructurePageInfo<>(list);
        return structurePageInfo;
    }

}