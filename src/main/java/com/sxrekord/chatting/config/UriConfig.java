package com.sxrekord.chatting.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/10 21:30
 */
@Component
@ConfigurationProperties(prefix = "config")
@Setter
@Getter
public class UriConfig {
    private List<String> includeUri;
    private List<String> excludeUri;

}
