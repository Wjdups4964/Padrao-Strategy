
# Websearch - Padrão Strategy

## Objetivo

Esta atividade aplica o padrão de projeto Strategy ao projeto `websearch`.

O programa simula um mecanismo de busca lendo o arquivo `Hamlet.txt`. Cada linha do arquivo representa uma consulta feita por um usuário. O objetivo da alteração é permitir que observadores sejam notificados apenas quando uma consulta atender a um critério específico.

Antes da alteração, todo observador recebia todas as consultas. Depois da aplicação do Strategy, cada observador passa a ter uma estratégia de filtro associada.

## Problema inicial

O código original já utilizava a ideia de observadores.

O `WebSearchModel` lia o arquivo linha por linha e notificava todos os observadores cadastrados:

```java
private void notifyAllObservers(String line) {
    for (QueryObserver obs : observers) {
        obs.onQuery(line);
    }
}
```

O problema é que o modelo não verificava se o observador estava interessado naquela consulta. Toda consulta era enviada para todos os observadores.

## Solução aplicada

Foi criada a interface `QueryFilter`, que representa a estratégia de filtragem:

```java
public interface QueryFilter {
    boolean matches(String query);
}
```

Essa interface define que qualquer filtro de consulta precisa implementar o método `matches`.

Esse método recebe uma consulta e retorna:

- `true`, se o observador deve ser notificado;
- `false`, se o observador não deve ser notificado.

Depois disso, o `WebSearchModel` passou a registrar cada observador junto com um filtro.

Com isso, antes de notificar um observador, o modelo consulta a estratégia de filtro:

```java
if (registration.filter.matches(line)) {
    registration.observer.onQuery(line);
}
```

Assim, o `WebSearchModel` não precisa conhecer os critérios concretos de filtragem. Ele apenas usa a interface `QueryFilter`.

## Estratégias configuradas

No `Snooper`, foram criados dois observadores com filtros diferentes.

O primeiro observador imprime:

```text
Oh Yes! <consulta>
```

quando a consulta contém a palavra `friend`, sem diferenciar letras maiúsculas e minúsculas.

O segundo observador imprime:

```text
So long <consulta>
```

quando a consulta possui mais de 60 caracteres.

## Relação com o padrão Strategy

O padrão Strategy foi aplicado porque o comportamento de filtragem foi separado do modelo principal.

O `WebSearchModel` não sabe se o filtro procura a palavra `friend`, se verifica tamanho ou se usa qualquer outro critério. Ele apenas chama o método definido pela interface:

```java
filter.matches(query)
```

Isso permite trocar ou adicionar novas regras de filtragem sem alterar a lógica principal do modelo de busca.

## Documentação do uso de IA

### Etapa 1 - Criação da estratégia de filtro

**Prompt utilizado:**

```text
Qual deve ser o primeiro passo para aplicar Strategy ao websearch?
```

**O que a IA sugeriu:**

A IA sugeriu criar uma interface para representar a estratégia de filtragem das consultas.

**Ajuste feito:**

Foi criada a interface `QueryFilter`, com o método `matches(String query)`.

**Por que esse ajuste foi adequado:**

A interface separa a regra de filtragem do modelo de busca, permitindo que diferentes filtros sejam usados sem alterar o `WebSearchModel`.

### Etapa 2 - Registro de observadores com filtro

**Prompt utilizado:**

```text
Como faço para associar cada observador a um filtro sem implementar os filtros finais ainda?
```

**O que a IA sugeriu:**

A IA sugeriu alterar o método de cadastro de observadores para receber também um objeto de filtro.

**Ajuste feito:**

O método `addQueryObserver` passou a receber um `QueryObserver` e um `QueryFilter`. Também foi criada uma classe auxiliar para guardar o observador junto com seu filtro.

**Por que esse ajuste foi adequado:**

Essa mudança permite que cada observador tenha sua própria estratégia de filtragem, que será usada posteriormente para decidir se ele deve ou não ser notificado.

### Etapa 3 - Uso do filtro antes da notificação

**Prompt utilizado:**

```text
Como faço o WebSearchModel usar o filtro antes de notificar os observadores?
```

