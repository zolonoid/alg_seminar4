import redblacktree.Tree;

/**
 * Семинар 4
 */
public class Program {
    private static int[] treeNodes = { 1, 4, 3, 8, 24, 15, 36, 87, 6, 9, 18, 45 };

    public static void main(String[] args) {
        var tree = new Tree<Integer>();
        for (int node : treeNodes)
            tree.add(node);
        System.out.printf("%s\n %s\n", "Создано дерево с кол-вом элентов:", treeNodes.length);
        int[] balance = tree.checkBalance();
        System.out.printf(
                "Проверка сбалансированности дерева:\n высота левого поддерева - %d\n высота правого поддерева - %d",
                balance[0], balance[1]);
    }
}