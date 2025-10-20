# 📚 Documentação Detalhada do Projeto Conversor de Moedas

## ✨ Introdução
Este documento explora a arquitetura e o design do projeto "Conversor de Moedas", uma aplicação Java console desenvolvida com foco em boas práticas de programação, reuso de código e manutenibilidade. Abordaremos como os princípios de Orientação a Objetos (OO), Clean Code e Padrões de Projeto foram aplicados para criar uma solução robusta e extensível.

## 🚀 Estrutura do Projeto
A organização do projeto segue uma estrutura modular, facilitando a separação de responsabilidades e a localização de funcionalidades.

| Diretório        | Descrição                                                                 |
| :--------------- | :------------------------------------------------------------------------ |
| `src/main/java/com/conversor` | Pacote principal contendo a lógica de orquestração e a interface de usuário. |
| `src/main/java/com/conversor/models` | Classes que representam as entidades de dados do sistema (Moeda, TaxaCambio, ConversaoResultado, HistoricoConversao). |
| `src/main/java/com/conversor/services` | Classes que implementam a lógica de negócio e a integração com APIs externas. |
| `src/main/java/com/conversor/utils` | Classes utilitárias para configurações, parsing JSON, logging e interação com o console. |

## 💡 Conceitos de Orientação a Objetos (OO)
A Orientação a Objetos é a base deste projeto, promovendo a modularidade, reuso e facilidade de manutenção.

### 📦 Encapsulamento
*   **Onde:** Todas as classes.
*   **Como:** Campos privados (`private final`) com acesso controlado via métodos públicos (`public`). Ex: `Moeda` tem `codigo`, `nomeCompleto`, `simbolo` privados, acessíveis via `getCodigo()`, etc.
*   **Porquê:** Protege o estado interno dos objetos, garantindo que as modificações ocorram de forma controlada e consistente.

### 🧩 Abstração
*   **Onde:** Interface `ApiTaxaCambio`.
*   **Como:** Define um contrato (`obterTaxaCambio`) para serviços de API, sem expor os detalhes de implementação de cada API externa.
*   **Porquê:** Permite que o `ConversorMoedas` interaja com qualquer serviço de API que implemente essa interface, sem se preocupar com a complexidade interna de cada um.

### ↩️ Herança (Polimorfismo)
*   **Onde:** Implementações de `ApiTaxaCambio` (e.g., `ExchangeRateApiImpl`, `OpenExchangeRatesApiImpl`, `CoinGeckoApiImpl`).
*   **Como:** Cada classe implementa o método `obterTaxaCambio` de sua própria maneira, mas todas podem ser tratadas como um `ApiTaxaCambio` genérico.
*   **Porquê:** Permite que o `ApiTaxaCambioService` (e o `ConversorMoedas`) trabalhe com diferentes APIs de forma unificada, facilitando a adição de novas APIs no futuro.

## 🧹 Princípios de Clean Code
Aderir aos princípios de Clean Code garante que o código seja legível, compreensível e fácil de manter.

### 🎯 Nomes Significativos
*   **Onde:** Em todo o projeto.
*   **Exemplos:** `HistoricoConversao`, `ConversaoResultado`, `ApiTaxaCambioService`, `solicitarValor`.
*   **Porquê:** Facilita a compreensão rápida da finalidade de classes, métodos e variáveis, reduzindo a necessidade de comentários excessivos.

### 📏 Funções Pequenas e Focadas (Single Responsibility Principle - SRP)
*   **Onde:** Métodos como `exibirMenuSelecaoMoedaGenerica`, `perguntarContinuarConversao`, `parseConversionRate`.
*   **Como:** Cada método realiza uma única tarefa bem definida.
*   **Porquê:** Melhora a legibilidade, testabilidade e manutenibilidade do código.

### ✍️ Comentários e Documentação
*   **Onde:** `README.md`, `API_SETUP.md`, `TESTES.md` e este `DOCUMENTACAO.md`. Comentários no código são usados para explicar o *porquê*, não o *o quê*.
*   **Porquê:** Fornece contexto e explicações de alto nível, essenciais para novos desenvolvedores e para a evolução do projeto.

