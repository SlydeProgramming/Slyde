package slyde.generation;

import slyde.structure.AST.BinaryOpNode;
import slyde.structure.AST.Expr;
import slyde.structure.AST.NumberNode;

public class Optimize {

    public static Expr attemptCalc(BinaryOpNode node) {
        Expr left = node.left;
        Expr right = node.right;

        // Try to reduce left and right expressions to numbers if possible
        Integer leftVal = null;
        Integer rightVal = null;

        if (left instanceof NumberNode) {
            leftVal = ((NumberNode) left).value;
        } else if (left instanceof BinaryOpNode) {
            leftVal = attemptTCalc((BinaryOpNode) left);
            if (leftVal != null) {
                left = new NumberNode(leftVal);
            }
        }

        if (right instanceof NumberNode) {
            rightVal = ((NumberNode) right).value;
        } else if (right instanceof BinaryOpNode) {
            rightVal = attemptTCalc((BinaryOpNode) right);
            if (rightVal != null) {
                right = new NumberNode(rightVal);
            }
        }

        if (leftVal != null && rightVal != null) {
            return evaluateBinaryOp(leftVal, node.operator, rightVal);
        }

        return new BinaryOpNode(left, node.operator, right);
    }

    private static NumberNode evaluateBinaryOp(int left, String operator, int right) {
        switch (operator) {
            case "+":
                return new NumberNode(left + right);
            case "-":
                return new NumberNode(left - right);
            case "*":
                return new NumberNode(left * right);
            case "/":
                return new NumberNode(left / right);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    public static Integer attemptTCalc(BinaryOpNode node) {
        Integer leftVal = null;
        Integer rightVal = null;

        // Try to resolve the left side
        if (node.left instanceof NumberNode) {
            leftVal = ((NumberNode) node.left).value;
        } else if (node.left instanceof BinaryOpNode) {
            leftVal = attemptTCalc((BinaryOpNode) node.left);
        }

        // Try to resolve the right side
        if (node.right instanceof NumberNode) {
            rightVal = ((NumberNode) node.right).value;
        } else if (node.right instanceof BinaryOpNode) {
            rightVal = attemptTCalc((BinaryOpNode) node.right);
        }

        // If both sides are successfully resolved, evaluate
        if (leftVal != null && rightVal != null) {
            switch (node.operator) {
                case "+":
                    return leftVal + rightVal;
                case "-":
                    return leftVal - rightVal;
                case "*":
                    return leftVal * rightVal;
                case "/":
                    if (rightVal == 0)
                        return null; // Prevent division by zero
                    return leftVal / rightVal;
                default:
                    System.err.println("Unknown operator: " + node.operator);
                    return null;
            }
        }

        // Not fully constant — can't evaluate
        return null;
    }

}
