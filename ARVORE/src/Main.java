

public class Main {

    public static void main(String[] args) {
        PacketRule[] regras = generateRules();

        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();


        long  insertionInitialAvl = System.nanoTime();
        long  insertionFinalAvl = System.nanoTime();
        long  insertionTimeAvl = System.nanoTime();

        for (PacketRule regra : regras){
            avl.insert(regra);
        }




    }
}