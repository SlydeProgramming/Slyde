package slyde.compiler;

import java.util.List;

public class AST {
    abstract class ASTNode {}
    class ClassDeclaration extends ASTNode {
        String name;
        List<ASTNode> members;

        public ClassDeclaration(String name, List<ASTNode> members) {
            this.name = name;
            this.members = members;
        }
    }

    class MethodDeclaration extends ASTNode {
        String returnType;
        String name;
        List<String> parameters;
        List<ASTNode> body;

        public MethodDeclaration(String returnType, String name, List<String> parameters, List<ASTNode> body) {
            this.returnType = returnType;
            this.name = name;
            this.parameters = parameters;
            this.body = body;
        }
    }

    class VariableDeclaration extends ASTNode {
        String type;
        String name;
        String value;

        public VariableDeclaration(String type, String name, String value) {
            this.type = type;
            this.name = name;
            this.value = value;
        }
    }


}
