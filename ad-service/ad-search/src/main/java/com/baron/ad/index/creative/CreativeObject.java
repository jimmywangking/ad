package com.baron.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/***
 @package com.baron.ad.index
 @author Baron
 @create 2020-08-31-5:25 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeObject {
    private Long adId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer weight;
    private Long size;
    private Integer auditStatus;
    private String adUrl;

    public void update(CreativeObject newObject){
        if (null != newObject.getAdId()){
            this.adId = newObject.getAdId();
        }
        if (StringUtils.isNotEmpty(newObject.getName())){
            this.name = newObject.getName();
        }
        if (null != newObject.getType()){
            this.type = newObject.getType();
        }
        if (null != newObject.getMaterialType()) {
            this.materialType = newObject.getMaterialType();
        }
        if (null != newObject.getHeight()){
            this.height = newObject.getHeight();
        }
        if (null != newObject.getWeight()) {
            this.weight = newObject.getWeight();
        }
        if (null != newObject.getSize()) {
            this.size = newObject.getSize();
        }
        if (null != newObject.getAuditStatus()) {
            this.auditStatus = newObject.getAuditStatus();
        }
        if (StringUtils.isNotEmpty(newObject.getAdUrl())){
            this.adUrl = newObject.getAdUrl();
        }
    }
}
