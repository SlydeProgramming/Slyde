package slyde.compiler;

import java.util.List;

public class AST {

    public static abstract class ASTNode {}

    public static class ProgramNode extends ASTNode {
        public List<ClassNode> classes;

        public ProgramNode(List<ClassNode> classes) {
            this.classes = classes;
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
    }
    public static class ReturnNode extends ASTNode {
        public ASTNode expr;

        public ReturnNode(ASTNode expr) {
            this.expr = expr;
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
    }

    public static class AssignmentNode extends ASTNode {
        public String name;
        public ASTNode value;

        public AssignmentNode(String name, ASTNode value) {
            this.name = name;
            this.value = value;
        }

    }

    public static class ConstructorNode extends ASTNode{
        public List<VarDeclNode> params;
        public BlockNode body;

        public ConstructorNode(List<VarDeclNode> params, BlockNode body){
            this.params = params;
            this.body = body;
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
    }

    public static class BlockNode extends ASTNode {
        public List<ASTNode> statements;

        public BlockNode(List<ASTNode> statements) {
            this.statements = statements;
        }

    }

    public static class Expr extends ASTNode{
        
    }

    public static class LiteralNode extends Expr{

    }

    public static class StringNode extends LiteralNode {
        public String value;

        public StringNode(String value){
            this.value = value;
        }
    }

    public static class BooleanNode extends LiteralNode {
        public Boolean value;

        public BooleanNode(Boolean value){
            this.value = value;
        }
    }

    public static class NumberNode extends LiteralNode {
        public int value;

        public NumberNode(int value) {
            this.value = value;
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
    }

    public static class WhileNode extends ASTNode {
        public ConditionalOp condition;
        public BlockNode body;

        public WhileNode(ConditionalOp condition, BlockNode body) {
            this.condition = condition;
            this.body = body;
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
    }


    public static class MethodCallNode extends Expr {
        public String methodName;
        public List<ASTNode> arguments;

        public MethodCallNode(String methodName, List<ASTNode> arguments) {
            this.methodName = methodName;
            this.arguments = arguments;
        }

    }

    public static class PrintNode extends ASTNode {
        public ASTNode value;

        public PrintNode(ASTNode value) {
            this.value = value;
        }

    }



}
