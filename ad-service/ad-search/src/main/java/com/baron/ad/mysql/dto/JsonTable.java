package com.baron.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-09-05-7:26 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {

    private String tableName;
    private Integer level;

    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {

        private String column;
    }
}
