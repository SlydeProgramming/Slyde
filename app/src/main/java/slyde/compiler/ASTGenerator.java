package slyde.compiler;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import slyde.compiler.AST.ASTNode;
import slyde.compiler.AST.AssignmentNode;
import slyde.compiler.AST.BinaryOpNode;
import slyde.compiler.AST.BlockNode;
import slyde.compiler.AST.BooleanNode;
import slyde.compiler.AST.ClassNode;
import slyde.compiler.AST.ConditionalOp;
import slyde.compiler.AST.ConstructorNode;
import slyde.compiler.AST.Expr;
import slyde.compiler.AST.IfNode;
import slyde.compiler.AST.Indent;
import slyde.compiler.AST.MainNode;
import slyde.compiler.AST.MethodCallNode;
import slyde.compiler.AST.NumberNode;
import slyde.compiler.AST.StringNode;
import slyde.compiler.AST.ProgramNode;
import slyde.compiler.AST.VarDeclNode;
import slyde.compiler.LP.SlydeParser.ArgListContext;
import slyde.compiler.LP.SlydeParser.AssignmentContext;
import slyde.compiler.LP.SlydeParser.BlockContext;
import slyde.compiler.LP.SlydeParser.ClassBodyContext;
import slyde.compiler.LP.SlydeParser.ClassDeclarationContext;
import slyde.compiler.LP.SlydeParser.ConstructorContext;
import slyde.compiler.LP.SlydeParser.ExprContext;
import slyde.compiler.LP.SlydeParser.IfStmtContext;
import slyde.compiler.LP.SlydeParser.MainContext;
import slyde.compiler.LP.SlydeParser.MethodCallContext;
import slyde.compiler.LP.SlydeParser.ParamListContext;
import slyde.compiler.LP.SlydeParser.StatementContext;
import slyde.compiler.LP.SlydeParser.VarDeclContext;

import java.util.ArrayList;
import java.util.List;

public class ASTGenerator {

    public static ProgramNode generateAST(ParseTree tree) {
        List<ClassNode> classNodes = new ArrayList<>();


        for (int i = 0; i < tree.getChildCount(); i++) {
            ParseTree classDeclTree = tree.getChild(i);
            if (classDeclTree instanceof ClassDeclarationContext) {
                classNodes.add(createClassNode((ClassDeclarationContext) classDeclTree));
            }
        }

        return new ProgramNode(classNodes);
    }

    public static ClassNode createClassNode(ClassDeclarationContext ctx){
        List<TerminalNode> TNL = ctx.IDENTIFIER();
        String name = TNL.get(0).getText();

        List<ASTNode> body = new ArrayList<>();

        body = createClassBodyNode(ctx.classBody());


        return new ClassNode(name, body);
    }


    public static List<ASTNode> createClassBodyNode(ClassBodyContext ctx){
        List<ASTNode> body = new ArrayList<>();
        for (int i =0; i < ctx.getChildCount(); i++){
           body.add(createASTNode(ctx.getChild(i)));
        }
        return body;
    }

    public static ASTNode createASTNode(ParseTree tree){

        if (tree instanceof VarDeclContext){
            return createVarDeclNode((VarDeclContext) tree);
        } else if (tree instanceof TerminalNode) {
            return createTerminalNode((TerminalNode) tree);
        } else if (tree instanceof ConstructorContext){
            return createConstructorNode((ConstructorContext) tree);
        } else if (tree instanceof ExprContext){
            return createExprNode((ExprContext) tree);
        } else if (tree instanceof MainContext){
            return createMainNode((MainContext) tree);
        } else if (tree instanceof StatementContext){
            return createASTNode(tree.getChild(0));
        } else if (tree instanceof IfStmtContext){
            return createIfNode((IfStmtContext) tree);
        } else if (tree instanceof AssignmentContext) {
            return createAssignmentNode((AssignmentContext) tree);
        } else {
            System.out.println(tree.toStringTree());
        }
        return null;
    }

    public static AssignmentNode createAssignmentNode(AssignmentContext ctx){
        String name = ctx.IDENTIFIER().getText();
        int exprSize = ctx.expr().size();
        ASTNode value = createASTNode(ctx.expr(exprSize - 1));

        return new AssignmentNode(name, value);
    }

