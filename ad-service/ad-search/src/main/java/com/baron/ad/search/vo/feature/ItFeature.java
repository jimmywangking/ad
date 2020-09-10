package com.baron.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

/***
 @package com.baron.ad.search.vo.feature
 @author Baron
 @create 2020-09-10-7:08 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItFeature {
    private List<String> its;
}
