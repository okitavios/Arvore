public class Benchmark {

    private static final long SEED = 12345L;
    private static final int NUM_RULES = 100000;


    public static void main(String[] args) {
        PacketRule[] rules = generateRules();

        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();


        long  insertionInitialRbt = System.nanoTime();
        

        for (PacketRule regras : rules){
            rbt.insert(regras);
        }

        long  insertionFinalRbt = System.nanoTime();
        long  insertionTimeRbt = insertionFinalRbt - insertionInitialRbt;

        


        System.out.println("Tempo consumido para inserção RBT: " + insertionTimeRbt + "ns");

    
    }

    private static long calculateTimeAVL(AVLRouterTree avl, PacketRule[] regras) {
        long  insertionInitialAvl = System.nanoTime();
        

        for (PacketRule rules : regras){
            avl.insert(rules);
        }

        long  insertionFinalAvl = System.nanoTime();
        long  insertionTimeAvl = insertionFinalAvl - insertionInitialAvl;

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

 
       