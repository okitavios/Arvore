public class Benchmark {

    private static final long SEED = 12345L;
    private static final int NUM_RULES = 100000;


    public static void main(String[] args) {
        PacketRule[] rules = generateRules();

        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();


        long timeAVL = calculateTimeAVL(avl, rules);
        long timeRBT = calculateTimeRbt(rbt, rules);


        System.out.println("Tempo consumido pela inserção AVL:" + timeAVL + "ns");
        System.out.println("Tempo consumido pela inserção RBT:" + timeRBT + "ns");
    
    }

    private static long calculateTimeAVL(AVLRouterTree avl, PacketRule[] rules) {
        long  insertionInitial = System.nanoTime();
        

        for (PacketRule rule : rules){
            avl.insert(rule);
        }

        long  insertionFinal = System.nanoTime();
        

        return insertionFinal - insertionInitial;

    }

    private static long calculateTimeRbt(RedBlackRouterTree rbt, PacketRule[] rules) {
        long  insertionInitial = System.nanoTime();
        

        for (PacketRule rule : rules){
            rbt.insert(rule);
        }

        long  insertionFinal = System.nanoTime();
        

        return insertionFinal - insertionInitial;

    }

    private static long calculateStAVL(AVLRouterTree avl, PacketRule[] rules) {
        long  insertionInitial = System.nanoTime();
        

        for (PacketRule rule : rules){
            avl.search(rule);
        }

        long  insertionFinal = System.nanoTime();
        

        return insertionFinal - insertionInitial;

    }

    private static long calculateStRbt(RedBlackRouterTree rbt, PacketRule[] rules) {
        long  insertionInitial = System.nanoTime();
        

        for (PacketRule rule : rules){
            rbt.search(rule);
        }

        long  insertionFinal = System.nanoTime();
        

        return insertionFinal - insertionInitial;

    }

    

    private static PacketRule[] generateRules(){
            PacketRule[] rules = new PacketRule[NUM_RULES];

            for (int i = 0; i < NUM_RULES; i++){
                int id = i + 1;
                String ipOrigem = "10.0.0." + (id % 255);
                String ipDestino = "172.16.0." + (id % 255);
                int prioridade = id;

                rules[i] = new PacketRule(id, ipOrigem, ipDestino, prioridade);
            }
  
            return rules;
        }
}

 
       