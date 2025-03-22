package slyde.compiler;

import java.util.ArrayList;
import java.util.List;

public class LLVMGenerator {
    private StringBuilder llvmCode;
    private int tempVarCounter;
    private int labelCounter;

    private boolean hasRet = false;

    public LLVMGenerator() {
        llvmCode = new StringBuilder();
        tempVarCounter = 0;
        labelCounter = 0;
    }

    private String newTempVar() {
        return "%t" + (tempVarCounter++);
    }

    private String newLabel() {
        return "label" + (labelCounter++);
    }

    public String generate(AST.ProgramNode program) {
        llvmCode.append("; Generated LLVM IR\n");
        for (AST.ClassNode classNode : program.classes) {
            generateClass(classNode);
        }
        return llvmCode.toString();
    }

    private void generateClass(AST.ClassNode classNode) {
        llvmCode.append("; Class: " + classNode.name + "\n");
        llvmCode.append("%" + classNode.name + "= type {" );

        List<AST.VarDeclNode> vars = classNode.getFields();

        llvmCode.append(getLLVMType(vars.get(0).type));

        for (AST.VarDeclNode v : vars){

            if(v.name == vars.get(0).name){
                continue;
            }
            llvmCode.append(", " + getLLVMType(v.type));
        }

        llvmCode.append("}\n");

        AST.ConstructorNode c = classNode.getConstructor();

        llvmCode.append("\ndefine void @" + classNode.name + "_constructor(%" + classNode.name + "* %this");

        if (c != null){

            for (AST.VarDeclNode n : c.params){
                llvmCode.append(", " + getLLVMType(n.type)  + " %" + n.name);
            }
        }

        llvmCode.append(") nounwind {\n");


        if (c != null){

            for (AST.VarDeclNode n : c.params){
                if (n.value != null) {
                    String ref = generateExpression(n.value);
                    llvmCode.append("store " + getLLVMType(n.type) + " %" + ref +  ", %" + n.name);
                }
            }
        }

        int index = 0;

        for (AST.VarDeclNode n : vars){

            llvmCode.append("\t%" + n.name + " = getelementptr %" + classNode.name + ", %" + classNode.name + "* %this, i32 0, i32 " + index + "\n");
            String valueRef = generateExpression(n.value);
            llvmCode.append("\tstore " + getLLVMType(n.type) + " " + valueRef + ", " + getLLVMType(n.type) + "* %" + n.name + "\n");
            index++;
        }

        if (c != null){

            generateBlock(c.body);
        }

        llvmCode.append("\tret void\n}\n");

        List<AST.MethodNode> methods = classNode.getMethods(); 

        
        for (AST.MethodNode m : methods){
            llvmCode.append("define " + getLLVMType(m.returnType) + " @" + classNode.name + "_" + m.name + "(%" + classNode.name + "* %this");

            for (AST.VarDeclNode n : m.params){
                llvmCode.append(", " + getLLVMType(n.type)  + " %" + n.name);
            }

            llvmCode.append(") nounwind {\n");

            for (AST.VarDeclNode n : m.params){
                if (n.value != null) {
                    String ref = generateExpression(n.value);
                    llvmCode.append("store " + getLLVMType(n.type) + " %" + ref +  ", %" + n.name);
                }
            }

            int index2 = 0;

            for (AST.VarDeclNode n : vars){

                llvmCode.append("\t%" + n.name + " = getelementptr %" + classNode.name + ", %" + classNode.name + "* %this, i32 0, i32 " + index2 + "\n");
                String valueRef = generateExpression(n.value);
                llvmCode.append("\tstore " + getLLVMType(n.type) + " " + valueRef + ", " + getLLVMType(n.type) + "* %" + n.name + "\n");
                index2++;
            }

            generateBlock(m.body);

            if (!hasRet){
                llvmCode.append("\tret void\n}\n");
            } else {
                llvmCode.append("\n}\n");
            }

        }
        


        AST.MainNode m = classNode.getMain();
        if (m != null){
            generateMain(m);
        }


        
    }

    private void generateMain(AST.MainNode mainNode) {
        llvmCode.append("define void @main(");
        if (mainNode.params != null){
            for (int i = 0; i < mainNode.params.size(); i++) {
                AST.VarDeclNode param = mainNode.params.get(i);
                llvmCode.append(getLLVMType(param.type) + " %" + param.name);
                if (i < mainNode.params.size() - 1) {
                    llvmCode.append(", ");
                }
            }
        }
        llvmCode.append(") {\n");
        generateBlock(mainNode.body);

        llvmCode.append("\tret void\n");
        
        llvmCode.append("}\n");
    }

