package cn.structure.starter.manager.mapper;

import cn.structure.starter.manager.mapper.provider.InsertListSpecialProvider;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * 动态插入
 * @param <T>
 */
@RegisterMapper
public interface InsertListMapper<T> {
    @InsertProvider(
            type = InsertListSpecialProvider.class,
            method = "dynamicSQL"
    )
    int insertList(List<? extends T> var1);
}
