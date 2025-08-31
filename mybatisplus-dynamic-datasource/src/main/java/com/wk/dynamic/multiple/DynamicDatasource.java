package com.wk.dynamic.multiple;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDatasource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDatasourceContextHolder.getDatasourceContextHolder();
    }
}
