package com.youthclub.support;

public class PathUtil {

    public static String jsonNameFromGetterName(final String methodName) {
        final String attribute;
        if (methodName.startsWith("get")) {
            attribute = methodName.substring(3);
        } else if (methodName.startsWith("is")) {
            attribute = methodName.substring(2);
        } else {
            attribute = methodName;
        }

        return underscoresFromCamelCase(attribute);
    }

    public static String isGetterNameFromJsonName(final String jsonName) {
        return new StringBuilder("is").append(nameFromJsonName(jsonName)).toString();
    }

    public static String getterNameFromJsonName(final String jsonName) {
        return new StringBuilder("get").append(nameFromJsonName(jsonName)).toString();
    }

    public static String setterNameFromJsonName(final String jsonName) {
        return new StringBuilder("set").append(nameFromJsonName(jsonName)).toString();
    }

    private static String nameFromJsonName(final String jsonName) {
        final StringBuilder builder = new StringBuilder();
        builder.append(jsonName.substring(0, 1).toUpperCase());

        for (int i = 1; i < jsonName.length(); i++) {
            if (Character.compare(jsonName.charAt(i), '_') == 0) {
                i++;
                builder.append(jsonName.substring(i, i + 1).toUpperCase());
            } else {
                builder.append(jsonName.charAt(i));
            }
        }
        return builder.toString();
    }

    private static String underscoresFromCamelCase(final String camelCase) {
        final String noFirstCapital = camelCase.substring(0, 1).toLowerCase() + camelCase.substring(1);

        final StringBuilder builder = new StringBuilder();

        boolean lastWasDigit = false;
        for (int i = 0; i < noFirstCapital.length(); i++) {
            final char c = noFirstCapital.charAt(i);
            final boolean digit = Character.isDigit(c);
            if (Character.isUpperCase(c) || (digit && !lastWasDigit)) {
                builder.append('_').append(noFirstCapital.substring(i, i + 1).toLowerCase());
            } else {
                builder.append(noFirstCapital.charAt(i));
            }
            lastWasDigit = digit;
        }

        return builder.toString();
    }
}