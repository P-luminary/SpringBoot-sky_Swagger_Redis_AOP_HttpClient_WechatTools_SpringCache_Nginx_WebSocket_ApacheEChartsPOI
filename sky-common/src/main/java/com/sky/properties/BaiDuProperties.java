package com.sky.properties;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
// 多个prefix
@ConfigurationProperties(prefix = "sky.baidu")
@Data
public class BaiDuProperties {
    private String shopAddress;
    private String ak;
}
