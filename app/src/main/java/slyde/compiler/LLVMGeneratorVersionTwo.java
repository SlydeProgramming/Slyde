package slyde.compiler;

import java.util.ArrayList;
import java.util.List;

import slyde.compiler.AST.*;

public class LLVMGeneratorVersionTwo {

    private static Generator codemanager = new Generator();

    private static void createPreDefinedMethods() {
        codemanager.appendHead("declare i32 @puts(i8*)\n");
    }

    private static void generateClasses(List<ClassNode> nodes) {

        for (ClassNode clas : nodes) {
            codemanager.addCommentHead(clas.parent + "_" + clas.name);
            codemanager.appendHead("% " + clas.parent + "_" + clas.name + " = type {");
            MetaDataString<ClassNode> context = new MetaDataString<ClassNode>().setObj(clas)
                    .setStringManager(codemanager);

            generateNodesArray(clas.body, context);
        }

    }

    private static <T> void generateNodesArray(List<ASTNode> nodes, MetaDataString<T> context) {
        for (ASTNode node : nodes) {
            node.explode(context);
        }
    }

    private static void generateMainMethod(MainNode main) {
        codemanager.addMethodHeader("main");

        MetaDataString<MainNode> context = new MetaDataString<MainNode>().setObj(main)
                .setStringManager(codemanager);

        generateNodesArray(main.body.statements, context);

    }

    public static String generate(ProgramNode src) {
        codemanager.addCommentHead("Generated LLVM");
        createPreDefinedMethods();

        generateClasses(src.classes);

        generateMainMethod(src.main);

        return codemanager.toString();
    }
}

class Generator {
    private static Indent indent = new Indent("\t");
    private static StringBuilder llvmHead = new StringBuilder();
    private static StringBuilder llvm = new StringBuilder();
    private static StringBuilder llvmEnd = new StringBuilder();
    private static List<String> definedStrings = new ArrayList<>();

    public void appendHead(String str) {
        llvmHead.append(str);
    }

    public void appendEnd(String str) {
        llvmEnd.append(str);
    }

    public void append(String str) {
        llvm.append(str);
    }

    public String end() {
        return llvmHead.toString() + "\n" + llvm.toString() + "\n" + llvmEnd.toString();
    }

    public String up() {
        return indent.up();
    }

    public String get() {
        return indent.get();
    }

    public String down() {
        return indent.down();
    }

    // == Methodheader overloads ==

    public void addMethodHeader(String name, String retType, List<String> paramsList, boolean isSafe) {
        append("define " + retType + " @" + name + "(");
        if (!paramsList.isEmpty()) {
            append(paramsList.get(0));
            for (int i = 1; i < paramsList.size(); i++) {
                append(", " + paramsList.get(i));
            }
        }
        append(") " + (isSafe ? "nounwind" : "") + "  {");
        up();
    }

    public void addMethodHeader(String name, String retType, List<String> paramsList) {
        addMethodHeader(name, retType, paramsList, false);
    }

    public void addMethodHeader(String name, String retType) {
        addMethodHeader(name, retType, new ArrayList<>());
    }

    public void addMethodHeader(String name) {
        addMethodHeader(name, "void");
    }

    // =============================

    public void addCommentHead(String str) {
        appendHead(get() + ";  " + str + "\n");
    }

    public void addComment(String str) {
        append(get() + ";  " + str + "\n");
    }

    public void addCommentEnd(String str) {
        appendEnd(get() + ";  " + str + "\n");
    }

    @Override
    public String toString() {
        return indent.toString();
    }

}

class MetaDataString<T> {
    String value;
    String metaData;
    boolean booleanValue;
    Generator stringManager;
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

    public MetaDataString<T> setStringManager(Generator stringManager) {
        this.stringManager = stringManager;
        return this;
    }

    public MetaDataString<T> setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return value;
    }
}