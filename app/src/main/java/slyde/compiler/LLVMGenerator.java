package slyde.compiler;

import java.util.ArrayList;
import java.util.List;

import slyde.compiler.AST.*;

public class LLVMGenerator {
    private StringBuilder llvmCode;
    private int tempVarCounter;
    private int labelCounter;
    private int strVarCounter;

    private boolean hasRet = false;

    private Indent indent = new Indent("\t");

    public LLVMGenerator() {
        llvmCode = new StringBuilder();
        tempVarCounter = 0;
        labelCounter = 0;
    }

    private String newTempVar() {
        return "%t" + (tempVarCounter++);
    }

    private String newStrVar() {
        return "%strVar" + (strVarCounter++);
    }

    private String newLabel() {
        return "label" + (labelCounter++);
    }

    public void createPreDefinedMethods(){
        llvmCode.append("define void @print(i8** %output){\n\t%loadedOutput = load i8*, i8** %output\n\tcall i32 @puts(i8* %loadedOutput)\n\tret void\n}\n\n\n\n");
    }

    public String generate(ProgramNode program) {
        llvmCode.append("; Generated LLVM IR\n");
        llvmCode.append("declare i32 @puts(i8*)\n");

        createPreDefinedMethods();

        for (ClassNode classNode : program.classes) {
            generateClass(classNode);
        }

        MainNode m = program.main;
        if (m != null){
            generateMain(m);
        }

        return llvmCode.toString();
    }

    private void generateClass(ClassNode classNode) {
        llvmCode.append("; Class: " + classNode.name + "\n");

        //Make Struct
        llvmCode.append("%" + classNode.name + "= type {" );

        List<VarDeclNode> vars = classNode.getFields();

        llvmCode.append(getLLVMType(vars.get(0).type));

        for (VarDeclNode v : vars){

            if(v.name == vars.get(0).name){
                continue;
            }
            llvmCode.append(", " + getLLVMType(v.type));
        }

        llvmCode.append("}\n");

        //Struct creation end


        //Make consructor

        ConstructorNode c = classNode.getConstructor();

        llvmCode.append("\ndefine void @" + classNode.name + "_constructor(%" + classNode.name + "* %this");

        if (c != null){

            for (VarDeclNode n : c.params){
                llvmCode.append(", " + getLLVMType(n.type)  + " %" + n.name);
            }
        }

        llvmCode.append(") nounwind {\n");

        indent.up();

        int index = 0;

        for (VarDeclNode n : vars){

            llvmCode.append(indent.get() + "%" + n.name + " = getelementptr %" + classNode.name + ", %" + classNode.name + "* %this, i32 0, i32 " + index + "\n");
            String[] valueRef = generateExpression(n.value, true);
            llvmCode.append(indent.get() + "store " + valueRef[1] + " " + valueRef[0] + ", " + getLLVMType(n.type) + "* %" + n.name + "\n");
            index++;
        }

        if (c != null){

            generateBlock(c.body);
        }

        llvmCode.append(indent.get() + "ret void\n}\n");

        indent.down();


        //End of making constructor


        //Create methods
        List<MethodNode> methods = classNode.getMethods(); 

        
        for (MethodNode m : methods){
            llvmCode.append("define " + getLLVMType(m.returnType) + " @" + classNode.name + "_" + m.name + "(%" + classNode.name + "* %this");

            for (VarDeclNode n : m.params){
                llvmCode.append(", " + getLLVMType(n.type)  + " %" + n.name);
            }

            llvmCode.append(") nounwind {\n");

            indent.up();

            int index2 = 0;

            for (VarDeclNode n : vars){

                llvmCode.append(indent.get() + "%" + n.name + " = getelementptr %" + classNode.name + ", %" + classNode.name + "* %this, i32 0, i32 " + index2 + "\n");
                index2++;
            }

            generateBlock(m.body);

            if (!hasRet){
                llvmCode.append(indent.get() + "ret void\n}\n");
                hasRet = false;
            } else {
                llvmCode.append("\n}\n");
            }
            indent.down();

        }

        //Finish making methods
        


        
    }

