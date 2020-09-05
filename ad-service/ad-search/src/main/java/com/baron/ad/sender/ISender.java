package com.baron.ad.sender;

import com.baron.ad.mysql.dto.MySqlRowData;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-05-9:43 PM
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
