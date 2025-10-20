# Conversor de Moedas

## 📋 Descrição
O Conversor de Moedas é uma aplicação Java console que permite converter valores entre 15 moedas diferentes usando taxas de câmbio em tempo real. A aplicação oferece histórico de conversões, logs detalhados e estatísticas de uso. O usuário pode selecionar a API de câmbio preferencial (ExchangeRate-API ou OpenExchangeRates) no início da execução.

## ⚙️ Funcionalidades
- ✅ Conversão entre 15 moedas diferentes (ARS, BOB, BRL, CLP, COP, USD, EUR, GBP, JPY, AUD, CAD, CHF, CNY, MXN, INR)
- ✅ Taxas de câmbio atualizadas em tempo real
- ✅ Cache de taxas para otimizar performance
- ✅ Histórico completo de conversões
- ✅ Persistência de dados em arquivo JSON
- ✅ Sistema de logs detalhado
- ✅ Estatísticas de uso
- ✅ Interface interativa via console
- ✅ Suporte para múltiplas APIs de câmbio (ExchangeRate-API e OpenExchangeRates)
- ✅ Fallback automático entre APIs
- ✅ **Seleção de API preferencial pelo usuário**

## 🛠️ Tecnologias Utilizadas
- **Java JDK 17**: Linguagem de programação
- **Gson 2.10.1**: Serialização/desserialização JSON
- **HttpClient**: Requisições HTTP (Java 11+)
- **ExchangeRate-API**: API principal de taxas de câmbio
- **OpenExchangeRates**: API alternativa (fallback)
- **java.util.logging**: Sistema de logs
- **Scanner**: Interface de usuário via console

## 🔄 Fluxogramas

### FLUXO PRINCIPAL DA APLICAÇÃO
```mermaid
flowchart TD
    A[Início] --> B[Exibir Menu Principal]
    B --> C{Opção Selecionada}
    C -->|1| D[Converter Moeda]
    C -->|2| E[Ver Histórico]
    C -->|3| F[Limpar Histórico]
    C -->|4| G[Ver Taxas Disponíveis]
    C -->|5| H[Ver Estatísticas]
    C -->|6| I[Selecionar API de Câmbio]
    C -->|7| J[Sair]

    D --> K[Selecionar Moeda Origem]
    K --> L[Selecionar Moeda Destino]
    L --> M[Informar Valor]
    M --> N[Consultar API]
    N --> O{Sucesso?}
    O -->|Sim| P[Calcular Conversão]
    O -->|Não| Q[Tentar API Fallback]
    Q --> N
    P --> R[Exibir Resultado]
    R --> S[Salvar no Histórico]
    S --> T[Registrar Log]
    T --> B

    E --> U[Carregar Histórico]
    U --> V[Exibir Últimas 10]
    V --> B

    F --> W[Confirmar Ação]
    W --> X[Limpar Dados]
    X --> B

    H --> Y[Calcular Estatísticas]
    Y --> Z[Exibir Dados]
    Z --> B

    I --> AA[Apresentar Opções de API]
    AA --> BB[Usuário Seleciona API]
    BB --> CC[Re-inicializar Serviços com Nova API]
    CC --> B

    J --> DD[Salvar Estado]
    DD --> EE[Fim]
```

