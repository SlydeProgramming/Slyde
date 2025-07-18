package slyde.compiler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import slyde.compiler.LP.SlydeLexer;
import slyde.compiler.LP.SlydeParser;
import slyde.compiler.LP.SlydeParser.ProgContext;
import slyde.generation.ASTGenerator;
import slyde.generation.LLVMGeneratorVersionTwo;
import slyde.structure.AST.ProgramNode;
import slyde.utils.ErrorHandler;
import slyde.utils.FileHandler;

public class Compiler {

    public static void compile(String path, String outPath) throws IOException {
        try {
            PrintStream fileOut = new PrintStream(
                    new FileOutputStream(Paths.get("logs.txt").toAbsolutePath().toString()));

            System.setErr(fileOut);
            CharStream input = CharStreams.fromFileName(path);

            SlydeLexer lexer = new SlydeLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SlydeParser parser = new SlydeParser(tokens);
            ProgContext tree = parser.prog();

            ProgramNode prog = ASTGenerator.generateAST(tree);

            FileHandler.writeFile(outPath, LLVMGeneratorVersionTwo.generate(prog));
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                String out = FileHandler.readFile(Paths.get("logs.txt").toAbsolutePath().toString());
                int line = Integer.parseInt("" + out.charAt(5));
                ErrorHandler.error("Missing ;", line - 1);
            } else {
                e.printStackTrace();
            }

        }

    }

    public static void compile(String path, String outPath, String exe) {
        try {
            CharStream input = CharStreams.fromFileName(path);

            SlydeLexer lexer = new SlydeLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SlydeParser parser = new SlydeParser(tokens);
            ProgContext tree = parser.prog();

            ProgramNode prog = ASTGenerator.generateAST(tree);

            FileHandler.writeFile(outPath, LLVMGeneratorVersionTwo.generate(prog));

            FileHandler.exec("./clang " + outPath + " -o " + exe);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
