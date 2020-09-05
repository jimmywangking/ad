package com.baron.ad.mysql;

import com.baron.ad.mysql.listener.AggregationListener;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.sun.tools.javadoc.Start;
import jdk.nashorn.api.scripting.ScriptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static sun.jvm.hotspot.runtime.PerfMemory.start;

/***
 @package com.baron.ad.mysql
 @author Baron
 @create 2020-09-05-10:05 PM
 */
@Slf4j
@Component
public class BinlogClient {
    private BinaryLogClient client;
    private final BinlogConfig config;
    private final AggregationListener listener;

    @Autowired
    public BinlogClient(BinlogConfig config, AggregationListener listener) {
        this.config = config;
        this.listener = listener;
    }

    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(config.getHost(), config.getPort(), config.getUsername(), config.getPassword());
            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }
            client.registerEventListener(listener);

            log.info("connecting to mysql start");
            try {
                client.connect();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            log.info("connecting to mysql done");
        }).start();
    }

    public void close(){
        try {
            client.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
