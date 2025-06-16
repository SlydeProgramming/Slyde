package slyde.utils;

import slyde.generation.MultiPartTextGenerator;

public class MetaDataString<T> {
    String value;
    String metaData;
    boolean booleanValue;
    MultiPartTextGenerator stringManager;
    T obj;

    public MetaDataString<T> setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
        return this;
    }

    public MetaDataString<T> setMetaData(String metaData) {
        this.metaData = metaData;
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

    @Override
    public String toString() {
        return value;
    }
}
