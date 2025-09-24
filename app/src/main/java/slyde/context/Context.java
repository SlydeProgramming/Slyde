package slyde.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import slyde.structure.AST.MainNode;
import slyde.structure.AST.MethodNode;
import slyde.utils.ErrorHandler;

public class Context<T> {

    public static List<String> createdStrings = new ArrayList<>();
    private MetaData metaData = new MetaData();
    private T obj;
    private static Map<String, String> varTypeReg = new HashMap<>();
    private static Map<String, List<MethodNode>> classMethodReg = new HashMap<>();

    private static Map<String, List<String>> globalRefrances = new HashMap<>();

    public Context<T> setHandleProtocol(HandleProtocol hp) {
        metaData.hp = hp;
        return this;
    }

    public Context<T> addContextName(String name) {
        metaData.contextNames.add(name);
        return this;
    }

    public void debug() {
        varTypeReg.forEach((k, v) -> {
            System.err.println("CtxName: " + k + " , RegType: " + v);
        });
        System.err.println(getContextName());
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

    public String[] findRegisteredType(String rawName) {
        String type = varTypeReg.get(getContextName() + rawName);
        String ctx = getContextName();
        int index = 1;
        while (type == null && index < metaData.contextNames.size()) {
            ctx = getContextName(index, true);
            type = varTypeReg.get(ctx + rawName);
            index++;
        }
        return new String[] { type, ctx };
    }

    public List<MethodNode> findRegisterMethods(String type, String methodName) {
        List<MethodNode> methodRegistry = classMethodReg.get(type);
        if (methodRegistry == null && obj instanceof MainNode n) {
            ErrorHandler.error("method " + methodName + " dosent exist for type " + type + "", n.line, n.column);
        } else if (methodRegistry == null) {
            try {
                throw new RuntimeException("");
            } catch (Exception e) {
                ErrorHandler.error("method " + methodName + " dosent exist for type " + type + "", e);
            }
        }
        List<MethodNode> registeredMethods = new ArrayList<>(methodRegistry);

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

    public String resolveContext(String resolveVar) {
        String ctx = getContextName();
        String test = findReturnedName(ctx + resolveVar);
        String[] rT = findRegisteredType(resolveVar);
        int index = 1;
        while (test == null && index < metaData.contextNames.size()) {
            ctx = getContextName(index, true);
            test = findReturnedName(ctx + resolveVar);
            index++;
        }

        if (test != null) {

            return ctx;
        } else if (rT[0] != null) {
            return rT[1];
        }
        return null;

    }

    public String getContextName(int subtractCount, boolean fullName) {
        if (fullName) {
            String res = "";

            List<String> add = new ArrayList<>();

            for (int i = 0; i < metaData.contextNames.size() - subtractCount; i++) {
                add.add(metaData.contextNames.get(i));
            }

            for (String name : add) {
                res += name + "_";
            }
            return res;
        } else {
            return metaData.contextNames.get(metaData.contextNames.size() - subtractCount);
        }

    }

    public String getContextName(int index) {
        return metaData.contextNames.get(index);
    }

    public String popContext() {
        return metaData.contextNames.removeLast();
    }

    public String findReturnedName(String requestName) {
        List<Integer> nameStorage = metaData.returnIndex.get(requestName);
        if (nameStorage == null) {
            return null;
        }
        int index = nameStorage.get(0);
        return metaData.returnValues.get(index);
    }

    public boolean registeredInContext(String varName) {
        List<String> rVars = globalRefrances.get(getContextName());
        if (rVars == null) {
            return false;
        }

        for (String s : rVars) {
            if (s.equals(varName)) {
                return true;
            }
        }
        return false;
    }

    public void registerGlobalInCtx(String varName) {
        List<String> rVars = globalRefrances.get(getContextName());
        if (rVars == null) {
            rVars = new ArrayList<>();
            globalRefrances.put(getContextName(), rVars);
        }

        rVars.add(varName);
    }

    public String findReturnedType(String requestName) {
        List<Integer> nameStorage = metaData.returnIndex.get(requestName);
        if (nameStorage == null) {
            return null;
        }
        int index = nameStorage.get(1);
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
