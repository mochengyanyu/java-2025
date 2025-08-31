package com.wk.dynamic.multiple;

public class DynamicDatasourceContextHolder {

    private final static ThreadLocal<String> DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<String>();

    public static void setDatasourceContextHolder(String dataSourceName){
        DATASOURCE_CONTEXT_HOLDER.set(dataSourceName);
    }

    public static String getDatasourceContextHolder(){
        return DATASOURCE_CONTEXT_HOLDER.get();
    }

    public static void clear(){
        DATASOURCE_CONTEXT_HOLDER.remove();
    }

}
