package com.youthclub.web;

import javax.servlet.http.Cookie;

/**
 * @author Frank <frank@baileyroberts.com.au>
 */
public class CookieUtil {

    public static String getValue(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
