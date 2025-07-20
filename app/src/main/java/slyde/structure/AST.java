package slyde.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import slyde.context.HandleProtocol;
import slyde.context.Context;
import slyde.generation.LLVMGeneratorVersionTwo;
import slyde.generation.MultiPartTextGenerator;
import slyde.utils.ErrorHandler;
import slyde.utils.Indent;

public class AST {

    public static ProgramNode storage;

    public static abstract class ASTNode {
        public int line;
        public int column;
        static int tI = 0;

        protected MultiPartTextGenerator cm = LLVMGeneratorVersionTwo.codemanager;

        public abstract String toString(Indent lvl);

        public <T> void gen(Context<T> ctx) {

            ErrorHandler.error(
                    "Unknown Generation protocol " + ctx.getHandleProtocol() + " for node: "
                            + this.getClass().getSimpleName(),
                    line,
                    column);
        }

        @SuppressWarnings("unchecked")
        public <T> T setPosition(ParserRuleContext ctx) {
            Token s = ctx.getStart();
            this.line = s.getLine();
            this.column = s.getCharPositionInLine();
            return (T) this;
        }
    }

    public static class ProgramNode extends ASTNode {
        public List<ClassNode> classes;
        public MainNode main;

        public ProgramNode(List<ClassNode> classes, MainNode main) {
            this.classes = classes;
            this.main = main;
            storage = this;
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Prog:\n";

            for (ClassNode c : classes) {
                lvl.up();
                str += c.toString(lvl) + "\n";
                lvl.down();
            }

            return str;
        }

    }

    public static class ClassNode extends ASTNode {
        public String name;
        public String parent;
        public List<ASTNode> body;

        public ClassNode(String name, String parent, List<ASTNode> body) {
            this.name = name;
            this.parent = parent;
            this.body = body;
        }

        public ClassNode(String name, List<ASTNode> body) {
            this.name = name;
            this.body = body;
        }

        public ConstructorNode getConstructor() {
            for (ASTNode n : body) {
                if (n instanceof ConstructorNode) {
                    return (ConstructorNode) n;
                }
            }
            return null;
        }

        public List<MethodNode> getMethods() {
            List<MethodNode> nodes = new ArrayList<>();
            for (ASTNode n : body) {
                if (n instanceof MethodNode) {
                    nodes.add((MethodNode) n);
                }
            }
            return nodes;
        }

        public List<VarDeclNode> getFields() {
            List<VarDeclNode> nodes = new ArrayList<>();
            for (ASTNode n : body) {
                if (n instanceof VarDeclNode) {
                    nodes.add((VarDeclNode) n);
                }
            }
            if (nodes.isEmpty()) {
                return null;
            }
            return nodes;
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Class:\n";
            str += lvl.up() + "Name: " + name + "\n" + lvl.get() + "Body:\n";
            for (ASTNode n : body) {
                lvl.up();
                str += n.toString(lvl) + "\n";
                lvl.down();
            }
            lvl.down();
            return str;
        }

    }

    public static class NewInstanceNode extends Expr {
        public String name;
        public List<ASTNode> args;
        public static List<String[]> objects = new ArrayList<>();

        public NewInstanceNode(String name, List<ASTNode> args) {
            this.name = name;
            this.args = args;
        }

        @Override
        public <T> void gen(Context<T> ctx) {
            if (ctx.is(HandleProtocol.GET)) {

                String varName = ctx.getRequestName();

                int insttI = tI++;

                cm.append(cm.get() + "%size_ptr_" + insttI + " = getelementptr %" + name + ", %" + name
                        + "* null, i32 1\n");

                cm.append(
                        cm.get() + "%size_" + insttI + " = ptrtoint %" + name + "* %size_ptr_" + insttI + " to i64\n");

                cm.append(cm.get() + "%" + varName + "_typed = call i8* @malloc(i64 %size_" + insttI + ")\n");

                cm.append(cm.get() + "%" + varName + " = bitcast i8* %" + varName + "_typed to %" + name + "*\n");

                cm.append(cm.get() + "call void @" + name + "_constructor(%" + name + "* %" + varName + "_typed)\n");

                ctx.setReturnValues("%" + varName, "%" + name + "*");

                objects.add(new String[] { "%" + varName, "%" + name + "*" });

                Context.regiserVar(varName, name);

            } else {
                ErrorHandler.warn("Warning new instance of " + name
                        + " created but its value is never stored", line, column);
            }
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "New Instance:\n";
            str += lvl.up() + "Name: " + name + "\n";
            str += lvl.get() + "Args:\n";
            for (ASTNode n : args) {
                lvl.up();
                str += n.toString(lvl) + "\n";
                lvl.down();
            }
            return str;
        }

    };