    private void generateMain(MainNode mainNode) {
        llvmCode.append("define void @main(");
        if (mainNode.params != null){
            for (int i = 0; i < mainNode.params.size(); i++) {
                VarDeclNode param = mainNode.params.get(i);
                llvmCode.append(getLLVMType(param.type) + " %" + param.name);
                if (i < mainNode.params.size() - 1) {
                    llvmCode.append(", ");
                }
            }
        }
        llvmCode.append(") {\n");
        indent.up();
        generateBlock(mainNode.body);

        llvmCode.append(indent.get() + "ret void\n");
        indent.down();
        
        llvmCode.append("}\n");
    }



    private void generateBlock(BlockNode block) {
        for (ASTNode stmt : block.statements) {
            if (stmt instanceof VarDeclNode) {
                generateVarDecl((VarDeclNode) stmt);
            } else if (stmt instanceof ReturnNode) {
                generateReturn((ReturnNode) stmt);
            } else if (stmt instanceof IfNode) {
                generateIf((IfNode) stmt);
            } else if (stmt instanceof WhileNode) {
                generateWhile((WhileNode) stmt);
            } else if (stmt instanceof ForNode) {
                generateFor((ForNode) stmt);
            } else if (stmt instanceof AssignmentNode){
                generateAssign((AssignmentNode) stmt);
            } else if (stmt instanceof MethodCallNode){
                generateMethodCall((MethodCallNode) stmt);
            }
        }
    }

    private void generateMethodCall(MethodCallNode node){
        List<String[]> refs = new ArrayList<>();

        for (ASTNode a : node.arguments){
            refs.add(generateExpression(a, true));
        }

        llvmCode.append(indent.get() + "call " + "void" + " @" + node.methodName + "(");

        boolean first = true;
        for (String[] ref : refs){
            llvmCode.append((first ? "" : ",") + ref[1] + " " + ref[0]);
            first = false;
        }

        llvmCode.append(")\n");
    }

    private void generateAssign(AssignmentNode node){
        String[] ref = generateExpression(node.value, true);
        llvmCode.append(indent.get() + "store " + ref[1] + " " + ref[0] + ", " + ref[1] + "* %" + node.name + "\n");
    }

    private void generateVarDecl(VarDeclNode varDecl) {
        String llvmType = getLLVMType(varDecl.type);
        llvmCode.append(indent.get() + "%" + varDecl.name + " = alloca " + llvmType + ", align 1\n");
        if (varDecl.value != null) {
            String[] value = generateExpression(varDecl.value, true);
            llvmCode.append(indent.get() + "store " + value[1] + " " + value[0] + ", " + llvmType + "* %" + varDecl.name + "\n");
        }
    }

    private void generateReturn(ReturnNode returnNode) {
        String returnValue = generateExpression(returnNode.expr);
        llvmCode.append(indent.get() + "ret " + getLLVMType(returnNode.type) + " " + returnValue + "\n");
        hasRet = true;
    }

    private void generateIf(IfNode ifNode) {
        String condition = generateExpression(ifNode.condition);
        String trueLabel = newLabel();
        String falseLabel = newLabel();
        String endLabel = newLabel();
        llvmCode.append(indent.get() + "br i1 " + condition + ", label %" + trueLabel + ", label %" + falseLabel + "\n");
        llvmCode.append(indent.get() + trueLabel + ":\n");
        indent.up();
        generateBlock(ifNode.trueBranch);
        llvmCode.append(indent.get() + "br label %" + endLabel + "\n");
        indent.down();
        llvmCode.append(indent.get() + falseLabel + ":\n");
        indent.up();
        if (ifNode.falseBranch != null) {
            generateBlock(ifNode.falseBranch);
            llvmCode.append(indent.get() + "br label %" + endLabel + "\n");
        } else {
            llvmCode.append(indent.get() + "br label %" + endLabel + "\n");
        }
        indent.down();
        llvmCode.append(indent.get() + endLabel + ":\n");
    }

