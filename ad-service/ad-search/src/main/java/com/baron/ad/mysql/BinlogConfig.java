package com.baron.ad.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/***
 @package com.baron.ad.mysql
 @author Baron
 @create 2020-09-05-10:03 PM
 */
@Component
@ConfigurationProperties(prefix = "adconf.mysql")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinlogConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;

    private String binlogName;
    private Long position;
}