    public static class ReturnNode extends ASTNode {
        public ASTNode expr;
        public String type;
        public int c = 0;

        public ReturnNode(ASTNode expr, String type) {
            this.expr = expr;
            this.type = type;
        }

        @Override
        public <T> void gen(Context<T> ctx) {
            if (ctx.is(HandleProtocol.STANDALONE)) {

                if (type == "void") {
                    cm.append(cm.get() + "ret void\n");
                    return;
                }

                int rtI = tI++;

                ctx
                        .setHandleProtocol(HandleProtocol.GET)
                        .requestName(ctx.getContextName() + "resultValue_" + rtI);

                expr.gen(ctx);

                String rType = ctx.findReturnedType(ctx.getContextName() + "resultValue_" + rtI);

                String name = ctx.findReturnedName(
                        ctx.getContextName() + "resultValue_" + rtI);

                if (rType != MultiPartTextGenerator.getLLVMType(type)) {

                    if (rType.equals(MultiPartTextGenerator.getLLVMType(type) + "*")) {
                        String realVal = cm.load(ctx.getContextName(), MultiPartTextGenerator.getLLVMType(type), name);

                        if (realVal == null) {
                            ErrorHandler.error("attempted to return " + MultiPartTextGenerator.getSlydeType(rType)
                                    + " when expected " + type, line, column);
                        } else {
                            name = realVal;
                        }
                    } else {
                        ErrorHandler.error(
                                "Expected " + type + " but instead got " + MultiPartTextGenerator.getSlydeType(rType),
                                line, column);
                    }

                }

                cm.append(cm.get() + "ret " + MultiPartTextGenerator.getLLVMType(type) + " " + name + "\n");

            } else {
                ErrorHandler.error("Attempted to store a return statment", line, column);
            }
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Return:\n";
            str += lvl.up() + "Value:\n" + expr.toString(lvl);
            lvl.down();
            return str;
        }
    }

    public static class VarDeclNode extends ASTNode {
        public String type;
        public String name;
        public ASTNode value;

        public VarDeclNode(String type, String name, ASTNode value) {
            this.type = type;
            this.name = name;
            this.value = value;
        }

        @Override
        public <T> void gen(Context<T> ctx) {
            if (ctx.is(HandleProtocol.STANDALONE)) {

                ctx
                        .requestName(ctx.getContextName() + name)
                        .setHandleProtocol(HandleProtocol.GET);

                value.gen(ctx);

                String actualName = ctx.findReturnedName(ctx.getContextName() + name);

                if (actualName != null) {
                    if (!actualName.equals("%" + ctx.getContextName() + name)) {
                        cm.append(cm.get() + actualName + " = "
                                + ctx.findReturnedType(ctx.getContextName() + name) + " "
                                + ctx.findReturnedName(ctx.getContextName() + name) + "\n");
                    }
                }

            } else {
                ErrorHandler.error("FUCK idk why u are trying to store this ;-;-;-;-;-;-;-;", line, column);
            }
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "VarDec:\n";
            str += lvl.up() + "Type: " + type + "\n" + lvl.get() + "Name: " + name + "\n" + lvl.get() + "Value:\n";
            String v;

            if (value == null) {
                str += lvl.up() + "null";
                lvl.down();
            } else {
                lvl.up();
                v = value.toString(lvl);
                str += v;
                lvl.down();
            }
            lvl.down();
            return str;
        }
    }

