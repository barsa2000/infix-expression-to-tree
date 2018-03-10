package espressioni.grafo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class InfixPostfixConverter {

    private boolean validate(ArrayList<String> tokens) {
        int par = 0;
        boolean wasOp = true;
        for (String s : tokens) {
            if (s.equals("(") && wasOp) {
                ++par;
            } else if (s.equals(")") && !wasOp) {
                --par;
            } else if (MyUtils.isOperator(s) && !wasOp) {
                wasOp = true;
            } else if (MyUtils.isDouble(s)) {
                wasOp = false;
            } else {
                return false;
            }
        }
        return par == 0 && !wasOp;
    }

    private ArrayList<String> tokenize(String s) {
        return new ArrayList<>(Arrays.asList(s.split("((?<=[\\(\\)\\+\\-\\*\\/\\^\\%])|(?=[\\(\\)\\+\\-\\*\\/\\^\\%]))")));
    }

    public ArrayList<String> convert(String s) {
        return convert(tokenize(s.replaceAll(" ", "")));
    }

    private ArrayList<String> convert(ArrayList<String> tokens) {

        if (!validate(tokens)) {
            return null;
        }

        //Shunting-yard algorithm     
        Stack<String> operator = new Stack<>();
        ArrayList<String> output = new ArrayList<>();

        for (int i = 0; i < tokens.size(); ++i) {
            String token = tokens.get(i);
            if (token.equals("(")) {
                operator.push(token);

            } else if (token.equals(")")) {
                while (!operator.peek().equals("(")) {
                    output.add(operator.pop());
                }
                operator.pop();

            } else if (MyUtils.operators.containsKey(token)) {
                while (!operator.empty()
                        && !operator.peek().equals("(")
                        && ((MyUtils.operators.get(token).getPriority() < MyUtils.operators.get(operator.peek()).getPriority())
                            || (MyUtils.operators.get(token).getPriority() == MyUtils.operators.get(operator.peek()).getPriority() 
                                && MyUtils.operators.get(token).isAssociativityLeft()))) {
                    output.add(operator.pop());
                }
                operator.push(token);

            } else {
                output.add(token);

            }
        }
        while (!operator.empty()) {
            output.add(operator.pop());
        }
        return output;
    }

}
