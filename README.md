<h1 align="center">Conversor de Moedas</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue.svg" alt="Java 17">
  <img src="https://img.shields.io/badge/Gson-2.10.1-blue.svg" alt="Gson 2.10.1">
  <img src="https://img.shields.io/badge/API-ExchangeRate-red.svg" alt="APIs">
  <img src="https://img.shields.io/badge/API-OpenExchangeRates-green.svg" alt="APIs">
  <img src="https://img.shields.io/badge/API-CoinGecko-orange.svg" alt="APIs">
  <img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License MIT">
</p>

## 📋 Descrição
O Conversor de Moedas é uma aplicação Java console que permite converter valores entre 15 moedas diferentes e diversas criptomoedas, usando taxas de câmbio em tempo real. A aplicação oferece histórico de conversões, logs detalhados e estatísticas de uso.

## ⚙️ Funcionalidades
- ✅ Conversão entre 15 moedas fiduciárias (ARS, BOB, BRL, CLP, COP, USD, EUR, GBP, JPY, AUD, CAD, CHF, CNY, MXN, INR)
- ✅ Conversão entre diversas criptomoedas (BTC, ETH, USDT, BNB, SOL, XRP, USDC, ADA, AVAX, DOGE)
- ✅ Conversão entre moedas fiduciárias e criptomoedas
- ✅ Taxas de câmbio atualizadas em tempo real através de múltiplas APIs (ExchangeRate-API, OpenExchangeRates, CoinGecko)
- ✅ Cache de taxas para otimizar performance e reduzir chamadas à API
- ✅ Histórico completo de conversões com persistência em arquivo JSON
- ✅ Sistema de logs detalhado para monitoramento de operações e erros
- ✅ Estatísticas de uso (total de conversões, moedas mais utilizadas, valores médios)
- ✅ Interface interativa via console com menus claros e validação de entrada
- ✅ Suporte para múltiplas APIs de câmbio com seleção de API preferencial
- ✅ Fallback automático entre APIs em caso de falha
- ✅ Exportação do histórico de conversões para arquivo TXT

## 🛠️ Tecnologias Utilizadas
- **Java JDK 17**: Linguagem de programação
- **Gson 2.10.1**: Biblioteca para serialização/desserialização JSON
- **Lombok 1.18.30**: Redução de boilerplate code (getters, setters, construtores)
- **HttpClient**: Módulo nativo do Java 11+ para requisições HTTP
- **ExchangeRate-API**: API principal para taxas de câmbio fiduciárias
- **OpenExchangeRates**: API alternativa para taxas de câmbio fiduciárias (fallback)
- **CoinGecko**: API para taxas de câmbio de criptomoedas
- **java.util.logging**: Sistema de logs integrado do Java
- **Scanner**: Interface de usuário via console para entrada de dados

