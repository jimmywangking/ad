package com.baron.ad.mysql.listener.impl;

import com.baron.ad.constant.Constant;
import com.baron.ad.constant.OpType;
import com.baron.ad.dto.BinlogRowData;
import com.baron.ad.dto.MySqlRowData;
import com.baron.ad.dto.TableTemplate;
import com.baron.ad.mysql.listener.AggregationListener;
import com.baron.ad.mysql.listener.IListener;
import com.baron.ad.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 @package com.baron.ad.mysql.listener
 @author Baron
 @create 2020-09-05-9:44 PM
 */
@Slf4j
@Component
public class IncrementListener implements IListener {

    @Resource
    private ISender sender;

    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }


    @Override
    @PostConstruct
    public void register() {

        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {

        TableTemplate tableTemplate = eventData.getTableTemplate();
        EventType eventType = eventData.getEventType();

        //包装
        MySqlRowData rowData = new MySqlRowData();
        rowData.setTableName(tableTemplate.getTableName());
        rowData.setLevel(eventData.getTableTemplate().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        //对应摸板子段
        List<String> fieldList =  tableTemplate.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList){
            return;
        }
        for (Map<String, String> afterMap : eventData.getAfter()){
            Map<String, String> _afterMap = new HashMap<>();
            for (Map.Entry<String, String> entry : afterMap.entrySet()){
                String colName = entry.getKey();
                String colValue = entry.getValue();
                _afterMap.put(colName, colValue);
            }
            rowData.getFieldValueMap().add(_afterMap);

        }
        sender.sender(rowData);
    }
}
