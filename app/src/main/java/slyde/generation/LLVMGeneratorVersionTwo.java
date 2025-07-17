package slyde.generation;

import java.util.ArrayList;
import java.util.List;

import slyde.context.HandleProtocol;
import slyde.context.Context;
import slyde.structure.AST.*;

public class LLVMGeneratorVersionTwo {

    public static MultiPartTextGenerator codemanager = new MultiPartTextGenerator();

    private static void createPreDefinedMethods() {
        codemanager.appendHead("declare i32 @puts(i8*)\n\n");
        codemanager.appendHead("define void @print(i8* %str) {\n");
        codemanager.appendHead("   call i32 @puts(i8* %str)\n");
        codemanager.appendHead("   ret void\n");
        codemanager.appendHead("}\n\n");
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
            codemanager.appendHead("}\n");

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

                // Create a context object to pass around for code generation
                Context<ClassNode> context = new Context<ClassNode>()
                        .setObj(clas)
                        .setHandleProtocol(HandleProtocol.STANDALONE)
                        .addContextName(classIdentifier)
                        .addContextName(node.name);

                // load all of the fields so they can be accsessed in the body
                codemanager.methodBodySetup(fields, classIdentifier);

                // Generate code for each statement in the method body
                generateNodesArray(node.body.statements, context);

                // Close the method
                codemanager.down();
                codemanager.append("}\n\n");
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
            Context<ClassNode> context = new Context<ClassNode>()
                    .setObj(clas)
                    .setHandleProtocol(HandleProtocol.STANDALONE)
                    .addContextName(classIdentifier)
                    .addContextName("constructor");

            // load all of the fields so they can be accsessed in the body
            int fieldIndex = 0;
            for (VarDeclNode field : fields) {

                String fieldName = field.name;

                // Get pointer to the field inside the struct
                String ptrName = "%ptr_" + fieldName;
                codemanager.append(codemanager.get() +
                        String.format("  %s = getelementptr inbounds %%%s, %%%s* %%this, i32 0, i32 %d\n",
                                ptrName, classIdentifier, classIdentifier, fieldIndex));

                fieldIndex++;
            }

            // set field to default value if set
            for (VarDeclNode field : fields) {
                if (field.value != null) {
                    // Generate code to evaluate the expression
                    context
                            .setHandleProtocol(HandleProtocol.GET)
                            .requestName(context.getContextName() + field.name);
                    field.value.gen(context); // should return a %reg
                    String valueRegister = context.findReturnedName(context.getContextName() + field.name);

                    // Store the result in the field
                    String fieldPtr = "%ptr_" + field.name;
                    codemanager.append(codemanager.get() +
                            "store " + MultiPartTextGenerator.getLLVMType(field.type) + " " + valueRegister + ", " +
                            MultiPartTextGenerator.getLLVMType(field.type) + "* " + fieldPtr);

                    // Load the field value
                    String llvmFieldType = MultiPartTextGenerator.getLLVMType(field.type);
                    String loadedName = "%val_" + field.name;
                    codemanager.append(String.format("  %s = load %s, %s* %s\n",
                            loadedName, llvmFieldType, llvmFieldType, "%ptr_" + field.name));
                }
            }

            // Generate code for each statement in the method body
            generateNodesArray(construct.body.statements, context);

            // Close the method
            codemanager.down();
            codemanager.append("}\n\n");

        }

    }

    private static <T> void generateNodesArray(List<ASTNode> nodes, Context<T> context) {
        for (ASTNode node : nodes) {
            context.setHandleProtocol(HandleProtocol.STANDALONE);
            node.gen(context);
        }
    }

    private static void generateMainMethod(MainNode main) {
        codemanager.addComment("============== Main Method ===============");
        codemanager.addMethodHeader("main");

        Context<MainNode> context = new Context<MainNode>().setObj(main);

        generateNodesArray(main.body.statements, context);

    }

    public static String generate(ProgramNode src) {
        codemanager.addCommentHead("============== Generated LLVM ===============");
        createPreDefinedMethods();

        generateClasses(src.classes);

        generateMainMethod(src.main);

        codemanager.addCommentEnd("============== EOF ===============");

        return codemanager.toString();
    }
}
