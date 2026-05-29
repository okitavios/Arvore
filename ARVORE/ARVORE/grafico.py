import matplotlib.pyplot as plt
import csv

volumes = []
insercao_avl = []
insercao_rbt = []
busca_avl = []
busca_rbt = []
remocao_avl = []
remocao_rbt = []

with open("resultados.csv") as f:
    reader = csv.DictReader(f)
    for row in reader:
        volumes.append(int(row["volume"]))
        insercao_avl.append(int(row["insercao_avl"]))
        insercao_rbt.append(int(row["insercao_rbt"]))
        busca_avl.append(int(row["busca_avl"]))
        busca_rbt.append(int(row["busca_rbt"]))
        remocao_avl.append(int(row["remocao_avl"]))
        remocao_rbt.append(int(row["remocao_rbt"]))


plt.figure()
plt.plot(volumes, insercao_avl, label="AVL", marker="o")
plt.plot(volumes, insercao_rbt, label="RBT", marker="o")
plt.title("Inserção: AVL vs RBT")
plt.xlabel("Volume de dados")
plt.ylabel("Tempo (ns)")
plt.legend()
plt.savefig("grafico_insercao.png")


plt.figure()
plt.plot(volumes, busca_avl, label="AVL", marker="o")
plt.plot(volumes, busca_rbt, label="RBT", marker="o")
plt.title("Busca: AVL vs RBT")
plt.xlabel("Volume de dados")
plt.ylabel("Tempo (ns)")
plt.legend()
plt.savefig("grafico_busca.png")


plt.figure()
plt.plot(volumes, remocao_avl, label="AVL", marker="o")
plt.plot(volumes, remocao_rbt, label="RBT", marker="o")
plt.title("Remoção 20%: AVL vs RBT")
plt.xlabel("Volume de dados")
plt.ylabel("Tempo (ns)")
plt.legend()
plt.savefig("grafico_remocao.png")

print("Gráficos gerados!")