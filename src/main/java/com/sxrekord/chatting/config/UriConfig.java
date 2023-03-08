package com.sxrekord.chatting.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/10 21:30
 */
@Component
@ConfigurationProperties(prefix = "config")
public class UriConfig {
    private List<String> includeUri;

    private List<String> excludeUri;

    public List<String> getIncludeUri() {
        return includeUri;
    }
    public void setIncludeUri(List<String> includeUri) {
        this.includeUri = includeUri;
    }
    public List<String> getExcludeUri() {
        return excludeUri;
    }
    public void setExcludeUri(List<String> excludeUri) {
        this.excludeUri = excludeUri;
    }
}