## 🔄 Fluxogramas
```mermaid
flowchart TD
    A[Início da Aplicação] --> B[MenuPrincipal.main]
    B --> C[Inicializa Dependências]
    C --> C1[Historico, ApiConfig, ApiTaxaCambioService]
    C1 --> C2[ConversorMoedas, InterfaceConversao, Estatisticas]
    C2 --> D[Cria Instância de MenuPrincipal com DI]
    D --> E[MenuPrincipal.executar]
    E --> F{Loop Principal do Menu}
    F --> G[ConsoleUtils.clearScreen]
    G --> H[MenuPrincipal.exibirMenu]
    H --> I[Usuário seleciona opção]
    I --> J{Opção Selecionada}

    J -->|1: Converter Moeda| K[InterfaceConversao.realizarConversao]
    J -->|2: Ver Histórico| L[InterfaceConversao.exibirHistorico]
    J -->|3: Limpar Histórico| M[InterfaceConversao.limparHistorico]
    J -->|4: Ver Taxas| N[MenuPrincipal.verTaxasDisponiveis]
    J -->|5: Ver Estatísticas| O[Estatisticas.exibirEstatisticas]
    J -->|6: Selecionar API| P[MenuPrincipal.selecionarApi]
    J -->|7: Cripto para Moeda| Q[InterfaceConversao.realizarConversaoCripto]
    J -->|8: Cripto para Criptomoeda| R[InterfaceConversao.realizarConversaoCriptoParaCripto]
    J -->|9: Sair| S[Salva estado e encerra]

    K --> K1[InterfaceConversao.realizarConversaoGenerica]
    K1 --> K2[Exibe menus de seleção de moeda/cripto]
    K2 --> K3[InterfaceConversao.selecionarMoeda/Cripto]
    K3 --> K4[InterfaceConversao.solicitarValor]
    K4 --> K5[ConversorMoedas.converter]
    K5 --> K6[ApiTaxaCambioService.obterTaxaCambio]
    K6 --> K7{Tenta API Primária}
    K7 -->|Sucesso| K8[Retorna Taxa]
    K7 -->|Falha| K9{Tenta API Fallback}
    K9 -->|Sucesso| K8
    K9 -->|Falha| K10[Lança Exceção]
    K8 --> K11[Calcula Resultado]
    K11 --> K12[HistoricoConversao.adicionarConversao]
    K12 --> K13[LoggerUtil.log]
    K13 --> K14[Exibe Resultado]
    K14 --> K15{Deseja continuar?}
    K15 -->|Sim| K1
    K15 -->|Não| F

    L --> L1[HistoricoConversao.getConversoes]
    L1 --> L2[Exibe histórico formatado]
    L2 --> L3{Exportar para TXT?}
    L3 -->|Sim| L4[Exporta arquivo]
    L3 -->|Não| F
    L4 --> F

    M --> M1{Confirma limpeza?}
    M1 -->|Sim| M2[HistoricoConversao.limparHistorico]
    M1 -->|Não| F
    M2 --> F

    N --> N1[Exibe taxas disponíveis]
    N1 --> F

    O --> O1[Calcula e exibe estatísticas]
    O1 --> F

    P --> P1[Atualiza API preferencial]
    P1 --> P2[MenuPrincipal.inicializarServicos]
    P2 --> F

    Q --> Q1[Fluxo similar a K]
    Q1 --> F

    R --> R1[Fluxo similar a K]
    R1 --> F

    S --> Z[Fim da Aplicação]
```

```mermaid
sequenceDiagram
    participant U as Usuário
    participant M as MenuPrincipal
    participant IC as InterfaceConversao
    participant CM as ConversorMoedas
    participant ATS as ApiTaxaCambioService
    participant API as API Externa
    participant H as HistoricoConversao
    participant L as Logger

    U->>M: Iniciar Aplicação
    M->>M: Inicializar Serviços
    M->>U: Exibir Menu Principal
    U->>M: Seleciona opção Converter (1)
    M->>IC: realizarConversao(scanner, conversorMoedas)
    IC->>IC: exibirMenuSelecaoMoeda()
    IC->>U: Solicita moeda origem
    U->>IC: Informa USD
    IC->>IC: selecionarMoeda(scanner, "Selecione a moeda de origem: ")
    IC->>IC: exibirMenuSelecaoMoeda()
    IC->>U: Solicita moeda destino
    U->>IC: Informa BRL
    IC->>IC: selecionarMoeda(scanner, "Selecione a moeda de destino: ")
    IC->>IC: solicitarValor(scanner)
    IC->>U: Solicita valor
    U->>IC: Informa 100.00
    IC->>CM: converter(100, USD, BRL)
    CM->>ATS: obterTaxaCambio(USD, BRL)
    
    alt Taxa em Cache
        ATS->>ATS: Buscar no cache
        ATS->>CM: Retorna taxa cached
    else Cache expirado/inexistente
        ATS->>ATS: Tentar API Primária
        ATS->>API: Requisição HTTP
        API-->>ATS: Resposta JSON
        ATS->>ATS: Parsear JSON
        ATS->>ATS: Armazenar em cache
        ATS->>CM: Retorna taxa
    end
    
    CM->>CM: Calcular conversão
    CM->>H: adicionarConversao(resultado)
    CM->>L: Registrar log
    CM-->>IC: Retorna resultado
    IC->>U: Exibe: 100 USD = 495 BRL
    IC->>U: Deseja fazer outra conversão? (s/n)
    U->>IC: Informa 'n'
    IC-->>M: Retorna
    M->>U: Exibir Menu Principal
```