    public static IfNode createIfNode(IfStmtContext ctx){
        Expr condition = createExprNode(ctx.expr());

        BlockNode trueBranch = createBlockNode(ctx.block(0));

        BlockNode falseBranch = null;
        if (ctx.ELSE() != null){
            falseBranch = createBlockNode(ctx.block(1));
        }

        return new IfNode(condition, trueBranch, falseBranch);
    }

    public static MainNode createMainNode(MainContext ctx){
        
        List<VarDeclNode> params = null;
        if (ctx.paramList() != null){
            params = createParamsListNode(ctx.paramList());
        }
        BlockNode body = createBlockNode(ctx.block());

        return new MainNode(params, body);
    }

    public static Expr createExprNode(ExprContext ctx){
        Expr left = null;

        if(ctx.expr(0) != null){
            left = createExprNode(ctx.expr(0));
        } else if (ctx.NUMBER() != null){
            return (Expr) createTerminalNode(ctx.NUMBER());
        } else if (ctx.STRING() != null) {
            return (Expr) createTerminalNode(ctx.STRING());
        } else if (ctx.BOOLEAN() != null) {
            return (Expr) createTerminalNode(ctx.BOOLEAN());
        } else if (ctx.methodCall() != null){
            return createMethodCallNode(ctx.methodCall());
        } else if (ctx.IDENTIFIER() != null) {
            return (Expr) createTerminalNode(ctx.IDENTIFIER());
        }

        if (ctx.expr(1) != null){
            String operator = ctx.getChild(1).getText();
            Expr right = createExprNode(ctx.expr(1));

            if(ctx.binOp() != null){
                return new BinaryOpNode(left, operator, right);
            } else if (ctx.compareOp() != null) {
                return new ConditionalOp(left, right, operator);
            }
        }



        return left;
    }

    public static MethodCallNode createMethodCallNode(MethodCallContext ctx){
        List<ASTNode> params = createArgsListNode(ctx.argList());
        String name = ctx.IDENTIFIER().getText();

        return new MethodCallNode(name, params);
    }

    public static List<ASTNode> createArgsListNode(ArgListContext ctx){
        List<ASTNode> args = new ArrayList<>();
        for (int i = 0; i < ctx.expr().size(); i++){
            args.add(createExprNode(ctx.expr(i)));
        }
        return args;
    }

    public static BlockNode createBlockNode(BlockContext ctx){
        List<ASTNode> statments = new ArrayList<>();
        for (int i = 0; i < ctx.statement().size(); i++){
            statments.add(createASTNode(ctx.statement(i)));
        }

        return new BlockNode(statments);

    }

    public static List<VarDeclNode> createParamsListNode(ParamListContext ctx){
        List<VarDeclNode> params = new ArrayList<>();

        for (int i = 0; i < ctx.type().size(); i++){
            params.add(new VarDeclNode(ctx.type(i).getText(), ctx.IDENTIFIER(i).getText(), null));
        }

        return params;
    }

    public static ConstructorNode createConstructorNode(ConstructorContext ctx){
        List<VarDeclNode> params = createParamsListNode(ctx.paramList());
        BlockNode body = createBlockNode(ctx.block());

        return new ConstructorNode(params, body);
        
    }

    public static boolean isBool(String text){
        return text.equals("yes") || text.equals("no") || text.equals("true") || text.equals("false");
    }

    public static ASTNode createTerminalNode(TerminalNode ctx) {
        String text = ctx.getText();
        if (text.matches("\\d+")){
            return new NumberNode(Integer.parseInt(text));
        } else if (isBool(text)){
            boolean value = text.equals("yes") || text.equals("true") || text.equals("on");
            return new BooleanNode(value);
        } else {
            System.out.println(ctx.getText());
        }
        return new StringNode(text);
    }

    public static VarDeclNode createVarDeclNode(VarDeclContext ctx){
        String type = ctx.getChild(0).getText();

        String name = ctx.IDENTIFIER().getText();

        ExprContext expr = ctx.expr();

        ASTNode value = null;

        if (expr != null){
            value = createASTNode(expr);
        }

        return new VarDeclNode(type, name, value);
        
    }

    public static void printProg(ProgramNode node){
        System.out.println(node.toString(new Indent()));
    }

    public static void printProg(ProgramNode node, String IndentType){
        System.out.println(node.toString(new Indent(IndentType)));
    }

}
