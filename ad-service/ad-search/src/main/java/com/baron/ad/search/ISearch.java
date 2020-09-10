package com.baron.ad.search;

import com.baron.ad.search.vo.SearchRequest;
import com.baron.ad.search.vo.SearchResponse;

/***
 @package com.baron.ad.search
 @author Baron
 @create 2020-09-08-10:16 PM
 */
public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}
