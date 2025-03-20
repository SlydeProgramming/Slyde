package slyde.compiler;

import java.util.List;

public class AST {

    public static class Indent{
        int lvl = -3;
        String indentType = "  ";

        public Indent(){
            lvl = 0;
        }

        public Indent(String indentType){
            lvl = 0;
            this.indentType = indentType;
        }

        public String get(){
            String str = "";
            for (int i = 0; i < lvl; i++){
                str += indentType;
            }
            return str;
        }

        public String up(){
            lvl++;
            return get();
        }

        public String down(){
            lvl--;
            return get();
        }
    }

    public static abstract class ASTNode {
        public abstract String toString(Indent lvl);
    }

    public static class ProgramNode extends ASTNode {
        public List<ClassNode> classes;

        public ProgramNode(List<ClassNode> classes) {
            this.classes = classes;
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Prog:\n";

            for (ClassNode c : classes){
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

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Class:\n";
            str += lvl.up() + "Name: " + name + "\n" + lvl.get() + "Body:\n";
            for (ASTNode n : body){
                lvl.up();
                str += n.toString(lvl) + "\n";
                lvl.down();
            }
            lvl.down();
            return str;
        }
    }
    public static class ReturnNode extends ASTNode {
        public ASTNode expr;

        public ReturnNode(ASTNode expr) {
            this.expr = expr;
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
        public String toString(Indent lvl) {
            String str = lvl.get() + "VarDec:\n";
            str += lvl.up() + "Type: " + type + "\n" + lvl.get() + "Name: " + name + "\n" + lvl.get() + "Value:\n";
            String v;

            if (value == null){
                lvl.up();
                str += lvl.get() + "null";
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

    public static class ConstructorNode extends ASTNode{
        public List<VarDeclNode> params;
        public BlockNode body;

        public ConstructorNode(List<VarDeclNode> params, BlockNode body){
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


    public static class MethodNode extends ASTNode {
        public String returnType;
        public String name;
        public List<VarDeclNode> params;
        public BlockNode body;

        public MethodNode(String returnType, String name, List<VarDeclNode> params, BlockNode body) {
            this.returnType = returnType;
            this.name = name;
            this.params = params;
            this.body = body;
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
                lvl.up();
                str += stmt.toString(lvl) + "\n";
                lvl.down();
            }
            return str;
        }

    }

    public static abstract class Expr extends ASTNode{

    }

    public abstract static class LiteralNode extends Expr{

    }

    public static class StringNode extends LiteralNode {
        public String value;

        public StringNode(String value){
            this.value = value;
        }

        @Override
        public String toString(Indent lvl) {
            return lvl.get() + "String: " + value;
        }
    }

    public static class BooleanNode extends LiteralNode {
        public Boolean value;

        public BooleanNode(Boolean value){
            this.value = value;
        }

        @Override
        public String toString(Indent lvl) {
            return lvl.get() + "Boolean: " + value;
        }
    }

    public static class NumberNode extends LiteralNode {
        public int value;

        public NumberNode(int value) {
            this.value = value;
        }

        @Override
        public String toString(Indent lvl) {
            return lvl.get() + "Number: " + value;
        }
    }

    public static class ConditionalOp extends Expr{
        public String operator;
        public Expr left;
        public Expr right;

        public ConditionalOp(Expr left, Expr right, String operator){
            this.operator = operator;
            this.left = left;
            this.right = right;
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
        public ConditionalOp condition;
        public BlockNode trueBranch;
        public BlockNode falseBranch;

        public IfNode(ConditionalOp condition, BlockNode trueBranch, BlockNode falseBranch) {
            this.condition = condition;
            this.trueBranch = trueBranch;
            this.falseBranch = falseBranch;
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "IfNode:\n";
            str += lvl.up() + "Condition:\n";
            lvl.up();
            str += condition.toString(lvl) + "\n";
            lvl.down();
            str += lvl.get() + "True Branch:\n"; 
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
        public ConditionalOp condition;
        public BlockNode body;

        public WhileNode(ConditionalOp condition, BlockNode body) {
            this.condition = condition;
            this.body = body;
        }

        @Override
        public String toString(Indent lvl) {
            String str = "";

            str += lvl.get() + "WhileNode:\n" + lvl.up() + "Condition:\n"; 
            lvl.up();
            str += condition.toString(lvl) + "\n";
            lvl.down();
            str += lvl.get() + "Body:\n";
            lvl.up(); 
            str += body.toString(lvl);
            lvl.down();
            lvl.down();
            return str;
        }
    }


    public static class ForNode extends ASTNode {
        public VarDeclNode init;
        public ConditionalOp condition;
        public AssignmentNode update;
        public BlockNode body;

        public ForNode(VarDeclNode init, ConditionalOp condition, AssignmentNode update, BlockNode body) {
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
            lvl.down();
            str += lvl.get() + "Condition:\n"; 
            lvl.up();
            str += condition.toString(lvl) + "\n";
            lvl.down();
            str += lvl.get() + "Update:\n"; 
            lvl.up();
            str += update.toString(lvl) + "\n";
            lvl.down();
            str += lvl.get() + "Body:\n"; 
            lvl.up();
            str += body.toString(lvl) + "\n";
            lvl.down();
            lvl.down();
            return str;
        }
    }


    public static class MethodCallNode extends Expr {
        public String methodName;
        public List<ASTNode> arguments;

        public MethodCallNode(String methodName, List<ASTNode> arguments) {
            this.methodName = methodName;
            this.arguments = arguments;
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

    public static class PrintNode extends ASTNode {
        public ASTNode value;

        public PrintNode(ASTNode value) {
            this.value = value;
        }

        @Override
        public String toString(Indent lvl) {
            String str = lvl.get() + "Print:\n" + lvl.up() + "Value:\n"; 
            lvl.up();
            str += value.toString(lvl);
            lvl.down();
            lvl.down();
            return str;
        }

    }



}
