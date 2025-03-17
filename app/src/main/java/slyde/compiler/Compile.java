package slyde.compiler;

import static org.bytedeco.llvm.global.LLVM.*;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import slyde.compiler.AST.ProgramNode;
import slyde.compiler.LP.SlydeLexer;
import slyde.compiler.LP.SlydeParser;

public class Compile {
    

    public static void main(String[] args) {
        try {
            CharStream input = CharStreams.fromFileName("test.sly");

            SlydeLexer lexer = new SlydeLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SlydeParser parser = new SlydeParser(tokens);
            ParseTree tree = parser.prog();

            ProgramNode prog = ASTGenerator.generateAST(tree);



            LLVMGenerator generator =  new LLVMGenerator();

            generator.generateLLVM(prog);

            


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
