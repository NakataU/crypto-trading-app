package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.HierarchicalMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Collections;
import java.util.List;

@Configuration
public class MessageConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {


    private static final int LAST_CHILD = 0;

    @Autowired(required = false)
    @Qualifier("bundles")
    private final List<HierarchicalMessageSource> externalMessageSources = Collections.emptyList();

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource rootMessageSource = new ReloadableResourceBundleMessageSource();
        rootMessageSource.setBasenames("classpath:messages");
        rootMessageSource.setDefaultEncoding("UTF-8");

        if (externalMessageSources.isEmpty()) {
            return rootMessageSource;
        }else {
            int sourceSize = externalMessageSources.size();
            for(int i = 0; i < sourceSize; i++) {
                HierarchicalMessageSource currentMessageSource = externalMessageSources.get(i);
                if(hasParent(i, sourceSize-1))
                    currentMessageSource.setParentMessageSource(externalMessageSources.get(i + 1) );
                else
                    currentMessageSource.setParentMessageSource(rootMessageSource);
            }

            return externalMessageSources.get(LAST_CHILD);
        }
    }

    private boolean hasParent(int index, int size){
        return index < size;
    }

}