    public static class AssignmentNode extends ASTNode {
        public String name;
        public ASTNode value;

        public AssignmentNode(String name, ASTNode value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Assign:\n";
            str += lvl.up() + "Name: " + name + "\n" + lvl.get() + "Value:\n";
            lvl.up();
            str += value.toString(lvl);
            lvl.down();
            lvl.down();
            return str;
        }

    }

    public static class ConstructorNode extends ASTNode {
        public List<VarDeclNode> params;
        public BlockNode body;

        public ConstructorNode(List<VarDeclNode> params, BlockNode body) {
            this.params = params;
            this.body = body;
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Constructor:\n";
            str += lvl.up() + "Parameters:\n";
            for (VarDeclNode param : params) {
                lvl.up();
                str += param.toString(lvl) + "\n";
                lvl.down();
            }
            str += lvl.get() + "Body:\n";
            lvl.up();
            str += body.toString(lvl);
            lvl.down();
            lvl.down();
            return str;
        }
    }

    public static class MainNode extends ASTNode {
        public List<VarDeclNode> params;
        public BlockNode body;

        public MainNode(List<VarDeclNode> params, BlockNode body) {
            this.params = params;
            this.body = body;
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Main:\n";
            str += lvl.up() + "Parameters:\n";
            if (params != null) {
                for (VarDeclNode param : params) {
                    lvl.up();
                    str += param.toString(lvl) + "\n";
                    lvl.down();
                }
            }
            str += lvl.get() + "Body:\n";
            lvl.up();
            str += body.toString(lvl);
            lvl.down();
            lvl.down();
            return str;
        }
    }

    public static class MethodNode extends ASTNode {
        public String returnType;
        public String name;
        public List<VarDeclNode> params;
        public BlockNode body;
        public String id;

        public MethodNode(String returnType, String name, List<VarDeclNode> params, BlockNode body) {
            this.returnType = returnType;
            this.name = name;
            this.params = params;
            this.body = body;
            this.id = UUID.randomUUID().toString().replace("-", "");
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Method:\n";
            str += lvl.up() + "Return Type: " + returnType + "\n";
            str += lvl.get() + "Name: " + name + "\n";
            str += lvl.get() + "Parameters:\n";
            for (VarDeclNode param : params) {
                lvl.up();
                str += param.toString(lvl) + "\n";
                lvl.down();
            }
            str += lvl.get() + "Body:\n";
            lvl.up();
            str += body.toString(lvl);
            lvl.down();
            lvl.down();
            return str;
        }
    }

    public static class BlockNode extends ASTNode {
        public List<ASTNode> statements;

        public BlockNode(List<ASTNode> statements) {
            this.statements = statements;
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Block:\n";
            for (ASTNode stmt : statements) {
                if (stmt != null) {
                    lvl.up();
                    str += stmt.toString(lvl) + "\n";
                    lvl.down();
                }
            }
            return str;
        }

    }

    public static abstract class Expr extends ASTNode {

    }

    public abstract static class LiteralNode extends Expr {

    }

    public static class StringNode extends LiteralNode {
        public String value;

        public StringNode(String value) {
            value = value.replaceFirst("\"", "");
            char[] cars = value.toCharArray();
            String val = "";
            for (int i = 0; i < cars.length - 1; i++) {
                val += cars[i];
            }
            this.value = val.replace("\"", "\\\"");
        }

        @Override
        public <T> void gen(Context<T> ctx) {
            if (!Context.createdStrings.contains(value)) {

                cm.appendHead("@\"" + value + "\" = private constant [" + (value.length() + 1) + " x i8] c\"" + value
                        + "\\00\"\n");
                Context.createdStrings.add(value);
            }

            if (ctx.is(HandleProtocol.GET)) {
                cm.append(cm.get() + "%" + ctx.getRequestName() + " = getelementptr inbounds [" + (value.length() + 1)
                        + " x i8], [" + (value.length() + 1) + " x i8]* @\"" + value + "\", i32 0, i32 0\n");
                ctx.setReturnValues("%" + ctx.getRequestName(), "i8*");
            }

        }

