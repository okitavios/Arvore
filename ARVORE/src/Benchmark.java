public class Benchmark {

    private static final long SEED = 12345L;
    private static final int NUM_REGRAS = 100000;


    public static void main(String[] args) {
        public static void main(String[] args) {
        PacketRule[] regras = generateRules();

        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();


        long  insertionInitialAvl = System.nanoTime();
        

        for (PacketRule regra : regras){
            avl.insert(regra);
        }

        long  insertionFinalAvl = System.nanoTime();
        long  insertionTimeAvl = insertionFinalAvl - insertionInitialAvl;


        System.out.println("Tempo consumido para inserção: " + insertionTimeAvl)

    }
    }

    private static PacketRule[] generateRules(){
            PacketRule[] rules = new PacketRule[NUM_REGRAS];

            for (int i = 0; i < NUM_REGRAS; i++){
                int id = i + 1;
                String ipOrigem = "10.0.0." + (id % 255);
                String ipDestino = "172.16.0." + (id % 255);
                int prioridade = id;

                regras[i] = new PacketRule(id, ipOrigem, ipDestino, prioridade);
            }
  
            return rules;
        }
}

 
       