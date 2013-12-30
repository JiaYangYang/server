package com.youthclub.producer;

import gnu.trove.map.TMap;

/**
 * Created by frank on 13-12-15.
 */
public interface ProducerResolver<K, V> {

    V resolve(final K that, final TMap<String, Object> params);
}