        @Override
        public String toString(Indent lvl) {
            return lvl.get() + "String: " + value;
        }
    }

    public static class BooleanNode extends LiteralNode {
        public Boolean value;

        public BooleanNode(Boolean value) {
            this.value = value;
        }

        @Override
        public <T> void gen(Context<T> ctx) {
            if (ctx.is(HandleProtocol.GET)) {
                cm.append(cm.get() + "%" + ctx.getRequestName() + " = alloca i1\n");
                cm.append(cm.get() + "store i1 " + (value ? "1" : "0") + ", i1* %" + ctx.getRequestName() + "\n");
                ctx.setReturnValues('%' + ctx.getRequestName(), "i1*");
            } else {
                ErrorHandler.warn("Boolean " + ctx.getContextName() + " is never stored", line, column);
            }
        }

        @Override
        public String toString(Indent lvl) {
            return lvl.get() + "Boolean: " + value;
        }
    }

    public static class IdentifierNode extends Expr {
        public String name;
        public String type;

        public IdentifierNode(String name, String type) {
            this.name = name;
            this.type = type;
        }

        @Override
        public <T> void gen(Context<T> ctx) {
            ctx.setReturnValues(ctx.findReturnedName(ctx.getContextName() + name),
                    ctx.findReturnedType(ctx.getContextName() + name));
        }

        @Override
        public String toString(Indent lvl) {
            return lvl.get() + "Identifier: " + name;
        }

    }

    public static class DoubleNode extends LiteralNode {
        public double value;

        public DoubleNode(double value) {
            this.value = value;
        }

        @Override
        public String toString(Indent lvl) {
            return lvl.get() + "Double: " + value;
        }

    }

    public static class NumberNode extends LiteralNode {
        public int value;

        public NumberNode(int value) {
            this.value = value;
        }

        @Override
        public <T> void gen(Context<T> ctx) {
            if (ctx.is(HandleProtocol.GET)) {
                cm.append(cm.get() + "%" + ctx.getRequestName() + " = alloca i32\n");
                cm.append(cm.get() + "store i32 " + value + ", i32* %" + ctx.getRequestName() + "\n");
                ctx.setReturnValues('%' + ctx.getRequestName(), "i32*");
            } else {
                ErrorHandler.warn("Integer " + ctx.getContextName() + " is never stored", line, column);
            }
        }

        @Override
        public String toString(Indent lvl) {
            return lvl.get() + "Number: " + value;
        }
    }

    public static class ConditionalOp extends Expr {
        public String operator;
        public Expr left;
        public Expr right;

        public ConditionalOp(Expr left, Expr right, String operator) {
            this.operator = operator;
            this.left = left;
            this.right = right;
        }