```mermaid
classDiagram
    class Main {
        +main(String[] args)
    }
    
    class MenuPrincipal {
        -Scanner scanner
        -ConversorMoedas conversorMoedas
        -HistoricoConversao historicoConversao
        -InterfaceConversao interfaceConversao
        -Estatisticas estatisticas
        -ApiProvider preferredApi
        +MenuPrincipal(ConversorMoedas, HistoricoConversao, InterfaceConversao, Estatisticas)
        +exibirMenu()
        +executar()
        -inicializarServicos()
        -selecionarApi()
        -realizarConversao()
        -verHistorico()
        -limparHistorico()
        -verTaxasDisponiveis()
        -verEstatisticas()
        -realizarConversaoCripto()
        -realizarConversaoCriptoParaCripto()
    }
    
    class Moeda {
        <<enumeration>>
        +codigo: String
        +nomeCompleto: String
        +simbolo: String
        +tipo: MoedaType
        +isCrypto(): boolean
        +isFiat(): boolean
        +fromCodigo(String): Moeda
    }

    class MoedaType {
        <<enumeration>>
        FIAT
        CRYPTO
    }
    
    class TaxaCambio {
        +origem: Moeda
        +destino: Moeda
        +taxa: double
        +timestamp: LocalDateTime
    }
    
    class ConversaoResultado {
        +valorOriginal: double
        +moedaOrigem: Moeda
        +valorConvertido: double
        +moedaDestino: Moeda
        +taxaUtilizada: double
        +dataHora: LocalDateTime
    }
    
    class ConversorMoedas {
        -ApiTaxaCambio apiTaxaCambio
        -HistoricoConversao historicoConversao
        +converter(double, Moeda, Moeda): ConversaoResultado
    }
    
    class ApiTaxaCambio {
        <<interface>>
        +obterTaxaCambio(Moeda, Moeda) TaxaCambio
    }

    class ApiTaxaCambioService {
        -ApiTaxaCambio primaryApi
        -ApiTaxaCambio fallbackApi
        -ApiTaxaCambio coinGeckoApi
        -Map~String,TaxaCambio~ cacheTaxas
        +ApiTaxaCambioService(ApiConfig, ApiProvider)
        +obterTaxaCambio(Moeda, Moeda): TaxaCambio
        -obterTaxaCambioCripto(Moeda, Moeda): TaxaCambio
        -isCacheValid(TaxaCambio): boolean
        +limparCacheExpirado()
    }

    class ApiProvider {
        <<enumeration>>
        EXCHANGERATE_API
        OPENEXCHANGERATES
        +getName(): String
        +fromString(String): ApiProvider
    }

    class ApiTaxaCambioFactory {
        +getApiImplementation(ApiProvider, ApiConfig): ApiTaxaCambio
    }
    
    class ExchangeRateApiImpl {
        -HttpClient client
        -ApiConfig apiConfig
        -JsonParser jsonParser
        +ExchangeRateApiImpl(ApiConfig)
        +obterTaxaCambio(Moeda, Moeda): TaxaCambio
    }

    class OpenExchangeRatesApiImpl {
        -HttpClient client
        -Gson gson
        -String appId
        +OpenExchangeRatesApiImpl(String)
        +obterTaxaCambio(Moeda, Moeda): TaxaCambio
        -parseOpenExchangeRatesResponse(String, Moeda, Moeda): double
    }

    class CoinGeckoApiImpl {
        -HttpClient client
        -Gson gson
        -String apiKey
        +CoinGeckoApiImpl(String)
        +obterTaxaCambio(Moeda, Moeda): TaxaCambio
        -parseCoinGeckoResponse(String, Moeda, Moeda): double
    }
    
    class HistoricoConversao {
        -Gson gson
        -List~ConversaoResultado~ conversoes
        +HistoricoConversao()
        +adicionarConversao(ConversaoResultado)
        +getUltimasConversoes(int): List~ConversaoResultado~
        +limparHistorico()
        +estaVazio(): boolean
        +salvarEmArquivo()
        -carregarDeArquivo()
    }

    class Estatisticas {
        -HistoricoConversao historicoConversao
        +Estatisticas(HistoricoConversao)
        +exibirEstatisticas()
        -getTotalConversoes(List~ConversaoResultado~): int
        -getMoedaMaisConvertida(List~ConversaoResultado~): Moeda
        -getValorTotalConvertidoPorMoeda(List~ConversaoResultado~): Map~Moeda,Double~
        -getMediaValoresConvertidos(List~ConversaoResultado~): double
        -gerarGraficoFrequenciaMoedas(List~ConversaoResultado~)
    }
    
    class InterfaceConversao {
        -Logger logger
        +exibirMenuSelecaoMoeda()
        +exibirMenuSelecaoCripto()
        -selecionarMoedaGenerica(Scanner, String, Predicate~Moeda~): Moeda
        +selecionarCripto(Scanner, String): Moeda
        +selecionarMoeda(Scanner, String): Moeda
        +solicitarValor(Scanner): double
        -perguntarContinuarConversao(Scanner): boolean
        -realizarConversaoGenerica(Scanner, ConversorMoedas, Runnable, Function~Scanner,Moeda~, Runnable, Function~Scanner,Moeda~)
        +realizarConversao(Scanner, ConversorMoedas)
        +realizarConversaoCripto(Scanner, ConversorMoedas)
        +realizarConversaoCriptoParaCripto(Scanner, ConversorMoedas)
        +exibirHistorico(Scanner, HistoricoConversao)
        +limparHistorico(Scanner, HistoricoConversao)
        -exportarHistoricoParaTxt(List~ConversaoResultado~)
    }
    
    class ApiConfig {
        -static ApiConfig instance
        -apiKey: String
        -openExchangeRatesAppId: String
        -coinGeckoApiKey: String
        -BASE_URL: String
        -COINGECKO_BASE_URL: String
        +getInstance(): ApiConfig
        -loadApiKey()
        +buildPairConversionUrl(String, String): String
        +buildCoinGeckoSimplePriceUrl(String, String): String
    }
    
    class JsonParser {
        -Gson gson
        +JsonParser(Gson)
        +parseConversionRate(String, String, String): double
    }
    
    class LoggerUtil {
        +getLogger(String): Logger
    }

    class ConsoleUtils {
        +clearScreen()
        +waitForEnter(Scanner)
        +solicitarDouble(Scanner, String, String, Predicate~Double~): double
    }

    Main --> MenuPrincipal
    MenuPrincipal --> ConversorMoedas
    MenuPrincipal --> HistoricoConversao
    MenuPrincipal --> InterfaceConversao
    MenuPrincipal --> Estatisticas
    MenuPrincipal --> ApiProvider
    MenuPrincipal --> ConsoleUtils
    ConversorMoedas --> ApiTaxaCambio
    ConversorMoedas --> HistoricoConversao
    ApiTaxaCambioService ..|> ApiTaxaCambio
    ApiTaxaCambioService --> ApiConfig
    ApiTaxaCambioService --> ApiProvider
    ApiTaxaCambioService --> ApiTaxaCambioFactory
    ApiTaxaCambioFactory --> ApiTaxaCambio
    ApiTaxaCambioFactory --> ApiConfig
    ExchangeRateApiImpl ..|> ApiTaxaCambio
    ExchangeRateApiImpl --> ApiConfig
    ExchangeRateApiImpl --> JsonParser
    OpenExchangeRatesApiImpl ..|> ApiTaxaCambio
    CoinGeckoApiImpl ..|> ApiTaxaCambio
    HistoricoConversao --> ConversaoResultado
    HistoricoConversao --> LoggerUtil
    Estatisticas --> HistoricoConversao
    Estatisticas --> Moeda
    InterfaceConversao --> ConversorMoedas
    InterfaceConversao --> HistoricoConversao
    InterfaceConversao --> Moeda
    InterfaceConversao --> ConsoleUtils
    InterfaceConversao --> LoggerUtil
    JsonParser --> Gson
    Moeda --> MoedaType
```

