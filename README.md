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
O **Conversor de Moedas** é uma aplicação de console robusta, desenvolvida em Java, que permite a conversão de valores entre moedas fiduciárias e criptomoedas. Utilizando taxas de câmbio em tempo real de múltiplas APIs, a aplicação oferece uma experiência interativa e completa, com funcionalidades de histórico, logs e estatísticas de uso.

## ⚙️ Funcionalidades
- ✅ **Conversão Tripla:**
  - Moedas fiduciárias para moedas fiduciárias.
  - Criptomoedas para moedas fiduciárias.
  - Criptomoedas para criptomoedas.
- ✅ **Taxas de Câmbio em Tempo Real:** Cotações atualizadas para garantir precisão.
- ✅ **Cache Inteligente:** Sistema de cache para otimizar o uso das APIs e melhorar a performance.
- ✅ **Histórico de Conversões:** Todas as conversões são salvas para consulta futura.
- ✅ **Persistência de Dados:** O histórico é salvo em um arquivo JSON, garantindo que os dados não sejam perdidos.
- ✅ **Logs Detalhados:** Registro de todas as operações, requisições e erros em arquivos de log.
- ✅ **Estatísticas de Uso:** Análise de dados como a moeda mais convertida e o total de conversões.
- ✅ **Interface Interativa:** Um menu de console claro e fácil de usar.
- ✅ **Suporte a Múltiplas APIs:** Integração com `ExchangeRate-API`, `OpenExchangeRates` e `CoinGecko`.
- ✅ **Fallback Automático:** Se a API principal de moedas fiduciárias falha, uma API secundária é utilizada automaticamente.
- ✅ **Seleção de API:** O usuário pode escolher a API de preferência para moedas fiduciárias.

## 🛠️ Tecnologias Utilizadas
- **Java 17:** Linguagem principal da aplicação.
- **Gson 2.10.1:** Para manipulação de dados JSON.
- **HttpClient (Java 11+):** Para realizar requisições HTTP às APIs.
- **APIs de Câmbio:**
  - `ExchangeRate-API`: Fonte principal para taxas de moedas fiduciárias.
  - `OpenExchangeRates`: Fonte secundária (fallback) para moedas fiduciárias.
  - `CoinGecko`: Fonte para cotações de criptomoedas.
- **java.util.logging:** Para o sistema de logs da aplicação.

## 🔄 Fluxogramas

### FLUXO PRINCIPAL DA APLICAÇÃO
```mermaid
flowchart TD
    A[Início] --> B[Exibir Menu Principal]
    B --> C{Opção Selecionada}
    C -->|1| D[Converter Moeda Fiduciária]
    C -->|2| E[Converter Criptomoeda para Moeda Fiduciária]
    C -->|3| F[Converter Criptomoeda para Criptomoeda]
    C -->|4| G[Ver Histórico]
    C -->|5| H[Limpar Histórico]
    C -->|6| I[Ver Taxas Disponíveis]
    C -->|7| J[Ver Estatísticas]
    C -->|8| K[Selecionar API de Câmbio]
    C -->|9| L[Sair]

    D --> M[Selecionar Moeda Origem]
    M --> N[Selecionar Moeda Destino]
    N --> O[Informar Valor]
    O --> P[Consultar API]
    P --> Q{Sucesso?}
    Q -->|Sim| R[Calcular Conversão]
    Q -->|Não| S[Tentar API Fallback]
    S --> P
    R --> T[Exibir Resultado]
    T --> U[Salvar no Histórico]
    U --> V[Registrar Log]
    V --> B

    E --> W[Selecionar Criptomoeda Origem]
    W --> X[Selecionar Moeda Destino]
    X --> O

    F --> Y[Selecionar Criptomoeda Origem]
    Y --> Z[Selecionar Criptomoeda Destino]
    Z --> O

    G --> AA[Carregar Histórico]
    AA --> BB[Exibir Últimas 10]
    BB --> B
    
    H --> CC[Confirmar Ação]
    CC --> DD[Limpar Dados]
    DD --> B
    
    I --> EE[Exibir Moedas e Criptomoedas]
    EE --> B
    
    J --> FF[Calcular Estatísticas]
    FF --> GG[Exibir Dados]
    GG --> B
    
    K --> HH[Apresentar Opções de API]
    HH --> II[Usuário Seleciona API]
    II --> JJ[Re-inicializar Serviços com Nova API]
    JJ --> B
    
    L --> KK[Salvar Estado]
    KK --> LL[Fim]
```

