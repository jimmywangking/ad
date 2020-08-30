package com.baron.ad.entity.unit_condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/***
 @package com.baron.ad.entity.unit_condition
 @author Baron
 @create 2020-08-29-3:11 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creative_unit")
public class CreativeUnit {
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Column(name = "creative_id", nullable = false)
    private Long creativeId;

    public CreativeUnit(Long unitId, Long creativeId) {
        this.unitId = unitId;
        this.creativeId = creativeId;
    }
}
