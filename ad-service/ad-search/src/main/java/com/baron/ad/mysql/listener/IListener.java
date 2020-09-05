package com.baron.ad.mysql.listener;

import com.baron.ad.mysql.dto.BinlogRowData;

/***
 @package com.baron.ad.mysql
 @author Baron
 @create 2020-09-05-8:39 PM
 */
public interface IListener {

    void register();

    void onEvent(BinlogRowData eventData);
}
