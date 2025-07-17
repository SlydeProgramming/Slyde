package slyde.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import slyde.generation.MultiPartTextGenerator;

public class MetaDataString<T> {
    String value;
    Map<String, List<Associate>> metaData;
    boolean booleanValue;
    MultiPartTextGenerator stringManager;
    T obj;

    public MetaDataString<T> setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
        return this;
    }

    public MetaDataString<T> setInfo(Map<String, List<Associate>> additionInfo) {
        this.metaData = additionInfo;
        return this;
    }

    public MetaDataString<T> addInfo(String key, List<Associate> value) {
        if (metaData != null) {
            metaData.put(key, value);
        } else {
            metaData = new HashMap<String, List<Associate>>();
            metaData.put(key, value);
        }
        return this;
    }

    public MetaDataString<T> addInfo(String key, Associate value) {
        if (metaData != null) {
            List<Associate> val = metaData.get(key);
            if (val != null) {
                val.add(value);
            } else {
                val = new ArrayList<Associate>();
                val.add(value);
                metaData.put(key, val);
            }
        } else {
            metaData = new HashMap<String, List<Associate>>();
            List<Associate> val = new ArrayList<Associate>();
            val.add(value);
            metaData.put(key, val);
        }
        return this;
    }

    public MetaDataString<T> setObj(T obj) {
        this.obj = obj;
        return this;
    }

    public MetaDataString<T> setStringManager(MultiPartTextGenerator stringManager) {
        this.stringManager = stringManager;
        return this;
    }

    public MetaDataString<T> setValue(String value) {
        this.value = value;
        return this;
    }

    public T getObjectValue() {
        return obj;
    }

    public Map<String, List<Associate>> getMetaData() {
        return metaData;
    }

    public MultiPartTextGenerator getStringManager() {
        return stringManager;
    }

    @Override
    public String toString() {
        return value;
    }

    public static class Associate {

        private Map<String, Object> map;
        private String value;

        public Associate(String val) {
            this.value = val;
        }

        public Associate(Map<String, Object> map) {
            this.map = map;
        }

        public Map<String, Object> getMap() {
            return map;
        }

        public String getString() {
            return value;
        }

        public String getType() {
            if (map != null) {
                return "map";
            } else {
                return "str";
            }
        }

    }

}
