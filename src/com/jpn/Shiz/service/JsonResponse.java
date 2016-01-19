package com.jpn.Shiz.service;

import java.util.ArrayList;

/**
 * Created by saeed on 1/16/16.
 */
public class JsonResponse {

    private static ArrayList<String> tags = new ArrayList<String>();
    private static ArrayList<String> texts = new ArrayList<String>();

    public static String getText(String tag) {
        return texts.get(tags.indexOf(tag));
    }

    public static void setText(String text, String tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).equals(tag)) {
                tags.remove(i);
                texts.remove(i);
            }
        }
        tags.add(tag);
        texts.add(text);
    }
}