        @Override
        public <T> void gen(Context<T> ctx) {

            if (ctx.is(HandleProtocol.GET)) {

                int ltI = tI++;
                int rtI = tI++;
                String retName = ctx.getRequestName();
                ctx
                        .setHandleProtocol(HandleProtocol.GET)
                        .requestName(ctx.getContextName() + "condOp_" + ltI);

                left.gen(ctx);

                ctx
                        .setHandleProtocol(HandleProtocol.GET)
                        .requestName(ctx.getContextName() + "condOp_" + rtI);

                right.gen(ctx);

                ctx.requestName(retName);

                String lName = ctx.findReturnedName(ctx.getContextName() + "condOp_" + ltI);
                String lType = ctx.findReturnedType(ctx.getContextName() + "condOp_" + ltI);
                String rName = ctx.findReturnedName(ctx.getContextName() + "condOp_" + rtI);
                String rType = ctx.findReturnedType(ctx.getContextName() + "condOp_" + rtI);

                String result = "%" + retName;
                String cmpOp;

                if (lType.equals("i32*") || lType.equals("i1*")) {
                    lName = cm.load(ctx.getContextName(), lType == "i32*" ? "i32" : "i1", lName);
                    lType = lType == "i32*" ? "i32" : "i1";
                }

                if (rType.equals("i32*") || rType.equals("i1*")) {
                    rName = cm.load(ctx.getContextName(), rType == "i32*" ? "i32" : "i1", rName);
                    rType = rType == "i32*" ? "i32" : "i1";
                }

                if (lType.equals("i32") || lType.equals("i1")) {

                    boolean special = false;

                    switch (operator) {
                        case "==":
                            cmpOp = "eq";
                            break;
                        case "!=":
                            cmpOp = "ne";
                            break;
                        case "<":
                            cmpOp = "slt";
                            break;
                        case "<=":
                            cmpOp = "sle";
                            break;
                        case ">":
                            cmpOp = "sgt";
                            break;
                        case ">=":
                            cmpOp = "sge";
                            break;
                        case "&&":
                            cmpOp = "and";
                            special = true;
                            break;
                        case "||":
                            cmpOp = "or";
                            special = true;
                            break;
                        default:
                            ErrorHandler.error("Unknown operator: " + operator, line, column);
                            return;
                    }

                    if (special && !lType.equals("i1")) {
                        ErrorHandler.error("Logical operators '&&' and '||' require boolean operands (i1)", line,
                                column);
                        return;
                    }

                    if (special) {
                        cm.append(cm.get() + result + " = " + cmpOp + " " + lType + " " + lName + ", " + rName + "\n");
                    } else {
                        cm.append(cm.get() + result + " = icmp " + cmpOp + " " + lType + " " + lName + ", " + rName
                                + "\n");
                    }

                } else if (lType.equals("double")) {
                    switch (operator) {
                        case "==":
                            cmpOp = "oeq";
                            break;
                        case "!=":
                            cmpOp = "one";
                            break;
                        case "<":
                            cmpOp = "olt";
                            break;
                        case "<=":
                            cmpOp = "ole";
                            break;
                        case ">":
                            cmpOp = "ogt";
                            break;
                        case ">=":
                            cmpOp = "oge";
                            break;
                        default:
                            ErrorHandler.error("Unknown operator: " + operator, line, column);
                            return;
                    }
                    cm.append(cm.get() + result + " = fcmp " + cmpOp + " double " + lName + ", " + rName + "\n");

                } else if (lType.equals("i8*")) {
                    switch (operator) {
                        case "==":
                            cm.append(cm.get() + result + " = call i1 @slyde_str_eq(i8* " + lName + ", i8* " + rName
                                    + ")\n");
                            break;
                        case "!=":
                            String temp = "%" + ctx.getContextName() + "compareResult_" + tI++;
                            cm.append(cm.get() + temp + " = call i1 @slyde_str_eq(i8* " + lName + ", i8* " + rName
                                    + ")\n");
                            cm.append(cm.get() + result + " = xor i1 " + temp + ", true\n");
                            break;
                        default:
                            ErrorHandler.error("Unsupported string comparison operator: " + operator, line, column);
                            return;
                    }

                } else {
                    ErrorHandler.error("Unsupported operand types: " + lType + ", " + rType, line, column);
                    return;
                }

                ctx.setReturnValues(result, "i1");

            } else {
                ErrorHandler.warn("Conditional statment not stored", line, column);
            }

        }

        @Override
        public String toString(Indent lvl) {
            String str = "";

            str += lvl.get() + "ConditionalOp:\n";
            lvl.up();
            str += left.toString(lvl) + "\n";
            str += lvl.get() + "Operator: " + operator + "\n";
            str += right.toString(lvl);
            lvl.down();
            return str;
        }
    }

    public static class BinaryOpNode extends Expr {
        public String operator;
        public Expr left;
        public Expr right;

        public BinaryOpNode(Expr left, String operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        public String toString(Indent lvl) {
            String str = "";

            str += lvl.get() + "BinaryOp:\n";
            lvl.up();
            str += left.toString(lvl) + "\n";
            str += lvl.get() + "Operator: " + operator + "\n";
            str += right.toString(lvl);
            lvl.down();
            return str;
        }
    }

    public static class IfNode extends ASTNode {
        public Expr condition;
        public BlockNode trueBranch;
        public BlockNode falseBranch;

