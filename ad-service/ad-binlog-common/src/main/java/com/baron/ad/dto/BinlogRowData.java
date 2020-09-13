package com.baron.ad.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/***
 @package com.baron.ad.mysql.dto
 @author Baron
 @create 2020-09-05-8:36 PM
 */
@Data
public class BinlogRowData {

    private TableTemplate tableTemplate;

    private EventType eventType;

    private List<Map<String, String>> after;

    private List<Map<String, String>> before;
}
