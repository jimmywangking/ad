package com.baron.ad.dao;

import com.baron.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-29-4:08 PM
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    AdUser findByUsername(String userName);
}
