package slyde.compiler;

import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.llvm.LLVM.*;

import slyde.compiler.AST.ASTNode;
import slyde.compiler.AST.BooleanNode;
import slyde.compiler.AST.ClassNode;
import slyde.compiler.AST.LiteralNode;
import slyde.compiler.AST.NumberNode;
import slyde.compiler.AST.ProgramNode;
import slyde.compiler.AST.StringNode;
import slyde.compiler.AST.VarDeclNode;

import static org.bytedeco.llvm.global.LLVM.*;

import java.util.ArrayList;
import java.util.List;


public class LLVMGenerator {

    public LLVMContextRef context;
    public LLVMModuleRef module;
    public LLVMBuilderRef builder;

    public LLVMGenerator() {
        LLVMInitializeNativeTarget();
        LLVMInitializeNativeAsmPrinter();

        context = LLVMContextCreate();
        module = LLVMModuleCreateWithName("slyde_module");
        builder = LLVMCreateBuilderInContext(context);
    }

    public void generateLLVM(ProgramNode prog){
        List<ClassNode> classes = prog.classes;
        for (int i = 0; i < classes.size(); i++){
            ClassNode classNode = classes.get(i);
            List<LLVMTypeRef> fields = new ArrayList<>();

            for (int j = 0; j < classNode.body.size(); j++){
                ASTNode node =  classNode.body.get(j);
                

                if(node instanceof VarDeclNode){
                    
                    VarDeclNode varNode = (VarDeclNode) node;
                    LLVMTypeRef type = getLLVMType(varNode.type);
                    fields.add(type);



                }

            }

            PointerPointer<LLVMTypeRef> fieldsPtr = new PointerPointer<>(fields.size());
                for (int k = 0; k < fields.size(); k++) {
                    fieldsPtr.put(k, fields.get(k));
                }

            LLVMTypeRef struct = LLVMStructTypeInContext(context, fieldsPtr, fields.size(), 0);
            LLVMValueRef globalStruct = LLVMAddGlobal(module, struct, "global_struct_" + classNode.name);
            LLVMSetLinkage(globalStruct, LLVMPrivateLinkage);
            LLVMSetInitializer(globalStruct, LLVMConstNull(struct));
        }

        String str = LLVMPrintModuleToString(module).getString();
        System.out.println(str);

    }

    private LLVMTypeRef getLLVMType(String slydeType) {
        switch (slydeType) {
            case "int":
                return LLVMInt32Type();
            case "float":
                return LLVMFloatType();
            case "String":
                return LLVMPointerType(LLVMInt8Type(), 0);
            case "boolean":
                return LLVMInt1Type();
            case "void":
                return LLVMVoidType();
            default:
                return LLVMVoidType();
        }
    }

    private LLVMValueRef getVarValue(ASTNode valueNode) {
        if(valueNode instanceof LiteralNode){
            if (valueNode instanceof NumberNode){
                return LLVMConstInt(LLVMInt32Type(), ((NumberNode) valueNode).value, 1);
            } else if (valueNode instanceof BooleanNode){
                return LLVMConstInt(LLVMInt1Type(), ((BooleanNode) valueNode).value ? 1 : 0, 0);
            } else if (valueNode instanceof StringNode){
                return LLVMConstString(((StringNode) valueNode).value, ((StringNode) valueNode).value.length(), 1);
            }
            
        }
        return null;
    }


}