    private void generateWhile(WhileNode whileNode) {
        String conditionLabel = newLabel();
        String bodyLabel = newLabel();
        String endLabel = newLabel();
        llvmCode.append(indent.get() + conditionLabel + ":\n");
        indent.up();
        String condition = generateExpression(whileNode.condition);
        indent.down();
        llvmCode.append(indent.get() + "br i1 " + condition + ", label %" + bodyLabel + ", label %" + endLabel + "\n");
        llvmCode.append(indent.get() + bodyLabel + ":\n");
        indent.up();
        generateBlock(whileNode.body);
        indent.down();
        llvmCode.append( indent.get() + "br label %" + conditionLabel + "\n");
        llvmCode.append(indent.get() + endLabel + ":\n");
    }

    private void generateFor(ForNode forNode) {
        generateVarDecl(forNode.init);
        generateWhile(new WhileNode(forNode.condition, forNode.body));
        generateExpression(forNode.update);
    }

    private String generateExpression(ASTNode expr) {
        if (expr instanceof NumberNode) {
            return Integer.toString(((NumberNode) expr).value);
        } else if (expr instanceof FloatNode){
            return Float.toString(((FloatNode) expr).value);
        } else if (expr instanceof DoubleNode){
            return Double.toString(((DoubleNode) expr).value);
        }  else if (expr instanceof BinaryOpNode) {
            return generateBinaryOp((BinaryOpNode) expr);
        } else if (expr instanceof StringNode){
            return createString((StringNode) expr);
        } else if (expr instanceof ConditionalOp){
            return generateCondOp((ConditionalOp) expr);
        } else if (expr instanceof BooleanNode){
            if (((BooleanNode) expr).value){
                return "1";
            } else {
                return "0";
            }
        } else if (expr instanceof IdentifierNode){
            return "%" + ((IdentifierNode) expr).name;
        } else if (expr instanceof NewInstanceNode){
            return generateNewInstance((NewInstanceNode) expr)[0];
        }
        return null;
    }


    private String[] generateExpression(ASTNode expr, boolean getType) {
        if (expr instanceof NumberNode) {
            return new String[] {Integer.toString(((NumberNode) expr).value), getLLVMType("int")};
        } else if (expr instanceof FloatNode){
            return new String[] {Float.toString(((FloatNode) expr).value), getLLVMType("float")};
        } else if (expr instanceof DoubleNode){
            return new String[] {Double.toString(((DoubleNode) expr).value), getLLVMType("float")};
        } else if (expr instanceof BinaryOpNode) {
            return new String[] {generateBinaryOp((BinaryOpNode) expr), getLLVMType("int")};
        } else if (expr instanceof StringNode){
            return createString((StringNode) expr, true);
        } else if (expr instanceof ConditionalOp){
            return new String[] {generateCondOp((ConditionalOp) expr), getLLVMType("boolean")};
        } else if (expr instanceof BooleanNode){
            if (((BooleanNode) expr).value){
                return new String[] {"1", getLLVMType("boolean")};
            } else {
                return new String[] {"0", getLLVMType("boolean")};
            }
        } else if (expr instanceof IdentifierNode){
            return new String[] {"%" + ((IdentifierNode) expr).name, getLLVMType(((IdentifierNode) expr).type) + "*"};
        } else if (expr instanceof NewInstanceNode){
            return generateNewInstance((NewInstanceNode) expr);
        }


        return new String[] {null, null};
    }

    private String[] generateNewInstance(NewInstanceNode n){
        String temp = newTempVar();
        llvmCode.append(indent.get() + temp + " = alloca %" + n.name + "\n");
        List<String[]> refs = new ArrayList<>();

        for (ASTNode a : n.args){
            refs.add(generateExpression(a, true));
        }

        llvmCode.append(indent.get() + "call void @" + n.name + "_constructor(%" + n.name + "* " + temp);
        for (String[] ref : refs){
            llvmCode.append(", " + ref[1] + " " + ref[0]);
        }

        llvmCode.append(")\n");

        return new String[]{temp, getLLVMType(n.name) + "*"};
    }

