package slyde.compiler;

import org.bytedeco.llvm.LLVM.*;
import static org.bytedeco.llvm.global.LLVM.*;

import org.bytedeco.javacpp.PointerPointer;

public class LLVMGenerator {
    public static void main(String[] args) {
        LLVMInitializeNativeTarget();
        LLVMInitializeNativeAsmPrinter();

        LLVMModuleRef module = LLVMModuleCreateWithName("slyde_module");
        LLVMBuilderRef builder = LLVMCreateBuilder();

        // Define Function Type (int myFunction(int x))
        LLVMTypeRef returnType = LLVMInt32Type();
        LLVMTypeRef[] paramTypesArray = {LLVMInt32Type()};
        PointerPointer<LLVMTypeRef> paramTypes = new PointerPointer<>(paramTypesArray);
        LLVMTypeRef functionType = LLVMFunctionType(returnType, paramTypes, 1, 0);
        LLVMValueRef function = LLVMAddFunction(module, "myFunction", functionType);

        // Define Function Body
        LLVMBasicBlockRef entry = LLVMAppendBasicBlock(function, "entry");
        LLVMPositionBuilderAtEnd(builder, entry);

        // Perform Addition x + 5
        LLVMValueRef arg = LLVMGetParam(function, 0);
        LLVMValueRef result = LLVMBuildAdd(builder, arg, LLVMConstInt(LLVMInt32Type(), 5, 0), "sum");

        // Return Value
        LLVMBuildRet(builder, result);
        LLVMDisposeBuilder(builder);

        // Print LLVM IR
        LLVMDumpModule(module);
    }
}