**O que a IA sugeriu:**

A IA sugeriu chamar o método `matches` do filtro antes de chamar o método `onQuery` do observador.

**Ajuste feito:**

O método `notifyAllObservers` foi alterado para notificar um observador apenas quando o filtro associado retorna `true`.

**Por que esse ajuste foi adequado:**

Com isso, o `WebSearchModel` continua sem conhecer os critérios concretos de filtragem, mas passa a respeitar a estratégia associada a cada observador.

### Etapa 4 - Configuração dos filtros reais

**Prompt utilizado:**

```text
Agora que o WebSearchModel já aceita estratégias de filtro, como configuro o Snooper para criar os dois observadores exigidos no enunciado?
```

**O que a IA sugeriu:**

A IA sugeriu configurar dois observadores no `Snooper`: um para consultas contendo `friend` e outro para consultas com mais de 60 caracteres.

**Ajuste feito:**

O filtro provisório que aceitava todas as consultas foi substituído pelos filtros reais do enunciado.

**Por que esse ajuste foi adequado:**

Essa alteração faz a solução atender diretamente ao enunciado, imprimindo `Oh Yes!` para consultas com `friend` e `So long` para consultas longas.

### Etapa 5 - Ajustes finais de execução

**Prompt utilizado:**

```text
Depois de configurar os filtros reais no Snooper, que ajustes finais devo fazer para testar a saída do websearch com o arquivo Hamlet.txt?
```

**O que a IA sugeriu:**

A IA sugeriu conferir o caminho do arquivo `Hamlet.txt` e ajustar a formatação da saída, se necessário.

**Ajuste feito:**

Foi conferido o caminho do arquivo de entrada e, se necessário, ajustada a saída para remover espaços iniciais das consultas.

**Por que esse ajuste foi adequado:**

Esses ajustes garantem que o programa execute corretamente no ambiente local e que a saída fique mais legível, sem alterar a lógica do padrão Strategy.

### Etapa 6 - Documentação final

**Prompt utilizado:**

```text
Como posso documentar a solução da questão websearch com Strategy, incluindo os prompts usados, os ajustes feitos e como executar o programa?
```

**O que a IA sugeriu:**

A IA sugeriu criar um README explicando o objetivo da questão, a aplicação do padrão Strategy, os prompts utilizados, os ajustes feitos e os comandos de compilação e execução.

**Ajuste feito:**

Foi criado este README para documentar a solução e complementar o histórico de commits.

**Por que esse ajuste foi adequado:**

A documentação ajuda a demonstrar como a solução evoluiu e facilita a avaliação do professor durante a análise do repositório.

## Histórico da solução

A solução foi organizada em commits curtos, cada um representando uma etapa da implementação:

```text
Cria estrategia de filtro de consultas
Registra observadores com estrategia de filtro
Filtra consultas antes de notificar observadores
Configura filtros reais no Snooper
Ajusta entrada e saida do websearch
Documenta solucao Strategy do websearch
```

Essa organização permite que o histórico do repositório conte a evolução da solução.

## Como compilar

Na raiz do projeto, execute:

```bash
javac websearch/*.java
```

## Como executar

Na raiz do projeto, execute:

```bash
java -cp websearch Main
```

## Resultado esperado

A saída deve exibir apenas as consultas que passam por pelo menos um dos filtros configurados.

Exemplos de saída:

```text
Oh Yes! Friends to this ground.
So long Enter KING CLAUDIUS, QUEEN GERTRUDE, HAMLET, POLONIUS, LAERTES, VOLTIMAND, CORNELIUS, Lords, and Attendants
Oh Yes! And let thine eye look like a friend on Denmark.
```

## Conclusão

A solução aplica o padrão Strategy ao separar a regra de filtragem do modelo de busca. O `WebSearchModel` continua responsável por ler as consultas e notificar observadores, mas a decisão sobre quais consultas interessam fica encapsulada em objetos de filtro.

Com isso, o código fica mais flexível, pois novos filtros podem ser criados e associados a observadores sem alterar o funcionamento interno do modelo de busca.
