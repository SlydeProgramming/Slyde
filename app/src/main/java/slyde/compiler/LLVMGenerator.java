package slyde.compiler;


public class LLVMGenerator {
    private StringBuilder llvmCode;
    private int tempVarCounter;
    private int labelCounter;

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
        for (AST.ASTNode node : classNode.body) {
            if (node instanceof AST.MethodNode) {
                generateMethod((AST.MethodNode) node);
            } else if (node instanceof AST.ConstructorNode) {
                generateConstructor((AST.ConstructorNode) node);
            } else if (node instanceof AST.VarDeclNode){
                generateFeildDecl((AST.VarDeclNode) node);
            }  else if (node instanceof AST.MainNode) {
                generateMain((AST.MainNode) node);
            }
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

        llvmCode.append("  ret void\n");
        
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
            llvmCode.append("  ret void\n");
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

        llvmCode.append("  ret void\n");
        
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
        llvmCode.append("  %" + varDecl.name + " = alloca " + llvmType + ", align 1\n");
        if (varDecl.value != null) {
            String value = generateExpression(varDecl.value);
            llvmCode.append("  store " + llvmType + " " + value + ", " + llvmType + "* %" + varDecl.name + "\n");
        }
    }

    private void generateFeildDecl(AST.VarDeclNode varDecl) {
        String llvmType = getLLVMType(varDecl.type);
        llvmCode.append("  %" + varDecl.name + " = type " + llvmType + "\n");
        if (varDecl.value != null) {
            String value = generateExpression(varDecl.value);
            llvmCode.append("  store " + llvmType + " " + value + ", " + llvmType + "* %" + varDecl.name + "\n");
        }
    }

    private void generateReturn(AST.ReturnNode returnNode) {
        String returnValue = generateExpression(returnNode.expr);
        llvmCode.append("  ret i32 " + returnValue + "\n");
    }

    private void generateIf(AST.IfNode ifNode) {
        String condition = generateExpression(ifNode.condition);
        String trueLabel = newLabel();
        String falseLabel = newLabel();
        llvmCode.append("  br i1 " + condition + ", label %" + trueLabel + ", label %" + falseLabel + "\n");
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
        llvmCode.append("  br i1 " + condition + ", label %" + bodyLabel + ", label %" + endLabel + "\n");
        llvmCode.append(bodyLabel + ":\n");
        generateBlock(whileNode.body);
        llvmCode.append("  br label %" + conditionLabel + "\n");
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
        llvmCode.append("  " + temp + " = " + op + " i32 " + left + ", " + right + "\n");
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
