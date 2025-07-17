package slyde.generation;

import slyde.structure.AST.BinaryOpNode;
import slyde.structure.AST.BooleanNode;
import slyde.structure.AST.ConditionalOp;
import slyde.structure.AST.DoubleNode;
import slyde.structure.AST.Expr;
import slyde.structure.AST.NumberNode;
import slyde.structure.AST.StringNode;
import slyde.utils.ErrorHandler;

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

    public static Expr attemptCalcConditional(ConditionalOp node, int l, int c) {
        // Recursively simplify left and right first
        Expr leftSimplified = simplifyExpr(node.left, l, c);
        Expr rightSimplified = simplifyExpr(node.right, l, c);

        var leftVal = extractValue(leftSimplified);
        var rightVal = extractValue(rightSimplified);

        if (leftVal != null && rightVal != null) {
            // Evaluate condition and return BooleanNode
            Boolean result = evaluateConditionalOp(leftVal, node.operator, rightVal, l, c);
            if (result != null) {
                return new BooleanNode(result);
            }
        }

        // Return simplified conditional if no full evaluation possible
        return new ConditionalOp(leftSimplified, rightSimplified, node.operator);
    }

    private static Expr simplifyExpr(Expr expr, int l, int c) {
        if (expr instanceof ConditionalOp) {
            return attemptCalcConditional((ConditionalOp) expr, l, c);
        } else if (expr instanceof BinaryOpNode) {
            // Use your numeric optimizer to simplify arithmetic inside conditionals
            return Optimize.attemptCalc((BinaryOpNode) expr);
        } else {
            return expr; // No simplification possible
        }
    }

    private static Object extractValue(Expr expr) {
        if (expr instanceof NumberNode) {
            return ((NumberNode) expr).value;
        } else if (expr instanceof StringNode) {
            return ((StringNode) expr).value;
        } else if (expr instanceof DoubleNode) {
            return ((DoubleNode) expr).value;
        } else if (expr instanceof BooleanNode) {
            return ((BooleanNode) expr).value;
        }
        // Optional: could support more literal types here if you want
        return null;
    }

    private static Boolean evaluateConditionalOp(Object left, String operator, Object right, int l, int c) {

        if (left instanceof String || right instanceof String) {
            switch (operator) {
                case "==":
                    return left.equals(right);
                case "!=":
                    return !left.equals(right);
                default:
                    ErrorHandler.error("Invalid operation " + operator + "for values " + left + " and " + right, l, c);
                    return null;
            }
        } else if (left instanceof Integer || left instanceof Double || right instanceof Integer
                || left instanceof Double) {
            double lv = (double) left;
            double r = (double) right;
            switch (operator) {
                case "==":
                    return lv == r;
                case "!=":
                    return lv != r;
                case "<":
                    return lv < r;
                case ">":
                    return lv > r;
                case "<=":
                    return lv <= r;
                case ">=":
                    return lv >= r;
                default:
                    ErrorHandler.error("Invalid operation " + operator + "for values " + left + " and " + right, l, c);
                    return null;
            }
        } else if (left instanceof Boolean || right instanceof Boolean) {
            boolean lv = (boolean) left;
            boolean r = (boolean) right;
            switch (operator) {
                case "==":
                    return lv == r;
                case "!=":
                    return lv != r;
                case "||":
                    return lv || r;
                case "&&":
                    return lv && r;
                default:
                    ErrorHandler.error("Invalid operation " + operator + "for values " + left + " and " + right, l, c);
                    return null;
            }
        }

        ErrorHandler.error("Invalid operation " + operator + "for values " + left + " and " + right, l, c);
        return null;

    }

}
