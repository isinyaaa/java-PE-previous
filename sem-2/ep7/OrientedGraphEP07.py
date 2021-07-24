#!/usr/bin/env python
# coding: utf-8

# Dados para geração da entrada (Note que para que o grafo
# seja fortemente conexo, precisamos de M >= N-1)
N = 10
M = 11
K = 2

# Escolha intervalo de tempos possíveis para as estradas
tempoMin = 1
tempoMax = 10

# Gerando grafo aleatório com N vértices e M arestas
# Para ser possível gerar uma orientação fortemente conexa,
# precisamos que o grau mínimo seja pelo menos 2
g = graphs.RandomGNM(N, M)
while (min(g.degree()) <= 1):
    g = graphs.RandomGNM(N, M)

for u,v in g.edge_iterator(labels=None):
    g.set_edge_label(u, v, randint(tempoMin,tempoMax))

# Orienta as arestas de g formando um grafo fortemente conexo d
d = g.strong_orientation()


# Imprime a entrada como pedido no EP07

# Imprime N, M e K
print(N, M, K)

# Imprime arestas orientadas e os tempos
for u,v,l in d.edge_iterator():
    print(u, v, l)

# Imprime a posição de cada membro da comunidade
for i in range(0, K):
    print(randint(0, N-1))

# Imprime a posição de Biroliro
print(randint(0, N-1))


# Plota o grafo para visualização
d.show(edge_labels=True)
#d.show(edge_labels=False)
