public class TesteLatencia {
    
    static int testesPassaram = 0;
    static int testesFalharam = 0;
    
    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("TESTE DE LATENCIA PARA LOAD BALANCER");
        System.out.println("Medindo tempos em nanossegundos (ns)");
        System.out.println("==========================================================");
        
        testarInsercaoSequencialComTempo();
        testarBuscaComTempo();
        testarCargaMista();
        testarCenarioProblema();
        
        System.out.println("\n==========================================================");
        System.out.println("RESUMO DOS TESTES");
        System.out.println("==========================================================");
        System.out.println("[OK] Testes concluidos: " + testesPassaram);
        System.out.println("[FALHOU] Testes com erro: " + testesFalharam);
        System.out.println("==========================================================");
        
        System.out.println("\n==========================================================");
        System.out.println("RECOMENDACAO PARA O LOAD BALANCER");
        System.out.println("==========================================================");
        System.out.println("Baseado nos testes de latencia em nanossegundos:");
        System.out.println();
        System.out.println("-> Se o sistema faz MAIS BUSCAS que insercoes:");
        System.out.println("   RECOMENDADO: ARVORE AVL");
        System.out.println();
        System.out.println("-> Se o sistema faz MAIS INSERCOES que buscas:");
        System.out.println("   RECOMENDADO: ARVORE RUBRO-NEGRA");
        System.out.println();
        System.out.println("-> Para um Load Balancer tipico (muitas buscas):");
        System.out.println("   VENCEDOR: ARVORE AVL");
        System.out.println("==========================================================");
    }
    
    public static void testarInsercaoSequencialComTempo() {
        System.out.println("\n[TESTE 1] Insercao sequencial - Cenario problema");
        System.out.println("O sistema sofre latencia quando regras sao inseridas em ordem");
        System.out.println("-".repeat(50));
        
        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();
        
        int quantidade = 1000; 
        
        long tempoAVL = 0;
        long tempoRBT = 0;
        
        try {
            long inicio = System.nanoTime();
            for (int i = 1; i <= quantidade; i++) {
                PacketRule p = new PacketRule(i, "192.168.1.1", "10.0.0.1", i);
                avl.insert(p);
            }
            long fim = System.nanoTime();
            tempoAVL = fim - inicio;
            
            inicio = System.nanoTime();
            for (int i = 1; i <= quantidade; i++) {
                PacketRule p = new PacketRule(i, "192.168.1.1", "10.0.0.1", i);
                rbt.insert(p);
            }
            fim = System.nanoTime();
            tempoRBT = fim - inicio;

            System.out.println("   Quantidade de insercoes: " + quantidade);
            System.out.println("   AVL - Tempo total: " + formatarTempo(tempoAVL));
            System.out.println("   AVL - Media por insercao: " + formatarTempo(tempoAVL / quantidade));
            System.out.println("   RBT - Tempo total: " + formatarTempo(tempoRBT));
            System.out.println("   RBT - Media por insercao: " + formatarTempo(tempoRBT / quantidade));
            System.out.println("   Rotacoes AVL: " + avl.getContadorRotacoes());
            System.out.println("   Rotacoes RBT: " + rbt.getContadorRotacoes());
            
            if (tempoRBT < tempoAVL) {
                System.out.println("\n   -> RBT foi " + formatarTempo(tempoAVL - tempoRBT) + " mais rapida");
            } else {
                System.out.println("\n   -> AVL foi " + formatarTempo(tempoRBT - tempoAVL) + " mais rapida");
            }
            
            System.out.println("\n   [OK] TESTE CONCLUIDO");
            testesPassaram++;
            
        } catch (Exception e) {
            System.out.println("   [FALHOU] " + e.getMessage());
            testesFalharam++;
        }
    }
    
    public static void testarBuscaComTempo() {
        System.out.println("\n[TESTE 2] Busca de regras - Operacao critica");
        System.out.println("Load Balancer precisa encontrar regras rapidamente");
        System.out.println("-".repeat(50));
        
        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();
        
        int quantidade = 1000; 
        
        try {
            for (int i = 1; i <= quantidade; i++) {
                PacketRule p = new PacketRule(i, "192.168.1.1", "10.0.0.1", i);
                avl.insert(p);
                rbt.insert(p);
            }
            
            int buscas = 1000;
            long tempoAVL = 0;
            long tempoRBT = 0;
            
            long inicio = System.nanoTime();
            for (int i = 1; i <= buscas; i++) {
                PacketRule p = new PacketRule(i, "192.168.1.1", "10.0.0.1", i);
                avl.search(p);
            }
            long fim = System.nanoTime();
            tempoAVL = fim - inicio;
            
            inicio = System.nanoTime();
            for (int i = 1; i <= buscas; i++) {
                PacketRule p = new PacketRule(i, "192.168.1.1", "10.0.0.1", i);
                rbt.search(p);
            }
            fim = System.nanoTime();
            tempoRBT = fim - inicio;
            
            System.out.println("   Quantidade de buscas: " + buscas);
            System.out.println("   AVL - Tempo total: " + formatarTempo(tempoAVL));
            System.out.println("   AVL - Media por busca: " + formatarTempo(tempoAVL / buscas));
            System.out.println("   RBT - Tempo total: " + formatarTempo(tempoRBT));
            System.out.println("   RBT - Media por busca: " + formatarTempo(tempoRBT / buscas));
            
            if (tempoAVL < tempoRBT) {
                System.out.println("\n   -> AVL e MAIS RAPIDA para buscas (melhor para Load Balancer)");
            } else {
                System.out.println("\n   -> RBT e MAIS RAPIDA para buscas");
            }
            
            System.out.println("\n   [OK] TESTE CONCLUIDO");
            testesPassaram++;
            
        } catch (Exception e) {
            System.out.println("   [FALHOU] " + e.getMessage());
            testesFalharam++;
        }
    }
   
    public static void testarCargaMista() {
        System.out.println("\n[TESTE 3] Carga mista - Simulando operacoes reais");
        System.out.println("Alternando entre insercoes e buscas");
        System.out.println("-".repeat(50));
        
        AVLRouterTree avl = new AVLRouterTree();
        RedBlackRouterTree rbt = new RedBlackRouterTree();
        
        int operacoes = 500;
        long tempoAVL = 0;
        long tempoRBT = 0;
        
        try {
            for (int i = 1; i <= operacoes; i++) {
                PacketRule p = new PacketRule(i, "192.168.1.1", "10.0.0.1", i);
                
                long inicio = System.nanoTime();
                avl.insert(p);
                long fim = System.nanoTime();
                tempoAVL += (fim - inicio);
               
                inicio = System.nanoTime();
                rbt.insert(p);
                fim = System.nanoTime();
                tempoRBT += (fim - inicio);
                
                if (i % 10 == 0) {
                    PacketRule busca = new PacketRule(i / 2, "192.168.1.1", "10.0.0.1", i / 2);
                    
                    inicio = System.nanoTime();
                    avl.search(busca);
                    fim = System.nanoTime();
                    tempoAVL += (fim - inicio);
                    
                    inicio = System.nanoTime();
                    rbt.search(busca);
                    fim = System.nanoTime();
                    tempoRBT += (fim - inicio);
                }
            }
            
            System.out.println("   Operacoes realizadas: " + operacoes + " insercoes + " + (operacoes/10) + " buscas");
            System.out.println("   AVL - Tempo total: " + formatarTempo(tempoAVL));
            System.out.println("   RBT - Tempo total: " + formatarTempo(tempoRBT));
            
            if (tempoAVL < tempoRBT) {
                System.out.println("\n   -> AVL teve MENOR LATENCIA neste cenario");
            } else {
                System.out.println("\n   -> RBT teve MENOR LATENCIA neste cenario");
            }
            
            System.out.println("\n   [OK] TESTE CONCLUIDO");
            testesPassaram++;
            
        } catch (Exception e) {
            System.out.println("   [FALHOU] " + e.getMessage());
            testesFalharam++;
        }
    }
    
    public static void testarCenarioProblema() {
        System.out.println("\n[TESTE 4] Cenario problema - Diferentes volumes de dados");
        System.out.println("Analisando como a latencia cresce com mais regras");
        System.out.println("-".repeat(50));
        
        int[] volumes = {100, 500, 1000, 2000};
        
        System.out.println("\n   Tabela de latencia para insercao sequencial:");
        System.out.println("   " + "-".repeat(58));
        System.out.println("   | Volume   | AVL (ns)      | RBT (ns)      | Vencedor     |");
        System.out.println("   " + "-".repeat(58));
        
        try {
            for (int volume : volumes) {
                AVLRouterTree avl = new AVLRouterTree();
                RedBlackRouterTree rbt = new RedBlackRouterTree();
                
                long tempoAVL = 0;
                long tempoRBT = 0;
                
                long inicio = System.nanoTime();
                for (int i = 1; i <= volume; i++) {
                    PacketRule p = new PacketRule(i, "192.168.1.1", "10.0.0.1", i);
                    avl.insert(p);
                }
                long fim = System.nanoTime();
                tempoAVL = fim - inicio;
                
                inicio = System.nanoTime();
                for (int i = 1; i <= volume; i++) {
                    PacketRule p = new PacketRule(i, "192.168.1.1", "10.0.0.1", i);
                    rbt.insert(p);
                }
                fim = System.nanoTime();
                tempoRBT = fim - inicio;
                
                String vencedor = (tempoRBT < tempoAVL) ? "RBT" : "AVL";
                
                System.out.printf("   | %-8d | %-12d | %-12d | %-10s |\n", 
                                  volume, tempoAVL, tempoRBT, vencedor);
            }
            
            System.out.println("   " + "-".repeat(58));
            System.out.println("\n   [OK] TESTE CONCLUIDO");
            testesPassaram++;
            
        } catch (Exception e) {
            System.out.println("   [FALHOU] " + e.getMessage());
            testesFalharam++;
        }
    }
    
    private static String formatarTempo(long nanosegundos) {
        if (nanosegundos < 1000) {
            return nanosegundos + " ns";
        } else if (nanosegundos < 1000000) {
            return String.format("%.2f us", nanosegundos / 1000.0);
        } else {
            return String.format("%.2f ms", nanosegundos / 1000000.0);
        }
    }
}