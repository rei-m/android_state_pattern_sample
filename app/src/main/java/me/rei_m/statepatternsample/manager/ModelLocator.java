package me.rei_m.statepatternsample.manager;

import java.util.HashMap;
import java.util.Map;

public class ModelLocator {

    public enum Tag {
        ATND,
    }

    private static Map<Tag, Object> showcase = new HashMap<>();

    private ModelLocator() {
    }

    public static void register(Tag tag, Object object) {
        showcase.put(tag, object);
    }

    public static Object get(Tag tag) {
        return showcase.get(tag);
    }
}
