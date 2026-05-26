import java.util.Random;

public class Benchmark {

    private static final long SEED = 12345L;
    private static final int NUM_RULES = 100000;


    public static void main(String[] args) {
        PacketRule[] ruleseed = generateRulesSeed();
        PacketRule[] toRemove = get20Percent(ruleseed);

        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();

        long StAVL = calculateStAVL(avl, ruleseed);
        long StRBT = calculateStRbt(rbt, ruleseed);

        long timeAVL = calculateTimeAVL(avl, ruleseed);
        long timeRBT = calculateTimeRbt(rbt, ruleseed);

        long RvRbt = calculateRvRbt(rbt, toRemove);
        long RvAvl = calculateRvAvl(avl, toRemove);

    



        /*System.out.println("Tempo consumido pela busca AVL:" + StAVL + "ns");
        System.out.println("Tempo consumido pela busca RBT:" + StRBT + "ns");

        System.out.println("");


        System.out.println("Tempo consumido pela inserção AVL:" + timeAVL + "ns");
        System.out.println("Tempo consumido pela inserção RBT:" + timeRBT + "ns");
        
        System.out.println("");

        System.out.println("Tempo consumido pela remoção AVL:" + RvAvl + "ns");
        System.out.println("Tempo consumido pela remoção RBT:" + RvRbt + "ns");*/







    
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

    private static long calculateRvRbt(RedBlackRouterTree rbt, PacketRule[] rules) {
        long  RemoveInitial = System.nanoTime();
        

        for (PacketRule rule : rules){
            rbt.remove(rule);
        }

        long  RemoveFinal = System.nanoTime();
        

        return RemoveFinal - RemoveInitial;

    }

    private static long calculateRvAvl(AVLRouterTree avl, PacketRule[] rules) {
        long  RemoveInitial = System.nanoTime();
        

        for (PacketRule rule : rules){
            avl.delete(rule);
        }

        long  RemoveFinal = System.nanoTime();
        

        return RemoveFinal - RemoveInitial;

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

    private static PacketRule[] generateRulesSeed() {
            PacketRule[] rules = new PacketRule[NUM_RULES];
            Random random = new Random(SEED);

            for (int i = 0; i < NUM_RULES; i++) {
                int id = i + 1;
                String ipOrigem = "10.0.0." + random.nextInt(255);
                String ipDestino = "172.16.0." + random.nextInt(255);
                int prioridade = random.nextInt(NUM_RULES);

                rules[i] = new PacketRule(id, ipOrigem, ipDestino, prioridade);
            }

            return rules;
        }



        private static PacketRule[] get20Percent(PacketRule[] rules) {
            int size = rules.length / 5;
            PacketRule[] percentset = new PacketRule[size];

            for(int i=0;i < size; i++){
                percentset[i] = rules[i];

            }
            return percentset;

        }
}

 
       