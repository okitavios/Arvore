public class Benchmark {

    private static final long SEED = 12345L;
    private static final int NUM_RULES = 100000;


    public static void main(String[] args) {
        PacketRule[] rules = generateRules();

        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();
    
    }

    private static long calculateTimeAVL(AVLRouterTree avl, PacketRule[] rules) {
        long  insertionInitial = System.nanoTime();
        

        for (PacketRule rule : rules){
            avl.insert(rule);
        }

        long  insertionFinal = System.nanoTime();
        long  insertionTime = insertionFinal - insertionInitial;

        return InsertionTime;

    }

    private static long calculateTimeRbt(RedBlackRouterTree rbt, PacketRule[] rules) {
        long  insertionInitial = System.nanoTime();
        

        for (PacketRule rule : rules){
            rbt.insert(rule);
        }

        long  insertionFinal = System.nanoTime();
        long  insertionTime = insertionFinal - insertionInitial;

        return insertionTime;

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

 
       