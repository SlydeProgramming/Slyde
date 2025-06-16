package slyde.generation;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import slyde.compiler.LP.SlydeParser.*;
import slyde.structure.AST.*;
import slyde.utils.Indent;

import java.util.ArrayList;
import java.util.List;

public class ASTGenerator {

    public static class Context {
        String name;
        Context parent = null;

        public Context(String name) {
            this.name = name;
        }

        public Context(String name, Context parent) {
            this.name = name;
            this.parent = parent;
        }

        public Context getParent() {
            return parent;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    public static List<String> vars = new ArrayList<>();
    public static List<String> varTypes = new ArrayList<>();

    public static Context currentContext = null;

    public static int blockIndex = 0;

    public static String getNewBlockContext() {
        String str = ".block" + blockIndex;
        blockIndex++;
        return str;
    }

    public static ProgramNode generateAST(ProgContext ctx) {
        List<ClassNode> classNodes = new ArrayList<>();

        currentContext = new Context("prog");

        MainNode main = createMainNode(ctx.paramList(0), ctx.block(0));

        for (int i = 0; i < ctx.classDeclaration().size(); i++) {
            ClassDeclarationContext classDeclTree = ctx.classDeclaration(i);
            classNodes.add(createClassNode(classDeclTree));
        }

        return new ProgramNode(classNodes, main);
    }

    public static ClassNode createClassNode(ClassDeclarationContext ctx) {
        List<TerminalNode> TNL = ctx.IDENTIFIER();
        String name = TNL.get(0).getText();

        currentContext = new Context(currentContext + "." + name, currentContext);

        List<ASTNode> body = new ArrayList<>();

        body = createClassBodyNode(ctx.classBody());

        return new ClassNode(name, body);
    }

    public static List<ASTNode> createClassBodyNode(ClassBodyContext ctx) {
        List<ASTNode> body = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            body.add(createASTNode(ctx.getChild(i)));
        }
        return body;
    }

    public static ASTNode createASTNode(ParseTree tree) {

        if (tree instanceof VarDeclContext) {
            return createVarDeclNode((VarDeclContext) tree);
        } else if (tree instanceof TerminalNode) {
            return createTerminalNode((TerminalNode) tree);
        } else if (tree instanceof ConstructorContext) {
            return createConstructorNode((ConstructorContext) tree);
        } else if (tree instanceof ExprContext) {
            return createExprNode((ExprContext) tree);
        } else if (tree instanceof StatementContext) {
            return createASTNode(tree.getChild(0));
        } else if (tree instanceof IfStmtContext) {
            return createIfNode((IfStmtContext) tree);
        } else if (tree instanceof AssignmentContext) {
            return createAssignmentNode((AssignmentContext) tree);
        } else if (tree instanceof MethodCallContext) {
            return createMethodCallNode((MethodCallContext) tree);
        } else {
            System.out.println(tree.toStringTree());
        }
        return null;
    }

    public static AssignmentNode createAssignmentNode(AssignmentContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        int exprSize = ctx.expr().size();
        ASTNode value = createASTNode(ctx.expr(exprSize - 1));

        return new AssignmentNode(name, value);
    }

    public static IfNode createIfNode(IfStmtContext ctx) {

        String blockContext = getNewBlockContext();

        currentContext = new Context(currentContext + "." + blockContext, currentContext);

        Expr condition = createExprNode(ctx.expr());

        BlockNode trueBranch = createBlockNode(ctx.block(0));

        BlockNode falseBranch = null;
        if (ctx.ELSE() != null) {
            falseBranch = createBlockNode(ctx.block(1));
        }

        currentContext = currentContext.getParent();

        return new IfNode(condition, trueBranch, falseBranch);
    }

    public static MainNode createMainNode(ParamListContext ctx1, BlockContext ctx2) {

        String blockContext = getNewBlockContext();

        currentContext = new Context(currentContext + "." + blockContext, currentContext);

        List<VarDeclNode> params = null;
        if (ctx1 != null) {
            params = createParamsListNode(ctx1);
        }
        BlockNode body = createBlockNode(ctx2);

        currentContext = currentContext.getParent();

        return new MainNode(params, body);
    }