    private void generateMethod(AST.MethodNode method) {
        String returnType = getLLVMType(method.returnType);
        llvmCode.append("define " + returnType + " @" + method.name + "(");
        for (int i = 0; i < method.params.size(); i++) {
            AST.VarDeclNode param = method.params.get(i);
            llvmCode.append(getLLVMType(param.type) + " %" + param.name);
            if (i < method.params.size() - 1) {
                llvmCode.append(", ");
            }
        }
        llvmCode.append(") {\n");
        generateBlock(method.body);
        if(returnType == "void"){
            llvmCode.append("\tret void\n");
        }
        llvmCode.append("}\n");
    }

    private void generateConstructor(AST.ConstructorNode constructor) {
        llvmCode.append("; Constructor\n");
        llvmCode.append("define void @constructor(");
        for (int i = 0; i < constructor.params.size(); i++) {
            AST.VarDeclNode param = constructor.params.get(i);
            llvmCode.append(getLLVMType(param.type) + " %" + param.name);
            if (i < constructor.params.size() - 1) {
                llvmCode.append(", ");
            }
        }
        llvmCode.append(") {\n");
        generateBlock(constructor.body);

        llvmCode.append("\tret void\n");
        
        llvmCode.append("}\n");
    }

    private void generateBlock(AST.BlockNode block) {
        for (AST.ASTNode stmt : block.statements) {
            if (stmt instanceof AST.VarDeclNode) {
                generateVarDecl((AST.VarDeclNode) stmt);
            } else if (stmt instanceof AST.ReturnNode) {
                generateReturn((AST.ReturnNode) stmt);
            } else if (stmt instanceof AST.IfNode) {
                generateIf((AST.IfNode) stmt);
            } else if (stmt instanceof AST.WhileNode) {
                generateWhile((AST.WhileNode) stmt);
            } else if (stmt instanceof AST.ForNode) {
                generateFor((AST.ForNode) stmt);
            } 
        }
    }

    private void generateVarDecl(AST.VarDeclNode varDecl) {
        String llvmType = getLLVMType(varDecl.type);
        llvmCode.append("\t%" + varDecl.name + " = alloca " + llvmType + ", align 1\n");
        if (varDecl.value != null) {
            String value = generateExpression(varDecl.value);
            llvmCode.append("\tstore " + llvmType + " " + value + ", " + llvmType + "* %" + varDecl.name + "\n");
        }
    }

    private void generateReturn(AST.ReturnNode returnNode) {
        String returnValue = generateExpression(returnNode.expr);
        llvmCode.append("\tret " + getLLVMType(returnNode.type) + " " + returnValue + "\n");
        hasRet = true;
    }

    private void generateIf(AST.IfNode ifNode) {
        String condition = generateExpression(ifNode.condition);
        String trueLabel = newLabel();
        String falseLabel = newLabel();
        llvmCode.append("\tbr i1 " + condition + ", label %" + trueLabel + ", label %" + falseLabel + "\n");
        llvmCode.append(trueLabel + ":\n");
        generateBlock(ifNode.trueBranch);
        llvmCode.append(falseLabel + ":\n");
        if (ifNode.falseBranch != null) {
            generateBlock(ifNode.falseBranch);
        }
    }

    private void generateWhile(AST.WhileNode whileNode) {
        String conditionLabel = newLabel();
        String bodyLabel = newLabel();
        String endLabel = newLabel();
        llvmCode.append(conditionLabel + ":\n");
        String condition = generateExpression(whileNode.condition);
        llvmCode.append("\tbr i1 " + condition + ", label %" + bodyLabel + ", label %" + endLabel + "\n");
        llvmCode.append(bodyLabel + ":\n");
        generateBlock(whileNode.body);
        llvmCode.append("\tbr label %" + conditionLabel + "\n");
        llvmCode.append(endLabel + ":\n");
    }

    private void generateFor(AST.ForNode forNode) {
        generateVarDecl(forNode.init);
        generateWhile(new AST.WhileNode(forNode.condition, forNode.body));
        generateExpression(forNode.update);
    }

    private String generateExpression(AST.ASTNode expr) {
        if (expr instanceof AST.NumberNode) {
            return Integer.toString(((AST.NumberNode) expr).value);
        } else if (expr instanceof AST.BinaryOpNode) {
            return generateBinaryOp((AST.BinaryOpNode) expr);
        }
        return "0";
    }

    private String generateBinaryOp(AST.BinaryOpNode binOp) {
        String left = generateExpression(binOp.left);
        String right = generateExpression(binOp.right);
        String temp = newTempVar();
        String op = binOp.operator.equals("+") ? "add" : "sub";
        llvmCode.append("\t" + temp + " = " + op + " i32 " + left + ", " + right + "\n");
        return temp;
    }

    private String getLLVMType(String type) {
        return switch (type) {
            case "int" -> "i32";
            case "boolean" -> "i1";
            case "void" -> "void";
            default -> "i32";
        };
    }
}