        public IfNode(Expr condition, BlockNode trueBranch, BlockNode falseBranch) {
            this.condition = condition;
            this.trueBranch = trueBranch;
            this.falseBranch = falseBranch;
        }

        @Override
        public <T> void gen(Context<T> ctx) {

            if (ctx.is(HandleProtocol.STANDALONE)) {
                int cTi = tI;

                ctx
                        .setHandleProtocol(HandleProtocol.GET)
                        .requestName(ctx.getContextName() + "condition_" + tI);

                tI++;
                condition.gen(ctx);

                String rT = ctx.findReturnedType(ctx.getContextName() + "condition_" + cTi);
                String val;
                if (rT.equals("i1*")) {
                    val = cm.load(ctx.getContextName(), "i1", ctx.findReturnedName(
                            ctx.getContextName() + "condition_" + cTi));
                } else if (rT.equals("i1")) {
                    val = ctx.findReturnedName(ctx.getContextName() + "condition_" + cTi);
                } else {
                    ErrorHandler.error("Invalid conditional type cannot evaluate " + rT + " as a boolean", line,
                            column);
                    return; // error thrown above so this is just here to stop java from complaing about val
                }

                int b1 = tI++;
                int b2 = tI++;
                int b3 = b2;
                if (falseBranch != null) {
                    b3 = tI++;
                }
                cm.append(cm.get() + "br i1 " + val + ", label %label" + b1 + ", label %label" + b2 + "\n");

                cm.createLabel(b1);

                cm.up();

                ctx
                        .setHandleProtocol(HandleProtocol.STANDALONE)
                        .addContextName("ifStm" + b1);

                for (ASTNode node : trueBranch.statements) {
                    if (node != null) {
                        ctx.setHandleProtocol(HandleProtocol.STANDALONE);
                        node.gen(ctx);
                    }
                }

                cm.append(cm.get() + "br label %label" + b3 + "\n");

                cm.down();
                ctx.popContext();

                if (falseBranch != null) {
                    cm.createLabel(b2);
                    cm.up();

                    ctx.addContextName("ifStm" + b2);

                    for (ASTNode node : falseBranch.statements) {
                        if (node != null) {
                            ctx.setHandleProtocol(HandleProtocol.STANDALONE);
                            node.gen(ctx);
                        }
                    }

                    cm.append(cm.get() + "br label %label" + b3 + "\n");

                    cm.down();
                    ctx.popContext();

                }

                cm.createLabel(b3);

            } else {
                ErrorHandler.error("Attempted to store an if statment??????", line, column);
            }

        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "IfNode:\n";
            str += lvl.up() + "Condition:\n";
            lvl.up();
            str += condition.toString(lvl) + "\n";
            str += lvl.down() + "True Branch:\n";
            lvl.up();
            str += trueBranch.toString(lvl) + "\n";
            lvl.down();
            if (falseBranch != null) {
                str += lvl.get() + "False Branch:\n";
                lvl.up();
                str += falseBranch.toString(lvl) + "\n";
                lvl.down();
            }
            return str;
        }
    }

    public static class WhileNode extends ASTNode {
        public Expr condition;
        public BlockNode body;

        public WhileNode(Expr condition, BlockNode body) {
            this.condition = condition;
            this.body = body;
        }

        @Override
        public String toString(Indent lvl) {
            String str = "";

            str += lvl.get() + "WhileNode:\n" + lvl.up() + "Condition:\n";
            lvl.up();
            str += condition.toString(lvl) + "\n";
            str += lvl.down() + "Body:\n";
            lvl.up();
            str += body.toString(lvl);
            lvl.down();
            lvl.down();
            return str;
        }
    }

    public static class ForNode extends ASTNode {
        public VarDeclNode init;
        public Expr condition;
        public AssignmentNode update;
        public BlockNode body;

        public ForNode(VarDeclNode init, Expr condition, AssignmentNode update, BlockNode body) {
            this.init = init;
            this.condition = condition;
            this.update = update;
            this.body = body;
        }