    public static Expr createExprNode(ExprContext ctx) {
        Expr left = null;

        if (ctx.expr(0) != null) {
            left = createExprNode(ctx.expr(0));
        } else if (ctx.NUMBER() != null) {
            return (Expr) createTerminalNode(ctx.NUMBER());
        } else if (ctx.STRING() != null) {
            return (Expr) createTerminalNode(ctx.STRING());
        } else if (ctx.BOOLEAN() != null) {
            return (Expr) createTerminalNode(ctx.BOOLEAN());
        } else if (ctx.methodCall() != null) {
            return createMethodCallNode(ctx.methodCall());
        } else if (ctx.IDENTIFIER() != null) {
            return (Expr) createTerminalNode(ctx.IDENTIFIER());
        } else if (ctx.newInstance() != null) {
            return createNewInstanceNode(ctx.newInstance());
        }

        if (ctx.expr(1) != null) {
            String operator = ctx.getChild(1).getText();
            Expr right = createExprNode(ctx.expr(1));

            if (ctx.binOp() != null) {
                return new BinaryOpNode(left, operator, right);
            } else if (ctx.compareOp() != null) {
                return new ConditionalOp(left, right, operator);
            }
        }

        return left;
    }

    public static NewInstanceNode createNewInstanceNode(NewInstanceContext ctx) {
        List<ASTNode> params = createArgsListNode(ctx.argList());
        String name = ctx.IDENTIFIER().getText();

        return new NewInstanceNode(name, params);
    }

    public static MethodCallNode createMethodCallNode(MethodCallContext ctx) {
        List<ASTNode> params = createArgsListNode(ctx.argList());
        String name = ctx.IDENTIFIER().getText();

        return new MethodCallNode(name, params);
    }

    public static List<ASTNode> createArgsListNode(ArgListContext ctx) {
        List<ASTNode> args = new ArrayList<>();

        if (ctx == null) {
            return args;
        }

        for (int i = 0; i < ctx.expr().size(); i++) {
            args.add(createExprNode(ctx.expr(i)));
        }
        return args;
    }

    public static BlockNode createBlockNode(BlockContext ctx) {
        List<ASTNode> statments = new ArrayList<>();
        for (int i = 0; i < ctx.statement().size(); i++) {
            statments.add(createASTNode(ctx.statement(i)));
        }

        return new BlockNode(statments);

    }

    public static List<VarDeclNode> createParamsListNode(ParamListContext ctx) {
        List<VarDeclNode> params = new ArrayList<>();

        if (ctx == null) {
            return params;
        }

        for (int i = 0; i < ctx.type().size(); i++) {
            vars.add(currentContext + "." + ctx.IDENTIFIER(i).getText());
            varTypes.add(ctx.type(i).getText());
            params.add(new VarDeclNode(ctx.type(i).getText(), ctx.IDENTIFIER(i).getText(), null));
        }

        return params;
    }

    public static ConstructorNode createConstructorNode(ConstructorContext ctx) {
        String blockContext = getNewBlockContext();

        currentContext = new Context(currentContext + "." + blockContext, currentContext);

        List<VarDeclNode> params = createParamsListNode(ctx.paramList());
        BlockNode body = createBlockNode(ctx.block());

        currentContext = currentContext.getParent();

        return new ConstructorNode(params, body);

    }

    public static boolean isBool(String text) {
        return text.equals("yes") || text.equals("no") || text.equals("true") || text.equals("false");
    }

    public static <T> int getIndex(List<T> list, T value) {
        int index = 0;
        for (T v : list) {
            if (v.equals(value)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static String getType(String varName, Context ctx) {
        int index = getIndex(vars, ctx + "." + varName);

        while (index == -1) {
            index = getIndex(vars, ctx.parent + "." + varName);
            ctx = ctx.parent;
        }

        return varTypes.get(index);
    }

    public static String getType(String varName) {
        return getType(varName, currentContext);
    }

    public static ASTNode createTerminalNode(TerminalNode ctx) {
        String text = ctx.getText();
        if (text.matches("\\d+")) {
            return new NumberNode(Integer.parseInt(text));
        } else if (isBool(text)) {
            boolean value = text.equals("yes") || text.equals("true") || text.equals("on");
            return new BooleanNode(value);
        } else if (text.contains("\"")) {
            return new StringNode(text);
        }
        return new IdentifierNode(text, getType(text));
    }

    public static VarDeclNode createVarDeclNode(VarDeclContext ctx) {
        String type = ctx.getChild(0).getText();

        String name = ctx.IDENTIFIER().getText();

        ExprContext expr = ctx.expr();

        ASTNode value = null;

        if (expr != null) {
            value = createASTNode(expr);
        }

        vars.add(currentContext + "." + name);
        varTypes.add(type);

        return new VarDeclNode(type, name, value);

    }

    public static void printProg(ProgramNode node) {
        System.out.println(node.toString(new Indent()));
    }

    public static void printProg(ProgramNode node, String IndentType) {
        System.out.println(node.toString(new Indent(IndentType)));
    }

}
