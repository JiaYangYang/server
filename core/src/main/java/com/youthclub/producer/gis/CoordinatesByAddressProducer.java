package com.youthclub.producer.gis;

import com.youthclub.cache.TreeCaches;
import com.youthclub.producer.Producer;
import com.youthclub.producer.ProducerResolver;
import com.youthclub.view.AddressView;
import com.youthclub.view.CoordinatesView;
import gnu.trove.map.TMap;
import org.infinispan.tree.Fqn;
import org.infinispan.tree.TreeCache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by frank on 13-12-14.
 */
public class CoordinatesByAddressProducer implements ProducerResolver<AddressView, CoordinatesView> {

    private static final String CACHE_NAME = CoordinatesByAddressProducer.class.getSimpleName();

    private final TreeCache<Fqn, Future<CoordinatesView>> cache = TreeCaches.GIS_ADDRESS_CACHE.getCache();

    private final String region = TreeCaches.GIS_ADDRESS_CACHE.getRegion() + "/" + CACHE_NAME;

    public CoordinatesView produce(final AddressView that) throws Exception {
        final Producer<AddressView, CoordinatesView> producer = new Producer<>()
                .setK(that)
                .setCache(cache)
                .setRegion(region)
                .setResolver(this);
        return producer.produce();
    }

    public CoordinatesView resolve(final AddressView addressView, final TMap<String, Object> params) {
        final CoordinatesView coordinatesView = new CoordinatesView();
        final StringBuilder sb = new StringBuilder();
        final String spaceEncode = "+";
        try {
            final URL url = new URL(GISConfiguration.GEOCODE + "&address=" + addressView.toString().replaceAll(" ", spaceEncode));
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inLine;
            while ((inLine = in.readLine()) != null) {
                sb.append(inLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Pattern pattern = Pattern.compile("<location>.*?</location>");
        final Matcher matcher = pattern.matcher(sb.toString());
        if (matcher.find()) {
            final String loc = matcher.group();
            final Pattern latPattern = Pattern.compile("<lat>.*?</lat>");
            final Matcher latMatcher = latPattern.matcher(loc);
            if (latMatcher.find()) {
                final String data = latMatcher.group();
                final String lat = data.replaceAll("</*lat>", "");
                coordinatesView.setLatitude(Float.parseFloat(lat));
            }

            final Pattern lngPattern = Pattern.compile("<lng>.*?</lng>");
            final Matcher lngMatcher = lngPattern.matcher(loc);
            if (lngMatcher.find()) {
                final String data = lngMatcher.group();
                final String lat = data.replaceAll("</*lng>", "");
                coordinatesView.setLongitude(Float.parseFloat(lat));
            }
        }

        return coordinatesView;
    }
}
