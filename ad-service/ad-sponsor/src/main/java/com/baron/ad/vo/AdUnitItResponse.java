package com.baron.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 @package com.baron.ad.vo
 @author Baron
 @create 2020-08-30-2:12 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItResponse {
    private List<Long> ids;
}
