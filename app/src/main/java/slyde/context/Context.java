package slyde.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import slyde.structure.AST.MethodNode;

public class Context<T> {

    public static List<String> createdStrings = new ArrayList<>();
    private MetaData metaData = new MetaData();
    private T obj;
    private static Map<String, String> varTypeReg = new HashMap<>();
    private static Map<String, List<MethodNode>> classMethodReg = new HashMap<>();

    public Context<T> setHandleProtocol(HandleProtocol hp) {
        metaData.hp = hp;
        return this;
    }

    public Context<T> addContextName(String name) {
        metaData.contextNames.add(name);
        return this;
    }

    public Context<T> requestName(String name) {
        metaData.requestedName = name;
        return this;
    }

    public Context<T> setObj(T obj) {
        this.obj = obj;
        return this;
    }

    public Context<T> setReturnValues(String name, String type) {
        List<Integer> indexes = new ArrayList<>();
        indexes.add(metaData.returnValues.size());
        metaData.returnValues.add(name);
        indexes.add(metaData.returnValues.size());
        metaData.returnValues.add(type);
        metaData.returnIndex.put(getRequestName(), indexes);
        return this;
    }

    public T getObjectValue() {
        return obj;
    }

    public static void regiserVar(String contextName, String type) {
        varTypeReg.put(contextName, type);
    }

    public static void registerMethod(String type, MethodNode method) {
        List<MethodNode> registeredMethods = classMethodReg.get(type);
        if (registeredMethods == null) {
            registeredMethods = new ArrayList<>();
        }
        registeredMethods.add(method);
        classMethodReg.put(type, registeredMethods);
    }

    public String findRegisteredType(String rawName) {
        return varTypeReg.get(getContextName() + rawName);
    }

    public List<MethodNode> findRegisterMethods(String type, String methodName) {
        List<MethodNode> registeredMethods = new ArrayList<>(classMethodReg.get(type));

        registeredMethods.removeIf((n) -> {
            return !n.name.equals(methodName);
        });

        return registeredMethods;

    }

    public String getRequestName() {
        return metaData.requestedName;
    }

    public String getContextName() {
        String res = "";
        for (String name : metaData.contextNames) {
            res += name + "_";
        }
        return res;
    }

    public String getContextName(int index) {
        return metaData.contextNames.get(index);
    }

    public String popContext() {
        return metaData.contextNames.removeLast();
    }

    public String findReturnedName(String requestName) {
        int index = metaData.returnIndex.get(requestName).get(0);
        return metaData.returnValues.get(index);
    }

    public String findReturnedType(String requestName) {
        int index = metaData.returnIndex.get(requestName).get(1);
        return metaData.returnValues.get(index);
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public HandleProtocol getHandleProtocol() {
        return metaData.hp;
    }

    public boolean is(HandleProtocol hp) {
        return getHandleProtocol().equals(hp);
    }

}