## 📦 Estrutura do Projeto
```
conversor-moedas/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── conversor/
│                   ├── Main.java
│                   ├── MenuPrincipal.java
│                   ├── InterfaceConversao.java
│                   ├── Estatisticas.java
│                   ├── models/
│                   │   ├── Moeda.java
│                   │   ├── TaxaCambio.java
│                   │   ├── ConversaoResultado.java
│                   │   └── HistoricoConversao.java
│                   ├── services/
│                   │   ├── ApiTaxaCambio.java
│                   │   ├── ApiTaxaCambioService.java
│                   │   ├── ApiTaxaCambioFactory.java
│                   │   ├── ApiProvider.java
│                   │   ├── ExchangeRateApiImpl.java
│                   │   ├── OpenExchangeRatesApiImpl.java
│                   │   ├── CoinGeckoApiImpl.java
│                   │   └── ConversorMoedas.java
│                   └── utils/
│                       ├── ApiConfig.java
│                       ├── ConsoleUtils.java
│                       ├── JsonParser.java
│                       └── LoggerUtil.java
├── data/
│   └── historico.json
├── logs/
│   └── conversoes.log
├── docs/
│   ├── capturas/
│   └── fluxogramas/
├── .gitignore
├── README.md
├── API_SETUP.md
├── TESTES.md
├── DOCUMENTACAO.md
└── pom.xml
```

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 17 ou superior instalado
- Apache Maven instalado
- Conexão com internet
- Chaves das APIs ExchangeRate-API, OpenExchangeRates e CoinGecko (gratuitas)

