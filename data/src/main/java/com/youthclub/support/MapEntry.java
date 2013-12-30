package com.youthclub.support;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */

public class MapEntry<K, V> {
    private K key;
    private V value;

    public K getKey() {
        return key;
    }

    public MapEntry<K, V> setKey(K key) {
        this.key = key;
        return this;
    }

    public V getValue() {
        return value;
    }

    public MapEntry<K, V> setValue(V value) {
        this.value = value;
        return this;
    }
}
