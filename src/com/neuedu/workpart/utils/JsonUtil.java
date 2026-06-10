package com.neuedu.workpart.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JSON工具类，提供全局共享的Jackson ObjectMapper实例。
 * <p>ObjectMapper是线程安全的且构造开销较大，应复用同一实例。</p>
 */
public class JsonUtil {
    /** 全局共享的ObjectMapper单例 */
    public static final ObjectMapper INSTANCE;

    static {
        INSTANCE = new ObjectMapper();
        INSTANCE.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private JsonUtil() {}
}
