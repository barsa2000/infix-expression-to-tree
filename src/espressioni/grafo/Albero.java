package espressioni.grafo;

import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public final class Albero {

    public Albero(ArrayList<String> tokens) {
        insert(tokens);
    }

    public Albero() {
    }

    private Node root = null;

    public double getTotal() {
        return root.getValue();
    }

    public boolean isSolvable() {
        return ((root.getLeft() != null) && (root.getRight() != null));
    }

    public void insert(ArrayList<String> tokens) {
        Stack<Node> s = new Stack<>();

        for (String t : tokens) {
            Node ln, rn;
            double value;
            String text;
            if (MyUtils.isOperator(t)) {
                double r = s.peek().getValue();
                rn = s.pop();
                double l = s.peek().getValue();
                ln = s.pop();
                value = MyUtils.operators.get(t).exec(l, r);
                text = t;
            } else {
                value = Double.parseDouble(t);
                text = Double.toString(value);
                ln = null;
                rn = null;
            }
            s.push(new Node(text, value, ln, rn));
        }
        root = s.pop();
    }

    private void generateTreeView(Node node, TreeItem<String> parent) {
        if (node != null) {
            TreeItem<String> item = new TreeItem<>(node.getText());
            item.setExpanded(true);
            parent.getChildren().add(item);
            generateTreeView(node.getLeft(), item);
            generateTreeView(node.getRight(), item);
        }
    }

    public void print(TreeView treeView) {
        TreeItem<String> rootItem = new TreeItem<>("");
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);

        generateTreeView(root, rootItem);
    }

    public Albero solve() {
        root.setText(Double.toString(root.getValue()));
        root.setLeft(null);
        root.setRight(null);
        return this;
    }

    public Albero solveStep() {
        solveStep(root);
        return this;
    }

    private void solveStep(Node current) {
        if (MyUtils.isOperator(current.getText())) {
            if (MyUtils.isDouble(current.getLeft().getText())) {
                if (MyUtils.isDouble(current.getRight().getText())) {
                    current.setText(Double.toString(current.getValue()));
                    current.setRight(null);
                    current.setLeft(null);
                } else {
                    solveStep(current.getRight());
                }
            } else {
                solveStep(current.getLeft());
                solveStep(current.getRight());
            }
        }
    }

    private class Node {

        private Node left;
        private Node right;
        private String text;
        private final double value;

        public Node(String text, double value, Node l, Node r) {
            this.text = text;
            this.value = value;
            this.left = l;
            this.right = r;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public double getValue() {
            return value;
        }
    }
}
