package com.mydomain.silverpay.helper;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class FindLocalPathFromUrl {

    public static String findPath(String path) {

//        String[] paths = path.split("/");

        String[] array = path.replace("https://", "").replace("http://", "")
                .split("/");

        array = Arrays.copyOf(array,1);
        array = Arrays.copyOf(array, 0);


        return StringUtils.join(array, "/");


    }
}
