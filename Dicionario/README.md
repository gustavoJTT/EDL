# Implementações de Dicionário - Hash Table e Skip List

Este projeto contém implementações completas de duas estruturas de dados fundamentais para dicionários: **Hash Table** e **Skip List**.

## 📁 Estrutura dos Arquivos

```
Dicionario/
├── dictInterface.java          # Interface comum para dicionários
├── Item.java                   # Classe para itens chave-valor
├── hash.java                   # Implementação da Hash Table
├── skipList.java              # Implementação da Skip List
├── Program.java               # Demonstração da Hash Table
├── ProgramSkipList.java       # Demonstração da Skip List
├── ComparacaoDicionarios.java # Comparação entre as duas estruturas
└── README.md                  # Este arquivo
```

## 🔍 Hash Table

### Características
- **Complexidade**: O(1) médio, O(n) pior caso
- **Técnicas**: Endereçamento aberto com sondagem linear ou double hashing
- **Redimensionamento**: Automático quando fator de carga > 50%
- **Capacidade**: Sempre um número primo para melhor distribuição

### Principais Funcionalidades
```java
hash tabela = new hash(capacidade, useDoubleHashing);

// Operações básicas
tabela.insertItem(chave, valor);
Item item = tabela.findElement(chave);
Item removido = tabela.removeElement(chave);

// Informações
int tamanho = tabela.size();
boolean vazia = tabela.isEmpty();

// Iteração
Iterator<Integer> chaves = tabela.keys();
Iterator<Item> elementos = tabela.elements();

// Visualização (debug)
tabela.printTable();
```

### Exemplo de Uso
```java
// Criar hash table com capacidade 7 e sondagem linear
hash tabela = new hash(7, false);

// Inserir elementos
tabela.insertItem(10, "Dez");
tabela.insertItem(22, "Vinte e Dois");
tabela.insertItem(31, "Trinta e Um");

// Buscar elemento
Item resultado = tabela.findElement(22);
if (resultado != null) {
    System.out.println("Encontrado: " + resultado.value());
}
```

## 📊 Skip List

### Características
- **Complexidade**: O(log n) probabilístico
- **Estrutura**: Multi-nível com ponteiros probabilísticos
- **Ordem**: Elementos sempre mantidos em ordem
- **Autobalanceamento**: Probabilístico através de "lançamento de moedas"

### Principais Funcionalidades
```java
skipList lista = new skipList();

// Operações básicas
lista.insertItem(chave, valor);
Item item = lista.findElement(chave);
Item removido = lista.removeElement(chave);

// Informações
int tamanho = lista.size();
boolean vazia = lista.isEmpty();
int niveis = lista.getLevels();

// Iteração (sempre em ordem)
Iterator<Integer> chaves = lista.keys();
Iterator<Item> elementos = lista.elements();

// Visualização
lista.printLevels();
lista.printStatistics();
```

### Exemplo de Uso
```java
// Criar skip list
skipList lista = new skipList();

// Inserir elementos (serão mantidos em ordem)
lista.insertItem(15, "Quinze");
lista.insertItem(5, "Cinco");
lista.insertItem(25, "Vinte e Cinco");

// Verificar estrutura multi-nível
lista.printLevels();

// Iterar em ordem
Iterator<Integer> chaves = lista.keys();
while (chaves.hasNext()) {
    System.out.print(chaves.next() + " "); // Output: 5 15 25
}
```

## 🚀 Como Executar

### 1. Compilar os arquivos
```bash
javac *.java
```

### 2. Executar demonstrações

**Hash Table:**
```bash
java Program
```

**Skip List:**
```bash
java ProgramSkipList
```

**Comparação:**
```bash
java ComparacaoDicionarios
```

## 📈 Comparação de Performance

| Operação | Hash Table | Skip List |
|----------|------------|-----------|
| Inserção | O(1) médio | O(log n) |
| Busca    | O(1) médio | O(log n) |
| Remoção  | O(1) médio | O(log n) |
| Iteração ordenada | O(n log n) | O(n) |
| Memória  | Menor | Maior |

## 🎯 Quando Usar Cada Uma

### Use Hash Table quando:
- ✅ Precisar de acesso muito rápido (O(1))
- ✅ Não precisar de elementos ordenados
- ✅ Memória for limitada
- ✅ Tiver boa distribuição de chaves

### Use Skip List quando:
- ✅ Precisar de elementos ordenados
- ✅ Quiser simplicidade vs árvores balanceadas
- ✅ Precisar de range queries
- ✅ Performance O(log n) for aceitável

## 🔧 Detalhes Técnicos

### Hash Table
- **Resolução de Colisões**: Sondagem linear ou double hashing
- **Função Hash**: Baseada na chave com compressão por módulo
- **Redimensionamento**: Dobra capacidade quando fator > 50%
- **Remoção**: Lazy deletion com marcação "Available"

### Skip List
- **Níveis**: Determinados probabilisticamente (50% chance por nível)
- **Sentinelas**: HEAD (MIN_VALUE) e TAIL (MAX_VALUE) em todos os níveis
- **Busca**: Navegação horizontal + vertical otimizada
- **Balanceamento**: Automático através da aleatoriedade

## 🧪 Testes Incluídos

1. **Operações Básicas**: Inserção, busca, remoção
2. **Tratamento de Erros**: Chaves duplicadas, elementos inexistentes
3. **Performance**: Comparação de tempos com muitos elementos
4. **Visualização**: Estrutura interna das duas implementações
5. **Iteração**: Verificação de ordem e completude

## 💡 Conceitos Demonstrados

- **Funções Hash** e tratamento de colisões
- **Estruturas probabilísticas** (Skip List)
- **Redimensionamento dinâmico**
- **Lazy deletion**
- **Iteradores** para travessia
- **Análise de complexidade** prática

## 🎓 Valor Educacional

Este projeto demonstra:
- Diferentes abordagens para o mesmo problema (dicionários)
- Trade-offs entre performance e funcionalidades
- Implementação de estruturas de dados avançadas
- Técnicas de otimização (números primos, probabilidade)
- Boas práticas de programação Java

---

**Nota**: As implementações incluem comentários detalhados explicando cada algoritmo e decisão de design, facilitando o aprendizado dos conceitos envolvidos.
