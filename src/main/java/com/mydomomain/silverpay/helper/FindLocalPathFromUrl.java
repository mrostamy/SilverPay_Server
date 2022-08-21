package com.mydomomain.silverpay.helper;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
