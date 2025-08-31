package com.wk.dynamic.aop;

import com.wk.dynamic.annon.DS;
import com.wk.dynamic.multiple.DynamicDatasourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@Order(-1)
public class DataSourceAspect {

    @Pointcut("@within(com.wk.dynamic.annon.DS) || @annotation(com.wk.dynamic.annon.DS)")
    public void piontcut(){

    }

    @Before("piontcut() && @annotation(ds)")
    public void before(DS ds){
        log.info("切换数据源:{}",ds.value().getValue());
        DynamicDatasourceContextHolder.setDatasourceContextHolder(ds.value().getValue());
    }

    @After("piontcut()")
    public void after(){
        DynamicDatasourceContextHolder.clear();
    }

}
