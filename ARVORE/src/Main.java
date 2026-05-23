

public class Main {

    public static void main(String[] args) {

        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();

        PacketRule r1 = new PacketRule(1, "A", "B", 50);
        PacketRule r2 = new PacketRule(2, "A", "B", 70);
        PacketRule r3 = new PacketRule(3, "A", "B", 30);

        avl.insert(r1);
        avl.insert(r2);
        avl.insert(r3);

        rbt.insert(r1);
        rbt.insert(r2);
        rbt.insert(r3);

        System.out.println("Busca AVL: " + avl.search(r2));
        System.out.println("Busca RBT: " + rbt.search(r2));

        System.out.println();

        System.out.println("Rotações AVL: " + avl.getContadorRotacoes());
        System.out.println("Rotações RBT: " + rbt.getContadorRotacoes());

        System.out.println();

        System.out.println("Elementos AVL em ordem:");
        avl.inorder();
    }
}