package slyde.compiler;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import slyde.compiler.LP.SlydeLexer;
import slyde.compiler.LP.SlydeParser;
import slyde.compiler.LP.SlydeParser.ProgContext;
import slyde.generation.ASTGenerator;
import slyde.generation.LLVMGenerator;
import slyde.structure.AST.ProgramNode;
import slyde.utils.FileHandler;

public class Compiler {

    public static void compile(String path, String outPath){
        try {
            CharStream input = CharStreams.fromFileName(path);

            SlydeLexer lexer = new SlydeLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SlydeParser parser = new SlydeParser(tokens);
            ProgContext tree = parser.prog();

            ProgramNode prog = ASTGenerator.generateAST(tree);

            LLVMGenerator generator =  new LLVMGenerator();


            FileHandler.writeFile(outPath, generator.generate(prog));

            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void compile(String path, String outPath, String exe){
        try {
            CharStream input = CharStreams.fromFileName(path);

            SlydeLexer lexer = new SlydeLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SlydeParser parser = new SlydeParser(tokens);
            ProgContext tree = parser.prog();

            ProgramNode prog = ASTGenerator.generateAST(tree);

            LLVMGenerator generator =  new LLVMGenerator();

            // ASTGenerator.printProg(prog);

            FileHandler.writeFile(outPath, generator.generate(prog));

            FileHandler.exec("./clang " + outPath + " -o " + exe);
            


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
