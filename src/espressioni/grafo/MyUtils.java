package espressioni.grafo;

import java.util.HashMap;
import java.util.Map;

public class MyUtils {

    public static final class Operator {

        private static interface Operation {

            public double exec(double l, double r);
        }

        private Operator(int priority, boolean associativityLeft, Operation op) {
            this.priority = priority;
            this.associativityLeft = associativityLeft;
            this.op = op;
        }

        private final Integer priority;
        private final Boolean associativityLeft;
        private final Operation op;

        public int getPriority() {
            return priority;
        }

        public boolean isAssociativityLeft() {
            return associativityLeft;
        }

        public double exec(double l, double r) {
            return op.exec(l, r);
        }
    }

    public static final Map<String, Operator> operators = new HashMap<>();

    static {
        operators.put("+", new Operator(0, true, (double l, double r) -> l + r));
        operators.put("-", new Operator(0, true, (double l, double r) -> l - r));
        operators.put("*", new Operator(1, true, (double l, double r) -> l * r));
        operators.put("/", new Operator(1, true, (double l, double r) -> l / r));
        operators.put("%", new Operator(1, true, (double l, double r) -> l % r));
        operators.put("^", new Operator(2, false, (double l, double r) -> Math.pow(l, r)));
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOperator(String s) {
        return s.matches("[\\+\\-\\*\\/\\^\\%]");
    }
}
