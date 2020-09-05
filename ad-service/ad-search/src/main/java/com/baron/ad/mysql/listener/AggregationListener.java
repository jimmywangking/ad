package com.baron.ad.mysql.listener;

import com.baron.ad.mysql.TemplateHolder;
import com.baron.ad.mysql.dto.BinlogRowData;
import com.baron.ad.mysql.dto.TableTemplate;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/***
 @package com.baron.ad.mysql.listener
 @author Baron
 @create 2020-09-05-8:41 PM
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;

    private Map<String, IListener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genKey(String dbName, String tableName) {
        return  dbName + ":" + tableName;
    }

    public void register(String _dbName, String _tableName, IListener iListener) {
        log.info("register : {}-{} ", _dbName, _tableName);
        this.listenerMap.put(genKey(_dbName, _tableName),iListener);
    }


    @Override
    public void onEvent(Event event) {

        EventType type = event.getHeader().getEventType();
        log.debug("event type: {} ", type);
        if(type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        if (type != EventType.EXT_UPDATE_ROWS && type != EventType.EXT_DELETE_ROWS && type != EventType.EXT_UPDATE_ROWS){
            return;
        }

        //表名库名
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)){
            log.error("no meta data event");
            return;
        }

        String key = genKey(this.dbName, this.tableName);
        IListener listener = this.listenerMap.get(key);
        if (null == listener){
            log.debug("skip: {}", key);
            return;
        }

        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);

        } catch (Exception ex){
            log.error(ex.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }
        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }

    private BinlogRowData buildRowData(EventData eventData){

        TableTemplate table = templateHolder.getTable(tableName);
        if (null == table){
            return null;
        }

        List<Map<String, String >> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)){
            Map<String, String> afterMap = new HashMap<>();
            int colLength = after.length;
            for (int ix = 0; ix < colLength; ++ix){
                //当前列
                String colName = table.getPosMap().get(ix);

                if (null == colName) {
                    continue;
                }
                String colValue = after[ix].toString();
                afterMap.put(colName, colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTableTemplate(table);

        return rowData;
    }
}
