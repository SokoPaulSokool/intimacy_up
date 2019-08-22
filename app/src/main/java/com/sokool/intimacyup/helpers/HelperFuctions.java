package com.sokool.intimacyup.helpers;

import android.util.Log;

public class HelperFuctions {
    public static String capitalize(String string){
        if(!string.equals("")) {
            String cap = string.substring(0, 1).toUpperCase();
            String low = string.substring(1).toLowerCase();

            return cap + low;
        }else {
            return string;
        }

    }
}
