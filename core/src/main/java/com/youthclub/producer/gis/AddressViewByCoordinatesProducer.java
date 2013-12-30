package com.youthclub.producer.gis;

import com.youthclub.cache.Keys;
import gnu.trove.map.TMap;
import com.youthclub.producer.Producer;
import com.youthclub.producer.ProducerResolver;
import com.youthclub.cache.TreeCaches;
import org.infinispan.tree.Fqn;
import org.infinispan.tree.TreeCache;
import org.jboss.logging.Logger;
import com.youthclub.view.AddressView;
import com.youthclub.view.CoordinatesView;

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
public class AddressViewByCoordinatesProducer implements ProducerResolver<CoordinatesView, AddressView> {

    private static final Logger log = Logger.getLogger(AddressViewByCoordinatesProducer.class.getCanonicalName());

    private static final String CACHE_NAME = AddressViewByCoordinatesProducer.class.getSimpleName();

    private final TreeCache<Fqn, Future<AddressView>> cache = TreeCaches.GIS_ADDRESS_CACHE.getCache();

    private final String region = TreeCaches.GIS_ADDRESS_CACHE.getRegion() + "/" + CACHE_NAME;

    public AddressView produce(final CoordinatesView that, final String language) throws Exception {
        final Producer<CoordinatesView, AddressView> producer = new Producer<>()
                .setK(that)
                .setCache(cache)
                .setRegion(region)
                .setKey(Keys.from(that))
                .setResolver(this)
                .addParam("language", language);
        return producer.produce();
    }

    public AddressView resolve(final CoordinatesView coordinatesView, final TMap<String, Object> params) {
        final AddressView addressView = new AddressView();
        final StringBuilder sb = new StringBuilder();

        String language = null;
        if (params.containsKey("language")) {
            language = params.get("language").toString();
        }
        final String urlStr = language == null ? "" : "&language=" + language;
        try {
            final URL url = new URL(GISConfiguration.GEOCODE
                    + urlStr
                    + "&latlng=" + coordinatesView.getLatitude() + "," + coordinatesView.getLongitude());
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inLine;
            while ((inLine = in.readLine()) != null) {
                sb.append(inLine);
            }
        } catch (IOException ex) {
            log.log(Logger.Level.ERROR, ex.getMessage(), ex);
        }

        final String response = sb.toString();
        final Pattern pattern = Pattern.compile("<result>.*?</result>");
        final Pattern addressPattern = Pattern.compile("<formatted_address>.*?</formatted_address>");
        final Pattern subPattern = Pattern.compile("<address_component>.*?</address_component>");
        final Pattern namePattern = Pattern.compile("<long_name>.*?</long_name>");
        final Matcher matcher = pattern.matcher(response);
        String data = null;
        if (matcher.find()) {
            final String group = matcher.group();
            final Matcher addressMatcher = addressPattern.matcher(group);
            if (addressMatcher.find()) {
                data = addressMatcher.group().replaceAll("</*formatted_address>", "");
                addressView.setFormattedAddress(data);
            }

            final Matcher subMatcher = subPattern.matcher(group);
            while (subMatcher.find()) {
                final String node = subMatcher.group();
                final Matcher nameMatcher = namePattern.matcher(node);
                if (nameMatcher.find()) {
                    data = nameMatcher.group().replaceAll("</*long_name>", "");
                }
                if (node.contains("<type>locality</type>")) {
                    addressView.setCity(data);
                } else if (node.contains("<type>administrative_area_level_1</type>")) {
                    addressView.setState(data);
                } else if (node.contains("<type>country</type>")) {
                    addressView.setCountry(data);
                } else if (node.contains("<type>postal_code</type>")) {
                    addressView.setPostCode(data);
                }
            }
        }

        return addressView;
    }
}
