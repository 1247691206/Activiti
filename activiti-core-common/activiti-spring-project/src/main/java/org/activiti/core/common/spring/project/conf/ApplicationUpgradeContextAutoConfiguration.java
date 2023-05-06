/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.core.common.spring.project.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.core.common.spring.project.ApplicationUpgradeContextService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.ResourcePatternResolver;

@AutoConfiguration
public class ApplicationUpgradeContextAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnMissingClass(value = "org.springframework.http.converter.json.Jackson2ObjectMapperBuilder")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ApplicationUpgradeContextService applicationUpgradeContextService(@Value("${project.manifest.file.path:classpath:/default-app.json}") String absolutePath,
                                                                             @Value("${application.version:0}") Integer enforcedAppVersion,
                                                                             @Value("${activiti.deploy.after-rollback:false}") Boolean isRollbackDeployment,
                                                                             ObjectMapper objectMapper,
                                                                             ResourcePatternResolver resourceLoader) {
        return new ApplicationUpgradeContextService(absolutePath,
            enforcedAppVersion,
            isRollbackDeployment,
            objectMapper,
            resourceLoader);
    }
}
