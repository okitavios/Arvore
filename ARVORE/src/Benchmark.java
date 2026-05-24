public class Benchmark {

    private static final long SEED = 12345L;
    private static final int NUM_REGRAS = 100000;


    public static void main(String[] args) {

        private static PacketRule[] generateRules(){
            PacketRule[] rules = new PacketRule[NUM_REGRAS];

            for (int i = 0; i < NUM_REGRAS; i++){
                int id = i + 1;
                String ipOrigem = "10.0.0." + (id % 255);
                String ipDestino = "172.16.0." + (id % 255);
                int prioridade = id;
            }
  
            return rules;
        }



    
    }
}

 
       