### ESTRUTURA DE CLASSES
```mermaid
classDiagram
    class Main {
        +main(String[] args)
    }
    
    class MenuPrincipal {
        -Scanner scanner
        -ConversorMoedas conversor
        -HistoricoConversao historico
        -Estatisticas estatisticas
        -String preferredApi
        +exibirMenu()
        +executar()
        -selecionarApi()
        -realizarConversao()
        -realizarConversaoCripto()
        -realizarConversaoCriptoParaCripto()
        -verHistorico()
        -limparHistorico()
        -verTaxasDisponiveis()
        -verEstatisticas()
        -inicializarServicos()
        -clearScreen()
    }
    
    class Moeda {
        <<enumeration>>
        ARS, BOB, BRL, CLP, COP, USD, EUR, GBP, JPY, AUD, CAD, CHF, CNY, MXN, INR
        BTC, ETH, USDT, BNB, SOL, XRP, USDC, ADA, AVAX, DOGE
        -String codigo
        -String nomeCompleto
        -String simbolo
        +getCodigo()
        +getNomeCompleto()
        +getSimbolo()
        +isCrypto()
        +isFiat()
        +fromCodigo(String)
    }
    
    class TaxaCambio {
        -Moeda origem
        -Moeda destino
        -double taxa
        -LocalDateTime timestamp
    }
    
    class ConversaoResultado {
        -double valorOriginal
        -Moeda moedaOrigem
        -double valorConvertido
        -Moeda moedaDestino
        -double taxaUtilizada
        -LocalDateTime dataHora
    }
    
    class ConversorMoedas {
        -ApiTaxaCambio apiService
        -HistoricoConversao historico
        +converter(double, Moeda, Moeda)
    }
    
    class ApiTaxaCambioService {
        -ApiTaxaCambio primaryApi
        -ApiTaxaCambio fallbackApi
        -ApiTaxaCambio coinGeckoApi
        -Map~String,TaxaCambio~ cache
        -String preferredApiName
        +ApiTaxaCambioService(ApiConfig, String)
        +obterTaxaCambio(Moeda, Moeda)
        -obterTaxaCambioCripto(Moeda, Moeda)
        -isCacheValid(TaxaCambio)
        +limparCacheExpirado()
    }

    class ApiTaxaCambio {
        <<interface>>
        obterTaxaCambio(Moeda, Moeda)
    }
    
    class ExchangeRateApiImpl {
        -HttpClient client
        -ApiConfig apiConfig
        -JsonParser jsonParser
        +obterTaxaCambio(Moeda, Moeda)
    }

    class OpenExchangeRatesApiImpl {
        -HttpClient client
        -Gson gson
        -String appId
        +obterTaxaCambio(Moeda, Moeda)
    }

    class CoinGeckoApiImpl {
        -HttpClient client
        -Gson gson
        -String apiKey
        +obterTaxaCambio(Moeda, Moeda)
    }
    
    class HistoricoConversao {
        -List~ConversaoResultado~ conversoes
        +adicionarConversao(ConversaoResultado)
        +getUltimasConversoes(int)
        +getTodasConversoes()
        +limparHistorico()
        +estaVazio()
        -salvarEmArquivo()
        -carregarDeArquivo()
    }

    class Estatisticas {
        -HistoricoConversao historico
        -Scanner scanner
        +exibirEstatisticas()
    }

    class InterfaceConversao {
        -Logger logger
        +exibirMenuSelecaoMoeda()
        +exibirMenuSelecaoCripto()
        +selecionarMoeda(Scanner, String)
        +selecionarCripto(Scanner, String)
        +solicitarValor(Scanner)
        +realizarConversao(Scanner, ConversorMoedas)
        +realizarConversaoCripto(Scanner, ConversorMoedas)
        +realizarConversaoCriptoParaCripto(Scanner, ConversorMoedas)
        +exibirHistorico(Scanner, HistoricoConversao)
        +limparHistorico(Scanner, HistoricoConversao)
        -exportarHistoricoParaTxt(List)
    }

    class ApiConfig {
        -ApiConfig instance
        -String apiKey
        -String openExchangeRatesAppId
        -String coinGeckoApiKey
        -String BASE_URL
        -String COINGECKO_BASE_URL
        +getInstance()
        -loadApiKey()
        +getApiKey()
        +getOpenExchangeRatesAppId()
        +getCoinGeckoApiKey()
        +buildPairConversionUrl(String, String)
        +buildCoinGeckoSimplePriceUrl(String, String)
    }

    class JsonParser {
        -Gson gson
        +parseConversionRate(String, String, String)
    }

    class LoggerUtil {
        -String LOG_FILE_PATH
        -int MAX_LOG_FILE_SIZE
        -int MAX_LOG_FILE_COUNT
        +getLogger(String)
    }
    
    Main --> MenuPrincipal
    MenuPrincipal --> ConversorMoedas
    MenuPrincipal --> HistoricoConversao
    MenuPrincipal --> Estatisticas
    MenuPrincipal --> InterfaceConversao
    ConversorMoedas --> ApiTaxaCambioService
    ApiTaxaCambioService ..|> ApiTaxaCambio
    ApiTaxaCambioService --> ExchangeRateApiImpl
    ApiTaxaCambioService --> OpenExchangeRatesApiImpl
    ApiTaxaCambioService --> CoinGeckoApiImpl
    ExchangeRateApiImpl ..|> ApiTaxaCambio
    OpenExchangeRatesApiImpl ..|> ApiTaxaCambio
    CoinGeckoApiImpl ..|> ApiTaxaCambio
    ConversaoResultado --> Moeda
    TaxaCambio --> Moeda
    ApiTaxaCambioService --> ApiConfig
    ExchangeRateApiImpl --> ApiConfig
    CoinGeckoApiImpl --> ApiConfig
    ExchangeRateApiImpl --> JsonParser
    ApiTaxaCambioService --> LoggerUtil
    MenuPrincipal --> LoggerUtil
    InterfaceConversao --> LoggerUtil
    HistoricoConversao --> LoggerUtil
    ConversorMoedas --> LoggerUtil
    ApiConfig --> LoggerUtil
```

