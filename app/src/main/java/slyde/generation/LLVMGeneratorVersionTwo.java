package slyde.generation;

import java.util.ArrayList;
import java.util.List;

import slyde.structure.AST.*;
import slyde.utils.MetaDataString;

public class LLVMGeneratorVersionTwo {

    private static MultiPartTextGenerator codemanager = new MultiPartTextGenerator();


    private static void createPreDefinedMethods() {
        codemanager.appendHead("declare i32 @puts(i8*)\n");
    }

    private static void generateClasses(List<ClassNode> nodes) {

        for (ClassNode clas : nodes) {
            String classIdentifier = clas.name;
            codemanager.addCommentHead(classIdentifier);
            codemanager.appendHead("%" + classIdentifier + " = type {");

            clas.getFields().forEach((field) -> {
                codemanager.appendHead(MultiPartTextGenerator.getLLVMType(field.name));
                codemanager.appendHead(",");
            });

            codemanager.removeLastCharHead();
            codemanager.appendHead("}");

            codemanager.addComment("============== " + classIdentifier + " ===============");

            List<MethodNode> methods = clas
                    .getMethods();

            for (MethodNode node : methods) {

                List<String> params = new ArrayList<>();

                node.params.forEach((param) -> {
                    params.add(MultiPartTextGenerator.getLLVMType(param.type));
                });

                codemanager.addMethodHeader(classIdentifier + "_" + node.name, MultiPartTextGenerator.getLLVMType(node.returnType),
                        null);
            }

        }

    }

    private static <T> void generateNodesArray(List<ASTNode> nodes, MetaDataString<T> context) {
        
    }

    private static void generateMainMethod(MainNode main) {
        codemanager.addMethodHeader("main");

        MetaDataString<MainNode> context = new MetaDataString<MainNode>().setObj(main)
                .setStringManager(codemanager);

        generateNodesArray(main.body.statements, context);

    }

    public static String generate(ProgramNode src) {
        codemanager.addCommentHead("Generated LLVM");
        createPreDefinedMethods();

        generateClasses(src.classes);

        generateMainMethod(src.main);

        return codemanager.toString();
    }
}



