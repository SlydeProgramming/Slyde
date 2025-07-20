package slyde.generation;

import java.util.ArrayList;
import java.util.List;

import slyde.structure.AST.VarDeclNode;
import slyde.utils.Indent;

public class MultiPartTextGenerator {
    private Indent indent = new Indent("\t");
    private StringBuilder textHead = new StringBuilder();
    private StringBuilder text = new StringBuilder();
    private StringBuilder textEnd = new StringBuilder();
    private int loaded = 0;

    public void appendHead(String str) {
        textHead.append(str);
    }

    public void appendEnd(String str) {
        textEnd.append(str);
    }

    public void append(String str) {
        text.append(str);
    }

    public String end() {
        return textHead.toString() + "\n" + text.toString() + "\n" + textEnd.toString();
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

    public StringBuilder getText() {
        return text;
    }

    public StringBuilder getTextEnd() {
        return textEnd;
    }

    public StringBuilder getTextHead() {
        return textHead;
    }

    public void removeLastCharHead() {
        textHead.deleteCharAt(textHead.toString().length() - 1);
    }

    public void removeLastChar() {
        text.deleteCharAt(text.toString().length() - 1);
    }

    public void removeLastCharEnd() {
        textEnd.deleteCharAt(textEnd.toString().length() - 1);

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
        append(") " + (isSafe ? "nounwind" : "") + "  {\n");
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

    public void methodBodySetup(List<VarDeclNode> fields, String classIdentifier) {
        if (fields != null) {
            int fieldIndex = 0;
            for (VarDeclNode field : fields) {
                String llvmFieldType = MultiPartTextGenerator.getLLVMType(field.type);
                String fieldName = field.name;

                // Get pointer to the field inside the struct
                String ptrName = "%ptr_" + fieldName;
                append(
                        String.format("  %s = getelementptr inbounds %%%s, %%%s* %%this, i32 0, i32 %d\n",
                                ptrName, classIdentifier, classIdentifier, fieldIndex));

                // Load the field value
                String loadedName = "%val_" + fieldName;
                append(String.format("  %s = load %s, %s* %s\n",
                        loadedName, llvmFieldType, llvmFieldType, ptrName));

                fieldIndex++;
            }
        }
    }

    public String load(String ctxName, String requestType, String ptr) {
        append(get() + "%temp_" + loaded + " = load " + requestType + ", " + requestType + "* " + ptr + "\n");

        return "%temp_" + loaded++;
    }

    public void createLabel(int id) {
        append(get() + "label" + id + ":\n");
    }

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

    public static String getLLVMType(String type) {
        return switch (type) {
            case "int" -> "i32";
            case "boolean" -> "i1";
            case "void" -> "void";
            case "double" -> "double";
            case "String" -> "i8*";
            default -> "%" + type;
        };
    }

    public static String getSlydeType(String type) {
        return switch (type.replace("*", "").replace("%", "")) {
            case "i32" -> "int";
            case "i1" -> "boolean";
            case "void" -> "void";
            case "double" -> "double";
            case "i8*" -> "String";
            default -> type;
        };
    }

}
