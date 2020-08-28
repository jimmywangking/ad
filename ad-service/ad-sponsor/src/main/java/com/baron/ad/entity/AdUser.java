package com.baron.ad.entity;

import com.baron.ad.constants.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-27-4:20 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_user")
public class AdUser {
//
//    id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
//    username` varchar(128) NOT NULL DEFAULT '' COMMENT '用户名',
//    token` varchar(256) NOT NULL DEFAULT '' COMMENT '给用户生成的 token',
//    user_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态',
//    create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
//    update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdUser(String username, String token) {
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getStatus();
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

}
