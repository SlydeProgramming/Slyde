package slyde.compiler.LP;
// Generated from ./app/src/main/java/slyde/compiler/LP/Slyde.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SlydeParser}.
 */
public interface SlydeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SlydeParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(SlydeParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(SlydeParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(SlydeParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(SlydeParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(SlydeParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(SlydeParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(SlydeParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(SlydeParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(SlydeParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(SlydeParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SlydeParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SlydeParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#constructor}.
	 * @param ctx the parse tree
	 */
	void enterConstructor(SlydeParser.ConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#constructor}.
	 * @param ctx the parse tree
	 */
	void exitConstructor(SlydeParser.ConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(SlydeParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(SlydeParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(SlydeParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(SlydeParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(SlydeParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(SlydeParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(SlydeParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(SlydeParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(SlydeParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(SlydeParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(SlydeParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(SlydeParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#binOp}.
	 * @param ctx the parse tree
	 */
	void enterBinOp(SlydeParser.BinOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#binOp}.
	 * @param ctx the parse tree
	 */
	void exitBinOp(SlydeParser.BinOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#compareOp}.
	 * @param ctx the parse tree
	 */
	void enterCompareOp(SlydeParser.CompareOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#compareOp}.
	 * @param ctx the parse tree
	 */
	void exitCompareOp(SlydeParser.CompareOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(SlydeParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(SlydeParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(SlydeParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(SlydeParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(SlydeParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(SlydeParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(SlydeParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(SlydeParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(SlydeParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(SlydeParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void enterPrintStmt(SlydeParser.PrintStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#printStmt}.
	 * @param ctx the parse tree
	 */
	void exitPrintStmt(SlydeParser.PrintStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#inputStmt}.
	 * @param ctx the parse tree
	 */
	void enterInputStmt(SlydeParser.InputStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#inputStmt}.
	 * @param ctx the parse tree
	 */
	void exitInputStmt(SlydeParser.InputStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SlydeParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SlydeParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterArrayDeclaration(SlydeParser.ArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#arrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitArrayDeclaration(SlydeParser.ArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SlydeParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void enterArrayLiteral(SlydeParser.ArrayLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SlydeParser#arrayLiteral}.
	 * @param ctx the parse tree
	 */
	void exitArrayLiteral(SlydeParser.ArrayLiteralContext ctx);
}