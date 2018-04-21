package org.amd.aqua.util;

/**
 * Created by Akira on 2018/04/16.
 */

public class StringUtil {
    public static final boolean isEmpty(String text) {
        if (text == null || text.length() == 0) {
            return true;
        }
        return false;
    }

    public static final boolean equals( String text1, String text2){
        if( text1 == null || text2 == null){
            return false;
        }
        return text1.equals( text2);
    }
}
