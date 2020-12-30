package cn.structure.starter.manager.base;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseManager<M> {

    @Autowired
    protected M baseMapper;

}
