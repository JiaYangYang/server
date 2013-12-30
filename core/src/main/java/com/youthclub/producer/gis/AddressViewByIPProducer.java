package com.youthclub.producer.gis;

import gnu.trove.map.TMap;
import com.youthclub.producer.Producer;
import com.youthclub.producer.ProducerResolver;
import com.youthclub.cache.TreeCaches;
import org.apache.commons.lang3.StringEscapeUtils;
import org.infinispan.tree.Fqn;
import org.infinispan.tree.TreeCache;
import org.jboss.logging.Logger;
import com.youthclub.view.AddressView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by frank on 13-12-14.
 */
public class AddressViewByIPProducer implements ProducerResolver<String, AddressView> {

    private static final Logger log = Logger.getLogger(AddressViewByIPProducer.class.getCanonicalName());

    private static final String CACHE_NAME = AddressViewByIPProducer.class.getSimpleName();

    private final TreeCache<Fqn, AddressView> cache = TreeCaches.GIS_ADDRESS_CACHE.getCache();

    private final String region = TreeCaches.GIS_ADDRESS_CACHE.getRegion() + "/" + CACHE_NAME;

    public AddressView produce(final String that) throws Exception {
        final Producer<String, AddressView> producer = new Producer<>()
                .setK(that)
                .setCache(cache)
                .setRegion(region)
                .setResolver(this);
        return producer.produce();
    }

    public AddressView resolve(final String ip, final TMap<String, Object> params) {
        final AddressView addressView = new AddressView();
        final StringBuilder sb = new StringBuilder();

        try {
            final URL url = new URL(GISConfiguration.IP_LOOK_UP + ip);
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inLine;
            while ((inLine = in.readLine()) != null) {
                sb.append(inLine);
            }
        } catch (IOException ex) {
            log.log(Logger.Level.ERROR, ex.getMessage(), ex);
        }

        final String[] fields = sb.toString().split(",");
        for (String f : fields) {
            if (f.startsWith("\"country") || f.startsWith("\"province") || f.startsWith("\"city")) {
                final String[] map = f.split(":");
                if (map.length == 2) {
                    final String ret = StringEscapeUtils.unescapeJava(map[1]).replaceAll("\"", "");
                    if (f.startsWith("\"country")) {
                        addressView.setCountry(ret);
                    } else if (f.startsWith("\"province")) {
                        addressView.setState(ret);
                    } else {
                        addressView.setCity(ret);
                    }
                }
            }
        }
        return addressView;
    }
}
