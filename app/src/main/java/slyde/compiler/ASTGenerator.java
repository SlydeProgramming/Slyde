package slyde.compiler;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import slyde.compiler.AST.ASTNode;
import slyde.compiler.AST.BooleanNode;
import slyde.compiler.AST.ClassNode;
import slyde.compiler.AST.NumberNode;
import slyde.compiler.AST.StringNode;
import slyde.compiler.AST.ProgramNode;
import slyde.compiler.AST.VarDeclNode;
import slyde.compiler.LP.SlydeParser.ClassDeclarationContext;
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

        int childCount = ctx.getChildCount();


        body.add(createASTNode(ctx.getChild(childCount-2)));


        return new ClassNode(name, body);
    }

    public static ASTNode createASTNode(ParseTree tree){

        if (tree.getChild(0) instanceof VarDeclContext){
            return createVarDeclNode((VarDeclContext) tree);
        } else if (tree.getChild(0) instanceof TerminalNode) {
            return createTerminalNode((TerminalNode) tree);
        }
        return null;
    }

    // public static boolean isNumber(){

    // }

    public static ASTNode createTerminalNode(TerminalNode ctx) {
        String text = ctx.getText();
        if (text.matches("\\d+")){
            System.out.println(ctx.getText());
            return new NumberNode(Integer.parseInt(text));
        } else if (text.equals("yes") || text.equals("no") || text.equals("true") || text.equals("false")){
            boolean value = text.equals("yes") || text.equals("true");
            return new BooleanNode(value);
        } else {
            System.out.println(ctx.getText());
        }
        return new StringNode(text);
    }

    public static VarDeclNode createVarDeclNode(VarDeclContext ctx){
        String type = ctx.getChild(0).getText();

        String name = ctx.IDENTIFIER().getText();

        ASTNode value = null;

        int childCount = ctx.getChildCount();

        for (int i =0; i < childCount; i++){
            ParseTree child = ctx.getChild(i);
            if (child instanceof TerminalNode && child.getText().equals("=")){
                i++;
                child = ctx.getChild(i);
                value = createASTNode(child);
            }
        }

        return new VarDeclNode(type, name, value);
        
    }

}