        @Override
        public String toString(Indent lvl) {
            String str = "";

            str += lvl.get() + "ForNode:\n" + lvl.up() + "Init:\n";
            lvl.up();
            str += init.toString(lvl) + "\n";
            str += lvl.down() + "Condition:\n";
            lvl.up();
            str += condition.toString(lvl) + "\n";
            str += lvl.down() + "Update:\n";
            lvl.up();
            str += update.toString(lvl) + "\n";
            str += lvl.down() + "Body:\n";
            lvl.up();
            str += body.toString(lvl) + "\n";
            lvl.down();
            lvl.down();
            return str;
        }
    }

    public static class MethodCallNode extends Expr {
        public String methodName;
        public String caller = null;
        public List<ASTNode> arguments;
        public String type = null;

        public MethodCallNode(String methodName, List<ASTNode> arguments, String caller) {
            this.methodName = methodName;
            this.arguments = arguments;
            this.caller = caller;
        }

        @Override
        public <T> void gen(Context<T> ctx) {
            HandleProtocol og = ctx.getHandleProtocol();

            List<Integer> tIs = new ArrayList<>();

            int i = 0;
            for (ASTNode node : arguments) {
                ctx
                        .requestName(methodName + "_" + node.getClass().getSimpleName() + "_" + tI)
                        .setHandleProtocol(HandleProtocol.GET);
                node.gen(ctx);
                tIs.add(tI);
                tI++;
                i++;
            }

            List<String> adds = new ArrayList<>();
            List<String> typs = new ArrayList<>();

            for (int j = 0; j < i; j++) {
                String lookUp = methodName + "_" + arguments.get(j).getClass().getSimpleName() + "_"
                        + tIs.get(j);
                String name = ctx.findReturnedName(lookUp);
                String type = ctx.findReturnedType(lookUp);
                adds.add(type + " " + name);
            }

            if (caller != null) {
                String type = ctx.findRegisteredType(caller);
                List<MethodNode> canidates = ctx.findRegisterMethods(type, methodName);

                canidates.removeIf((n) -> {
                    return n.params.size() != arguments.size();
                });

                canidates.removeIf((n) -> {
                    int c = 0;
                    for (VarDeclNode v : n.params) {
                        if (v.type != typs.get(c)) {
                            return true;
                        }
                        c++;
                    }
                    return false;
                });

                if (canidates.size() != 1) {
                    if (canidates.size() > 0) {
                        ErrorHandler.error(
                                "Fuck you man, you made " + canidates.size()
                                        + " of the same method an idk what to do with it in class  "
                                        + type + " method name " + methodName,
                                line, column);
                    } else {
                        ErrorHandler.error("I cant find this fucking method you dipshit you tryna find it in class "
                                + type + " name " + methodName, line, column);
                    }
                } else {
                    this.methodName = type + "_" + this.methodName + "_" + canidates.get(0).id;

                    adds.addFirst("%" + type + "* %" + ctx.getContextName() + caller);

                    this.type = MultiPartTextGenerator.getLLVMType(canidates.get(0).returnType);
                }
            } else {
                this.type = LLVMGeneratorVersionTwo.defaultRetRegistery.get(methodName);
                if (this.type == null) {
                    ErrorHandler.error("I cant find this fucking method you dipshit name " + methodName, line, column);
                }
            }

            if (og.equals(HandleProtocol.STANDALONE)) {

                cm.append(cm.get() + "call " + type + " @" + methodName + "(");

            } else {
                cm.append(cm.get() + "%" + ctx.getRequestName() + " = call " + type + " @" + methodName + "(");

                ctx.setReturnValues("%" + ctx.getRequestName(), this.type);
            }

            for (String a : adds) {
                cm.append(a + ",");
            }

            if (adds.size() > 0) {
                cm.removeLastChar();
            }

            cm.append(")\n");

        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "MethodCall:\n" + lvl.up() + "Name: " + methodName + "\n";
            str += lvl.get() + "Arguments:\n";
            for (ASTNode arg : arguments) {
                lvl.up();
                str += arg.toString(lvl) + "\n";
                lvl.down();
            }
            lvl.down();
            return str;
        }
    }
}