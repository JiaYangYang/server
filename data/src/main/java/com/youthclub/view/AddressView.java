package com.youthclub.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
public class AddressView extends ViewBase {
    private String country;
    private String state;
    private String city;
    private String postCode;
    private String formattedAddress;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    @Override
    public String toString() {
        final List<String> list = new ArrayList<>();
        if (country != null) {
            list.add(country);
        }
        if (state != null) {
            list.add(state);
        }
        if (city != null) {
            list.add(city);
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AddressView) {
            AddressView that = (AddressView) obj;
            if (formattedAddress != null) {
                return formattedAddress.equals(that.formattedAddress) && toString().equals(that.toString());
            }
            if (that.formattedAddress != null) {
                return that.formattedAddress.equals(formattedAddress) && toString().equals(that.toString());
            }
            return toString().equals(that.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