### ❌ Tratamento de Erros
*   **Onde:** Em todas as interações com o usuário e chamadas de API.
*   **Como:** Uso de `try-catch` para exceções específicas (`InputMismatchException`, `IOException`, `InterruptedException`, `RuntimeException`), mensagens amigáveis para o usuário e logs detalhados para o desenvolvedor.
*   **Porquê:** Garante que a aplicação seja robusta, não falhe inesperadamente e forneça feedback útil ao usuário.

## 🏗️ Padrões de Projeto
A aplicação de padrões de projeto resolve problemas comuns de design de software de forma comprovada e eficiente.

### 🌟 Singleton
*   **Onde:** `ApiConfig`.
*   **Como:** Garante que apenas uma instância de `ApiConfig` exista em toda a aplicação, acessível globalmente via `ApiConfig.getInstance()`.
*   **Porquê:** `ApiConfig` gerencia chaves de API e URLs base, que são recursos únicos e globais. Uma única instância evita inconsistências e uso excessivo de recursos.

### 🏭 Factory Method
*   **Onde:** `ApiTaxaCambioFactory`.
*   **Como:** A classe `ApiTaxaCambioFactory` é responsável por criar instâncias de implementações de `ApiTaxaCambio` (e.g., `ExchangeRateApiImpl`, `OpenExchangeRatesApiImpl`) com base em um `ApiProvider`.
*   **Porquê:** Desacopla o código cliente (`ApiTaxaCambioService`) da criação de objetos concretos, permitindo que novas APIs sejam adicionadas sem modificar o código que as utiliza.

### 🔌 Strategy
*   **Onde:** Interface `ApiTaxaCambio` e suas implementações.
*   **Como:** Define uma família de algoritmos (diferentes formas de obter taxas de câmbio) e encapsula cada um deles. O `ApiTaxaCambioService` pode alternar entre essas estratégias (APIs) em tempo de execução.
*   **Porquê:** Permite que o algoritmo (a API usada) varie independentemente dos clientes que o utilizam, facilitando o fallback e a seleção dinâmica de APIs.

### 💉 Dependency Injection (DI)
*   **Onde:** `MenuPrincipal` e `ConversorMoedas`.
*   **Como:** As dependências (e.g., `ConversorMoedas`, `HistoricoConversao`, `InterfaceConversao`, `Estatisticas`) são passadas para o construtor de `MenuPrincipal` em vez de serem criadas internamente.
*   **Porquê:** Reduz o acoplamento entre as classes, tornando-as mais modulares, testáveis e fáceis de manter.

## 🔄 Fluxo de Execução Detalhado
A aplicação inicia com o `MenuPrincipal`, que orquestra as interações do usuário e as operações de negócio.