### Configuração
1. Clone o repositório:
   ```bash
   git clone https://github.com/dessima/conversor-moedas.git
   cd conversor-moedas
   ```
2. Obtenha suas chaves das APIs:
   - **ExchangeRate-API**: Registre-se em [https://www.exchangerate-api.com/](https://www.exchangerate-api.com/) e obtenha sua chave gratuita.
   - **OpenExchangeRates**: Registre-se em [https://openexchangerates.org/](https://openexchangerates.org/) e obtenha seu App ID gratuito.
   - **CoinGecko**: Registre-se em [https://www.coingecko.com/api](https://www.coingecko.com/api) e obtenha sua chave gratuita (se necessário, para acesso a dados mais abrangentes).
3. Configure as variáveis de ambiente com suas chaves:
   - `EXCHANGERATE_API_KEY=SUA_CHAVE_EXCHANGERATE`
   - `OPENEXCHANGERATES_APP_ID=SEU_APP_ID_OPENEXCHANGERATES`
   - `COINGECKO_API_KEY=SUA_CHAVE_COINGECKO`
   (No Windows, você pode definir variáveis de ambiente temporariamente no terminal com `set VAR=VALOR` ou permanentemente nas configurações do sistema. No Linux/macOS, use `export VAR=VALOR` ou adicione ao seu `.bashrc`/`.zshrc`).

### Compilação e Execução
1. Compile o projeto usando Maven:
   ```bash
   mvn clean install
   ```
2. Execute a aplicação:
   ```bash
   java -jar target/conversor-moedas-1.0-SNAPSHOT.jar
   ```
   (Certifique-se de que o nome do JAR corresponde ao gerado pelo Maven, que pode variar ligeiramente.)

## 👨‍💻 Autor

<p>
    <a href="https://www.linkedin.com/in/dessim">
        <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn">
    </a>
    <a href="https://github.com/dessima">
        <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub">
    </a>
</p>

<p>
    Feito com ❤️ por <strong>José Anderson</strong>
</p>

---

## 📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.