### FLUXO DE CONVERSÃO DETALHADO
```mermaid
sequenceDiagram
    participant U as Usuário
    participant M as Menu
    participant C as Conversor
    participant A as API Service
    participant H as Histórico
    participant L as Logger

    U->>M: Seleciona opção Converter
    M->>U: Solicita moeda origem
    U->>M: Informa USD
    M->>U: Solicita moeda destino
    U->>M: Informa BRL
    M->>U: Informa 100.00
    M->>C: converter(100, USD, BRL)
    C->>A: obterTaxaCambio(USD, BRL)
    
    alt Taxa em Cache
        A->>A: Buscar no cache
        A->>C: Retorna taxa cached
    else Cache expirado/inexistente
        A->>A: Fazer requisição HTTP
        A->>A: Parsear JSON
        A->>A: Armazenar em cache
        A->>C: Retorna taxa
    end
    
    C->>C: Calcular conversão
    C->>H: Adicionar ao histórico
    C->>L: Registrar log
    C->>M: Retorna resultado
    M->>U: Exibe: 100 USD = 495 BRL
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
        -String preferredApi
        +exibirMenu()
        +executar()
        +selecionarApi()
        -inicializarServicos()
    }
    
    class Moeda {
        <<enumeration>>
        USD
        BRL
        EUR
        +getCodigo()
        +getNome()
        +getSimbolo()
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
        -Map~String,TaxaCambio~ cache
        -String preferredApiName
        +ApiTaxaCambioService(ApiConfig, String)
        +obterTaxaCambio(Moeda, Moeda)
        -isCacheValid(TaxaCambio)
        +limparCacheExpirado()
    }

    class ApiTaxaCambio {
        <<interface>>
        obterTaxaCambio()
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
    
    class HistoricoConversao {
        -List~ConversaoResultado~ conversoes
        +adicionar(ConversaoResultado)
        +obterUltimas(int)
        +salvarEmArquivo()
        +carregarDeArquivo()
    }
    
    Main --> MenuPrincipal
    MenuPrincipal --> ConversorMoedas
    MenuPrincipal --> HistoricoConversao
    ConversorMoedas --> ApiTaxaCambioService
    ApiTaxaCambioService ..|> ApiTaxaCambio
    ApiTaxaCambioService --> ExchangeRateApiImpl
    ApiTaxaCambioService --> OpenExchangeRatesApiImpl
    ExchangeRateApiImpl ..|> ApiTaxaCambio
    OpenExchangeRatesApiImpl ..|> ApiTaxaCambio
    ConversaoResultado --> Moeda
    TaxaCambio --> Moeda
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
├── .git/
├── .idea/
├── data/
│   ├── historico.json
│   └── historico.json.bak
├── docs/
│   ├── capturas/
│   └── fluxogramas/
├── logs/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── conversor/
│                   ├── Estatisticas.java
│                   ├── InterfaceConversao.java
│                   ├── Main.java
│                   ├── MenuPrincipal.java
│                   ├── models/
│                   │   ├── ConversaoResultado.java
│                   │   ├── HistoricoConversao.java
│                   │   ├── Moeda.java
│                   │   └── TaxaCambio.java
│                   ├── services/
│                   │   ├── ApiTaxaCambio.java
│                   │   ├── ApiTaxaCambioService.java
│                   │   ├── ConversorMoedas.java
│                   │   ├── ExchangeRateApiImpl.java
│                   │   └── OpenExchangeRatesApiImpl.java
│                   └── utils/
│                       ├── ApiConfig.java
│                       ├── JsonParser.java
│                       └── LoggerUtil.java
└── target/
    ├── classes/
    ├── generated-sources/
    ├── maven-archiver/
    └── maven-status/
```

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 17 ou superior instalado
- Conexão com internet
- Chaves das APIs ExchangeRate e OpenExchangeRates (ambas gratuitas, mas a OpenExchangeRates requer registro para um App ID)

### Configuração
1. Clone o repositório.
2. Obtenha sua chave da API ExchangeRate em https://www.exchangerate-api.com/.
3. Obtenha seu App ID da OpenExchangeRates em https://openexchangerates.org/ (registro gratuito).
4. **Defina as variáveis de ambiente** para as chaves das APIs. Crie um arquivo `.env` na raiz do projeto (não versionado) ou defina-as diretamente no seu sistema operacional:
   ```
   EXCHANGERATE_API_KEY=SUA_CHAVE_EXCHANGERATE_AQUI
   OPENEXCHANGERATES_APP_ID=SEU_APP_ID_OPENEXCHANGERATES_AQUI
   ```
   Consulte o arquivo `API_SETUP.md` para instruções detalhadas sobre como configurar essas variáveis.
5. Configure a biblioteca Gson no IntelliJ (se estiver usando IDE).

### Execução
```bash
javac -cp .:gson-2.10.1.jar src/main/java/com/conversor/*.java
java -cp .:gson-2.10.1.jar com.conversor.Main
```

## 📸 Capturas de Tela
Para adicionar as capturas de tela, salve as imagens na pasta `docs/capturas/` e atualize esta seção com os links.

- `menu-principal.png` - Menu inicial com todas as opções
- `conversao-sucesso.png` - Exemplo de conversão bem-sucedida (USD para BRL)
- `historico.png` - Visualização do histórico com múltiplas conversões
- `estatisticas.png` - Tela de estatísticas completa
- `erro-api.png` - Exemplo de tratamento de erro amigável

## 🎥 Vídeo Demonstrativo
Para adicionar o vídeo demonstrativo, faça o upload para uma plataforma (ex: YouTube, Vimeo) e insira o link aqui.

[Link do vídeo ou GIF animado mostrando uso da aplicação - A ser adicionado pelo usuário]

## 👨‍💻 Autor
**José Anderson da Silva Costa**

<a href="https://www.linkedin.com/in/dessim"><img src="https://cdn.worldvectorlogo.com/logos/linkedin-icon.svg" alt="LinkedIn" width="24" height="24"></a>
<a href="https://github.com/dessima"><img src="https://cdn4.iconfinder.com/data/icons/iconsimple-logotypes/512/github-512.png" alt="GitHub" width="24" height="24"></a>

## 📄 Licença
Este projeto está sob a licença MIT.