##projeto de compiladores semestre 2013.2

Lista de Analises Semânticas

Checagem Semânticas

- Declaração de Classes, metodos e variaveis já existentes 
- Operador "+" somente aplicado a inteiros
- Operador "-" somente aplicado a inteiros
- Operador "*" somente aplicado a inteiros
- Operador "<" somente aplicado a inteiros
- Operador "&&" somente aplicado a booleano
- Operador "!" somente aplicado a booleanos 
- Expressão no "if" somente booleano 
- Expressão no "while" somente booleano
- Tipo de retorno de Metodo igual ao tipo do metodo
- Chamada de Metodos com o número correto de parâmetros
- Chamada de Metodos com os tipos dos parâmetros corretos
- Variavel utilizada é declarada anteriormente
- Variavel usada, onde a declaração é em uma classe base
- Herda de classe inexistente
- Atribuições: variaveis simples(int = int e boolean = boolean)
- Atribuições: vetor1 = vetor2
- Atribuição de vetor[exp1] = exp2 tem o mesmo tipo
- Atribuições: variavel simples(int ou boolean) recebe o retorno de um metodo
- Atribuições: Objeto recebe outro Objeto
- Atribuições: Objeto recebe retorno de um método
- Expressoes em System.out.println(EXP) somente tipo inteiro
- Expressão EXP.length somente aplicado ao tipo int[]
- Expressão em new int[EXP] somente tipo inteiro
- Expressão(index) em vetor[EXP] somente tipo inteiro
- Declaração de objeto de uma classe inexistente 
- Declaração de tipo inexistente 
- Métodos sobrepostos(overriding) tem mesma lista de arguemtos e mesmo retorno
- Verificar se argumentos e variaveis de um método não são iguais
- Chamada de metodo inexistente
- Atribuições: Objeto recebe um Objeto de uma classe base (Herança) 
- Atribuições: Verificar se vetor2 (vetor1 = vetor2) é do tipo intArrayType

OBS:
Sobrecarga de métodos(overloading) NÂO é suportada pelo Minijava
Sobreposição de métodos(overriding) é suportada pelo Minijava

