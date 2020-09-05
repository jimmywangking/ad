package com.baron.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-05-7:01 PM
 */
public class BinlogServiceTest {
    public static void main(String[] args) throws IOException {
        BinaryLogClient client = new BinaryLogClient(
                "localhost", 3306, "root","root"
        );
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update---------------");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write------------------");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete-------------------");
                System.out.println(data.toString());
            }

        });
        client.connect();
    }
}