## 📦 Estrutura do Projeto
```
conversor-moedas/
├── .env.example
├── .gitignore
├── API_SETUP.md
├── pom.xml
├── README.md
├── TESTES.md
├── data/
│   ├── historico.json
│   └── historico.json.bak
├── docs/
│   ├── capturas/
│   └── fluxogramas/
├── logs/
│   └── conversoes.log
└── src/
    └── main/
        └── java/
            └── com/
                └── conversor/
                    ├── Estatisticas.java
                    ├── InterfaceConversao.java
                    ├── Main.java
                    ├── MenuPrincipal.java
                    ├── models/
                    │   ├── ConversaoResultado.java
                    │   ├── HistoricoConversao.java
                    │   ├── Moeda.java
                    │   └── TaxaCambio.java
                    ├── services/
                    │   ├── ApiTaxaCambio.java
                    │   ├── ApiTaxaCambioService.java
                    │   ├── CoinGeckoApiImpl.java
                    │   ├── ConversorMoedas.java
                    │   ├── ExchangeRateApiImpl.java
                    │   └── OpenExchangeRatesApiImpl.java
                    └── utils/
                        ├── ApiConfig.java
                        ├── JsonParser.java
                        └── LoggerUtil.java
```

## 🚀 Como Executar

### Pré-requisitos
- **Java JDK 17** ou superior
- Conexão com a internet
- Chaves de API para `ExchangeRate-API`, `OpenExchangeRates` e `CoinGecko`

### Configuração
1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/dessima/conversor-moedas.git
    ```
2.  **Obtenha as chaves de API:**
    - `ExchangeRate-API`: [exchangerate-api.com](https://www.exchangerate-api.com/)
    - `OpenExchangeRates`: [openexchangerates.org](https://openexchangerates.org/)
    - `CoinGecko`: [coingecko.com/api](https://www.coingecko.com/en/api)
3.  **Configure as variáveis de ambiente:**
    Crie um arquivo `.env` na raiz do projeto ou defina as variáveis no seu sistema. Consulte o `API_SETUP.md` para mais detalhes.
    ```
    EXCHANGERATE_API_KEY=SUA_CHAVE_AQUI
    OPENEXCHANGERATES_APP_ID=SEU_APP_ID_AQUI
    COINGECKO_API_KEY=SUA_CHAVE_AQUI
    ```
4.  **Instale as dependências:**
    Se estiver usando Maven, execute:
    ```bash
    mvn install
    ```

### Execução
Compile e execute a aplicação a partir da raiz do projeto:
```bash
javac -cp .:gson-2.10.1.jar src/main/java/com/conversor/*.java
java -cp .:gson-2.10.1.jar com.conversor.Main
```

---

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