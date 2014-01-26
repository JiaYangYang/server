package com.youthclub.producer;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import org.infinispan.tree.Fqn;
import org.infinispan.tree.TreeCache;

public class Producer<K, V> {

    protected K k;
    protected TreeCache<Fqn, V> cache;
    protected String region;
    protected Fqn key;
    protected ProducerResolver<K, V> resolver;
    protected TMap<String, Object> params = new THashMap<>();

    public V produce() throws Exception {
        if (region == null) {
            return null;
        }
        final Fqn region = Fqn.fromElements(this.region);
        if (key == null) {
            key = Fqn.fromElements(k);
        }
        return cached(region, key);
    }

    protected V cached(final Fqn region, final Fqn key) throws Exception {
        if (cache == null) {
            return null;
        }
        final V cached = cache.get(region, key);
        if (cached != null) {
            return cached;
        }
        V ret = resolver.resolve(k, params);
        cache.put(region, key, ret);

        return ret;
    }

    protected void invalidate(final Fqn node) {
        if (cache == null) {
            return;
        }
        cache.removeNode(node);
    }

    protected void invalidate(final Fqn node, final Fqn key) {
        if (cache == null) {
            return;
        }
        cache.remove(node, key);
    }

    public Producer setK(K k) {
        this.k = k;
        return this;
    }

    public Producer setCache(TreeCache<Fqn, V> cache) {
        this.cache = cache;
        return this;
    }

    public Producer setRegion(String region) {
        this.region = region;
        return this;
    }

    public Producer setKey(Fqn key) {
        this.key = key;
        return this;
    }

    public Producer setResolver(ProducerResolver resolver) {
        this.resolver = resolver;
        return this;
    }

    public Producer addParam(String key, Object value) {
        this.params.put(key, value);
        return this;
    }
}
