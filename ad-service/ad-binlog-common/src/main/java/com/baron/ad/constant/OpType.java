package com.baron.ad.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-03-1:55 AM
 */
public enum OpType {
    ADD,
    UPDATE,
    DELETE,
    OTHER;

    public static OpType to (EventType eventType) {
        switch (eventType) {
            case EXT_DELETE_ROWS: return DELETE;
            case EXT_UPDATE_ROWS: return UPDATE;
            case EXT_WRITE_ROWS: return ADD;
            default: return OTHER;
        }
    }
}
