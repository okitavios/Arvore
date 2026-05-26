

public class PacketRule implements Comparable<PacketRule> {

    private int id;
    private String ipOrigem;
    private String ipDestino;
    private int prioridade;

    public PacketRule(int id, String ipOrigem, String ipDestino, int prioridade) {
        this.id = id;
        this.ipOrigem = ipOrigem;
        this.ipDestino = ipDestino;
        this.prioridade = prioridade;
    }

    public int getId() {
        return id;
    }

    public String getIpOrigem() {
        return ipOrigem;
    }

    public String getIpDestino() {
        return ipDestino;
    }

    public int getPrioridade() {
        return prioridade;
    }

    @Override
    public int compareTo(PacketRule other) {

        int cmp = Integer.compare(other.prioridade, this.prioridade);

        if (cmp != 0)
            return cmp;

        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ID=" + id + " | Prioridade=" + prioridade;
    }
}
