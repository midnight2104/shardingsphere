/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.shardingjdbc.spring.boot.datasource;

import lombok.SneakyThrows;
import org.apache.shardingsphere.shardingjdbc.spring.boot.util.PropertyUtil;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

/**
 * Hikari datasource properties setter.
 *
 * @author xiayan
 */
public final class HikariDataSourcePropertiesSetter implements DataSourcePropertiesSetter {
    
    @Override
    @SneakyThrows(ReflectiveOperationException.class)
    public void propertiesSet(final Environment environment, final String prefix, final String dataSourceName, final DataSource dataSource) {
        Properties properties = new Properties();
        properties.putAll(PropertyUtil.handle(environment, prefix + dataSourceName.trim() + ".data-source-properties", Map.class));
        Method method = dataSource.getClass().getMethod("setDataSourceProperties", Properties.class);
        method.invoke(dataSource, properties);
    }
    
    @Override
    public boolean support(final DataSource type) {
        return type.getClass().getName().equals("com.zaxxer.hikari.HikariDataSource");
    }
}