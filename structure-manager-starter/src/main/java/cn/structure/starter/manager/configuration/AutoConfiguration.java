package cn.structure.starter.manager.configuration;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>
 *     自动装载配置类
 * </p>
 * @author chuck
 * @since 2020-12-30
 * @version 1.0.1
 */
@Configuration
public class AutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "structure.manager.plus.insert-update", havingValue = "true")
    @ConditionalOnMissingBean(MetaObjectHandler.class)
    public LocalDateMetaObjectHandler localDateMetaObjectHandler(){
        return new LocalDateMetaObjectHandler();
    }

    @Bean
    @ConditionalOnProperty(value = "structure.manager.plus.injector", havingValue = "true")
    @ConditionalOnMissingBean(ISqlInjector.class)
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

}