    private String[] createString(StringNode str, boolean getType){
        int len = str.value.length();
        String temp = newTempVar();
        String value = str.value;
        String type = "[" + (len - 1) + " x i8]";

        llvmCode.append(indent.get() + temp + " = alloca " + type + ", align 1\n");

        String ref = newStrVar();

        llvmCode.append(indent.get() + ref + " = getelementptr " + type + ", " + type + "* " + temp + ", i32 0, i32 0\n");
        llvmCode.append(indent.get() + "store i8 " + (int) value.charAt(1) + ", i8* " + ref + "\n");

        for (int i = 2; i < len - 1; i++){
            String newStringPtr = newStrVar();
            llvmCode.append(indent.get() + newStringPtr + " = getelementptr i8, i8* " + ref + ", i32 " + (i-1) + "\n");
            llvmCode.append(indent.get() + "store i8 " + (int) value.charAt(i) + ", i8* " + newStringPtr + "\n");
        }

        String newStringPtr = newStrVar();
        llvmCode.append(indent.get() + newStringPtr + " = getelementptr i8, i8* " + ref + ", i32 " + (len - 2) + "\n");
        llvmCode.append(indent.get() + "store i8 0, i8* " + newStringPtr + "\n");

        return new String[] {ref, "i8*"};

    }

    private String createString(StringNode str){
        int len = str.value.length();
        String temp = newTempVar();
        String value = str.value;
        String type = "[" + (len - 1) + " x i8]";

        llvmCode.append(indent.get() + temp + " = alloca " + type + ", align 1\n");

        String ref = newStrVar();

        llvmCode.append(indent.get() + ref + " = getelementptr " + type + ", " + type + "* " + temp + ", i32 0, i32 0\n");
        llvmCode.append(indent.get() + "store i8 " + (int) value.charAt(1) + ", i8* " + ref + "\n");

        for (int i = 2; i < len - 1; i++){
            String newStringPtr = newStrVar();
            llvmCode.append(indent.get() + newStringPtr + " = getelementptr i8, i8* " + ref + ", i32 " + (i-1) + "\n");
            llvmCode.append(indent.get() + "store i8 " + (int) value.charAt(i) + ", i8* " + newStringPtr + "\n");
        }

        String newStringPtr = newStrVar();
        llvmCode.append(indent.get() + newStringPtr + " = getelementptr i8, i8* " + ref + ", i32 " + (len - 2) + "\n");
        llvmCode.append(indent.get() + "store i8 0, i8* " + newStringPtr + "\n");

        return ref;

    }

    private String generateCondOp(ConditionalOp conditionalOp){
        String[] left = generateExpression(conditionalOp.left, true);
        String right = generateExpression(conditionalOp.right);
        String temp = newTempVar();
        String op = null;
        String t = left[1] == "double" ? "f" : "i";

        switch (conditionalOp.operator) {
            case "==":
                op = "eq";
                break;
            case "!=":
                op = "ne";
                break;
            case "<":
                op = "lt";
                break;
            case "<=":
                op = "le";
                break;
            case ">":
                op = "gt";
                break;
            case ">=":
                op = "ge";
                break;
            default:
                break;
        }

        if(op == null){
            System.err.println("Error: Invalid operation: " + conditionalOp.operator);
        }

        llvmCode.append(indent.get() + temp + " = " + t + "cmp " + op + " " + left[1] + " " + left[0] + ", " + right + "\n");


        return temp;
    }

    private String generateBinaryOp(BinaryOpNode binOp) {
        String[] left = generateExpression(binOp.left, true);
        String right = generateExpression(binOp.right);
        String temp = newTempVar();
        String op = binOp.operator.equals("+") ? "add" : "sub";

        switch (binOp.operator) {
            case "*":
                op = "mul";
                break;
        
            case "/":
                op = "sdiv";
                if(left[1] == "double"){
                    op = "fdiv";
                }
            default:
                break;
        }
        llvmCode.append(indent.get() + temp + " = " + op + " " + left[1] + " " + left[0] + ", " + right + "\n");
        return temp;
    }

    private String getLLVMType(String type) {
        return switch (type) {
            case "int" -> "i32";
            case "boolean" -> "i1";
            case "void" -> "void";
            case "double" -> "double";
            case "float" -> "float";
            case "String" -> "i8*";
            default -> "%" + type;
        };
    }
}
