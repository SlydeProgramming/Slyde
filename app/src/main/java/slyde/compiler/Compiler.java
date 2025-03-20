package slyde.compiler;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import slyde.compiler.AST.ProgramNode;
import slyde.compiler.LP.SlydeLexer;
import slyde.compiler.LP.SlydeParser;

public class Compiler {
    
    public static void compile(String path, String outPath){
        try {
            CharStream input = CharStreams.fromFileName(path);

            SlydeLexer lexer = new SlydeLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SlydeParser parser = new SlydeParser(tokens);
            ParseTree tree = parser.prog();

            ProgramNode prog = ASTGenerator.generateAST(tree);

            ASTGenerator.printProg(prog);

            // LLVMGenerator generator =  new LLVMGenerator();

            // generator.generateLLVM(prog);

            // generator.dump(outPath);




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        compile("test.sly", "slyde_module.ll");
    }
    
}
