package redblacktree;

/**
 * Левостороннее красно-черное дерево с автобалансировкой при добавлении
 * элементов.
 */
public class Tree<T extends Comparable<T>> {
    private Node root;

    public boolean add(T value) {
        if (root == null) {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
        boolean result = addNode(root, value);
        root = rebalance(root);
        root.color = Color.BLACK;
        return result;
    }

    // метод добавления нового элемента с балансировкой
    private boolean addNode(Node node, T value) {
        if (node.value.compareTo(value) > 0) {
            if (node.left == null) {
                node.left = new Node();
                node.left.color = Color.RED;
                node.left.value = value;
                return true;
            }
            boolean result = addNode(node.left, value);
            node.left = rebalance(node.left);
            return result;
        } else if (node.value.compareTo(value) < 0) {
            if (node.right == null) {
                node.right = new Node();
                node.right.color = Color.RED;
                node.right.value = value;
                return true;
            }
            boolean result = addNode(node.right, value);
            node.right = rebalance(node.right);
            return result;
        } else {
            return false;
        }
    }

    // ребалансировка
    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.right != null && result.right.color == Color.RED &&
                    (result.left == null || result.left.color == Color.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.left != null && result.left.color == Color.RED &&
                    result.left.left != null && result.left.left.color == Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.left != null && result.left.color == Color.RED &&
                    result.right != null && result.right.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
        } while (needRebalance);
        return result;
    }

    // правосторонний поворот
    private Node rightSwap(Node node) {
        Node right = node.right;
        Node between = right.left;
        right.left = node;
        node.right = between;
        right.color = node.color;
        node.color = Color.RED;
        return right;
    }

    // левосторонний поворот
    private Node leftSwap(Node node) {
        Node left = node.left;
        Node between = left.right;
        left.right = node;
        node.left = between;
        left.color = node.color;
        node.color = Color.RED;
        return left;
    }

    // смена цвета
    private void colorSwap(Node node) {
        node.right.color = Color.BLACK;
        node.left.color = Color.BLACK;
        node.color = Color.RED;
    }

    public boolean contains(T value) {
        Node node = findNode(root, value);
        return node != null;
    }

    // метод поиска элемента в дереве
    private Node findNode(Node node, T value) {
        if (node.value.compareTo(value) > 0) {
            if (node.left == null)
                return null;
            return findNode(node.left, value);
        } else if (node.value.compareTo(value) < 0) {
            if (node.right == null)
                return null;
            return findNode(node.right, value);
        } else {
            return node;
        }
    }

    // Метод проверки сбалансированности дерева. Возвращает высоту левого и правого
    // поддерева.
    public int[] checkBalance() {
        int[] result = { 0, 0 };
        if (root != null) {
            for (Node node = root.left; node != null; result[0]++)
                node = node.left != null ? node.left : node.right;
            for (Node node = root.left; node != null; result[1]++)
                node = node.left != null ? node.left : node.right;
        }
        return result;
    }

    private class Node {
        private T value;
        private Color color;
        private Node left;
        private Node right;
    }

    private enum Color {
        RED, BLACK
    }
}