```mermaid
graph TD
    A[Início da Aplicação] --> B{MenuPrincipal.main()}
    B --> C[Inicializa Dependências (Historico, ApiConfig, ApiTaxaCambioService, ConversorMoedas, InterfaceConversao, Estatisticas)]
    C --> D[Cria Instância de MenuPrincipal com DI]
    D --> E[MenuPrincipal.executar()]
    E --> F{Loop Principal do Menu}
    F --> G[ConsoleUtils.clearScreen()]
    F --> H[MenuPrincipal.exibirMenu()]
    H --> I[Usuário seleciona opção]
    I --> J{Switch/Case para Opções}

    J --> |1: Converter Moeda| K[InterfaceConversao.realizarConversao()]
    J --> |2: Ver Histórico| L[InterfaceConversao.exibirHistorico()]
    J --> |3: Limpar Histórico| M[InterfaceConversao.limparHistorico()]
    J --> |4: Ver Taxas| N[MenuPrincipal.verTaxasDisponiveis()]
    J --> |5: Ver Estatísticas| O[Estatisticas.exibirEstatisticas()]
    J --> |6: Selecionar API| P[MenuPrincipal.selecionarApi()]
    J --> |7: Cripto para Moeda| Q[InterfaceConversao.realizarConversaoCripto()]
    J --> |8: Cripto para Criptomoeda| R[InterfaceConversao.realizarConversaoCriptoParaCripto()]
    J --> |9: Sair| S[Salva estado e encerra]

    K --> K1[InterfaceConversao.realizarConversaoGenerica()]
    K1 --> K2[Exibe menus de seleção de moeda/cripto]
    K2 --> K3[InterfaceConversao.selecionarMoeda/Cripto()]
    K3 --> K4[InterfaceConversao.solicitarValor()]
    K4 --> K5[ConversorMoedas.converter()]
    K5 --> K6[ApiTaxaCambioService.obterTaxaCambio()]
    K6 --> K7{Tenta API Primária}
    K7 --> |Sucesso| K8[Retorna Taxa]
    K7 --> |Falha| K9{Tenta API Fallback}
    K9 --> |Sucesso| K8
    K9 --> |Falha| K10[Lança Exceção]
    K8 --> K11[Calcula Resultado]
    K11 --> K12[HistoricoConversao.adicionarConversao()]
    K12 --> K13[LoggerUtil.log()]
    K13 --> K14[Exibe Resultado]
    K14 --> K15[Pergunta se deseja continuar]
    K15 --> F

    L --> L1[HistoricoConversao.getConversoes()]
    L1 --> L2[Exibe histórico formatado]
    L2 --> L3[Oferece exportar para TXT]
    L3 --> F

    O --> O1[Calcula e exibe estatísticas]
    O1 --> F

    P --> P1[Atualiza API preferencial]
P1 --> P2[MenuPrincipal.inicializarServicos() para recarregar APIs]
P2 --> F

    S --> Z[Fim da Aplicação]


## 🛡️ Tratamento de Erros
A robustez da aplicação é garantida por um tratamento de erros abrangente.

*   **Validação de Entrada do Usuário:** Métodos como `solicitarValor` e `selecionarMoedaGenerica` validam a entrada do usuário, solicitando novas entradas em caso de dados inválidos.
*   **Exceções de API:** `ApiTaxaCambioService` e suas implementações (`ExchangeRateApiImpl`, `OpenExchangeRatesApiImpl`, `CoinGeckoApiImpl`) capturam e tratam exceções de rede (`IOException`, `InterruptedException`) e erros de resposta da API (códigos HTTP 4xx, 5xx), lançando `RuntimeException` com mensagens claras.
*   **Logs Detalhados:** Todos os erros são registrados no `LoggerUtil`, com níveis de severidade apropriados (`WARNING`, `SEVERE`), auxiliando na depuração e monitoramento.

## 📝 Logs e Persistência
A aplicação mantém um registro de suas operações e um histórico das conversões realizadas.

### 🪵 Sistema de Logs (`LoggerUtil`)
*   **Funcionalidade:** Registra eventos importantes da aplicação em um arquivo (`logs/conversoes.log`).
*   **Formato:** `[DATA] [NÍVEL] Mensagem`.
*   **Recursos:** Rotação de logs (limite de tamanho e número de arquivos) e um `CustomFormatter` para padronizar a saída.
*   **Porquê:** Essencial para depuração, auditoria e monitoramento do comportamento da aplicação.

### 💾 Persistência do Histórico (`HistoricoConversao`)
*   **Funcionalidade:** Salva e carrega o histórico de conversões de/para um arquivo JSON (`data/historico.json`).
*   **Recursos:** Utiliza Gson para serialização/desserialização e implementa um mecanismo de backup (`historico.json.bak`) para proteger contra arquivos corrompidos.
*   **Porquê:** Garante que o histórico de conversões seja mantido entre as sessões da aplicação, proporcionando uma experiência contínua ao usuário.

## 📈 Extensibilidade
O design do projeto foi pensado para facilitar futuras expansões.

*   **Novas Moedas/Criptomoedas:** Adicionar novas moedas ou criptomoedas é tão simples quanto adicionar uma nova entrada ao enum `Moeda`.
*   **Novas APIs de Câmbio:** Graças à interface `ApiTaxaCambio` e ao `ApiTaxaCambioFactory`, novas APIs podem ser integradas criando uma nova implementação da interface e adicionando-a à fábrica, sem modificar a lógica central de conversão.
*   **Novas Funcionalidades:** A separação de responsabilidades permite adicionar novas funcionalidades (e.g., gráficos mais avançados, integração com outras plataformas) como novas classes ou métodos, minimizando o impacto no código existente.

## 🏁 Conclusão
O projeto "Conversor de Moedas" é um exemplo prático de como a aplicação consciente de princípios de Orientação a Objetos, Clean Code e Padrões de Projeto pode resultar em um software bem estruturado, fácil de entender, manter e estender. A modularidade e o baixo acoplamento são pilares que garantem a longevidade e a adaptabilidade desta solução.
