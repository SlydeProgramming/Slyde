package slyde.compiler;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import slyde.compiler.AST.ASTNode;
import slyde.compiler.AST.BlockNode;
import slyde.compiler.AST.BooleanNode;
import slyde.compiler.AST.ClassNode;
import slyde.compiler.AST.ConstructorNode;
import slyde.compiler.AST.NumberNode;
import slyde.compiler.AST.StringNode;
import slyde.compiler.AST.ProgramNode;
import slyde.compiler.AST.VarDeclNode;
import slyde.compiler.LP.SlydeParser.BlockContext;
import slyde.compiler.LP.SlydeParser.ClassBodyContext;
import slyde.compiler.LP.SlydeParser.ClassDeclarationContext;
import slyde.compiler.LP.SlydeParser.ConstructorContext;
import slyde.compiler.LP.SlydeParser.ExprContext;
import slyde.compiler.LP.SlydeParser.ParamListContext;
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
        }
        return null;
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

}
