package slyde.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import slyde.structure.AST.*;
import slyde.utils.MetaDataString;
import slyde.utils.MetaDataString.Associate;

public class LLVMGeneratorVersionTwo {

    private static MultiPartTextGenerator codemanager = new MultiPartTextGenerator();

    private static void createPreDefinedMethods() {
        codemanager.appendHead("declare i32 @puts(i8*)\n");
    }

    private static void generateClasses(List<ClassNode> nodes) {

        for (ClassNode clas : nodes) {
            // Defines class
            String classIdentifier = clas.name;
            codemanager.addCommentHead(classIdentifier);
            codemanager.appendHead("%" + classIdentifier + " = type {");

            // class field content
            clas.getFields().forEach((field) -> {
                codemanager.appendHead(MultiPartTextGenerator.getLLVMType(field.name));
                codemanager.appendHead(",");
            });

            codemanager.removeLastCharHead();
            codemanager.appendHead("}");

            // Class body (methods)
            codemanager.addComment("============== " + classIdentifier + " ===============");

            List<MethodNode> methods = clas
                    .getMethods();
            List<VarDeclNode> fields = clas.getFields();

            // loop thorugh all methods and generate
            for (MethodNode node : methods) {
                List<String> params = new ArrayList<>();

                params.add("%" + classIdentifier + "* %this");

                for (VarDeclNode param : node.params) {
                    String llvmType = MultiPartTextGenerator.getLLVMType(param.type);
                    params.add(llvmType + " %" + param.name);
                }

                // Add method header (start method definition)
                codemanager.addMethodHeader(classIdentifier + "_" + node.name,
                        MultiPartTextGenerator.getLLVMType(node.returnType),
                        params);

                Map<String, Object> val = new HashMap<>();
                val.put("methods", node.name);
                Associate hp = new Associate(val);

                // Create a context object to pass around for code generation
                MetaDataString<ClassNode> context = new MetaDataString<ClassNode>()
                        .setObj(clas)
                        .addInfo("hp", hp)
                        .setValue(node.name)
                        .setStringManager(codemanager);

                // load all of the fields so they can be accsessed in the body
                codemanager.methodBodySetup(fields, classIdentifier);

                // Generate code for each statement in the method body
                generateNodesArray(node.body.statements, context);

                // Close the method
                codemanager.down();
                codemanager.append("}\n");
            }

            ConstructorNode construct = clas.getConstructor();

            List<String> params = new ArrayList<>();

            params.add("%" + classIdentifier + "* %this");

            for (VarDeclNode param : construct.params) {
                String llvmType = MultiPartTextGenerator.getLLVMType(param.type);
                params.add(llvmType + " %" + param.name);
            }

            // Add method header (start method definition)
            codemanager.addMethodHeader(classIdentifier + "_constructor",
                    MultiPartTextGenerator.getLLVMType("void"),
                    params);

            // Create a context object to pass around for code generation
            MetaDataString<ClassNode> context = new MetaDataString<ClassNode>()
                    .setObj(clas)
                    .addInfo("hp", new Associate("constructor"))
                    .setStringManager(codemanager);

            // load all of the fields so they can be accsessed in the body
            codemanager.methodBodySetup(fields, classIdentifier);

            // Generate code for each statement in the method body
            generateNodesArray(construct.body.statements, context);

            // Close the method
            codemanager.down();
            codemanager.append("}\n");

        }

    }

    private static <T> void generateNodesArray(List<ASTNode> nodes, MetaDataString<T> context) {
        for (ASTNode node : nodes) {
            node.gen(context);
        }
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
