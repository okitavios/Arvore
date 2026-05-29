import java.util.Random;

public class Benchmark {

    private static final long SEED = 12345L;
    private static final int NUM_RULES = 100000;


    public static void main(String[] args) throws Exception{

        //   Teste com Seed
        PacketRule[] ruleseed = generateRulesSeed();
        PacketRule[] toRemoveSeed = get20Percent(ruleseed);

        AVLRouterTree avl1 = new AVLRouterTree();
        RedBlackRouterTree rbt1 = new RedBlackRouterTree();

        long timeAVL = calculateTimeAVL(avl1, ruleseed);
        long timeRBT = calculateTimeRbt(rbt1, ruleseed);

        long StAVL = calculateStAVL(avl1, ruleseed);
        long StRBT = calculateStRbt(rbt1, ruleseed);

        long RvRbt = calculateRvRbt(rbt1, toRemoveSeed);
        long RvAvl = calculateRvAvl(avl1, toRemoveSeed);

    



       System.out.println("Tempo consumido pela busca AVL:" + StAVL + "ns");
        System.out.println("Tempo consumido pela busca RBT:" + StRBT + "ns");

        System.out.println("");

        System.out.println("Tempo consumido pela inserção AVL:" + timeAVL + "ns");
        System.out.println("Tempo consumido pela inserção RBT:" + timeRBT + "ns");
        
        System.out.println("");


        System.out.println("Tempo consumido pela remoção de 20% dos nós AVL:" + RvAvl + "ns");
        System.out.println("Tempo consumido pela remoção de 20% dos nós RBT:" + RvRbt + "ns");



        //   Teste sem Seed
        int[] volumes = {10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000};
        PacketRule[] ruless = generateRules();

        java.io.PrintWriter writer = new java.io.PrintWriter("resultados.csv");
        writer.println("volume,insercao_avl,insercao_rbt,busca_avl,busca_rbt,remocao_avl,remocao_rbt");

        for (int vol : volumes) {
        PacketRule[] rules = java.util.Arrays.copyOfRange(ruless, 0, vol);


        PacketRule[] toRemove = get20Percent(rules);

        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();

        long tAVL = calculateTimeAVL(avl, rules);
        long tRBT = calculateTimeRbt(rbt, rules);
        long sAVL = calculateStAVL(avl, rules);
        long sRBT = calculateStRbt(rbt, rules);
        long rAVL = calculateRvAvl(avl, toRemove);
        long rRBT = calculateRvRbt(rbt, toRemove);

        writer.println(vol + "," + tAVL + "," + tRBT + "," + sAVL + "," + sRBT + "," + rAVL + "," + rRBT);
        System.out.println("Volume " + vol + " concluído.");

    }

    writer.close();
    System.out.println("Resultados salvos em resultados.csv");









    
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
            PacketRule[] ruless = new PacketRule[NUM_RULES];

            for (int i = 0; i < NUM_RULES; i++){
                int id = i + 1;
                String ipOrigem = "10.0.0." + (id % 255);
                String ipDestino = "172.16.0." + (id % 255);
                int prioridade = id;

                ruless[i] = new PacketRule(id, ipOrigem, ipDestino, prioridade);
            }
  
            return ruless;
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

 
       