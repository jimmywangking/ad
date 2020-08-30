package com.baron.ad.index;

/***
 @package com.baron.ad
 @author Baron
 @create 2020-08-30-11:05 PM
 */
public interface IndexAware<K,V> {
    V get(K key);
    void add(K key, V value);
    void update(K key, V value);
    void delete(K key, V value);
}
