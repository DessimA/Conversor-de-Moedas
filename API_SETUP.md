# 🔑 Configuração das APIs de Câmbio (ExchangeRate-API, OpenExchangeRates e CoinGecko)

Este documento detalha o processo para obter e configurar as chaves de API necessárias para o projeto Conversor de Moedas, garantindo a segurança e o correto funcionamento da aplicação, que suporta múltiplas fontes de dados para moedas fiduciárias e criptomoedas.

---

## 1. Registro na ExchangeRate-API (API Principal para Moedas Fiduciárias)

Para utilizar o serviço de taxas de câmbio fiduciárias, você precisará de uma chave de API gratuita. Siga os passos abaixo para se registrar:

1.  Acesse o site oficial: [https://www.exchangerate-api.com/](https://www.exchangerate-api.com/)
2.  Clique em "Sign Up" ou "Get Free API Key".
3.  Preencha o formulário de registro com suas informações (nome, e-mail, senha).
4.  Confirme seu e-mail (verifique sua caixa de entrada e spam).

## 2. Obtenção da Chave da API Gratuita (ExchangeRate-API)

Após o registro e confirmação do e-mail:

1.  Faça login na sua conta na ExchangeRate-API.
2.  Sua chave de API será exibida no painel de controle (dashboard).
3.  **Copie esta chave; ela será necessária para configurar o projeto.**

## 3. Registro na OpenExchangeRates (API de Fallback para Moedas Fiduciárias)

Para utilizar a OpenExchangeRates como API alternativa ou de fallback para moedas fiduciárias, você também precisará de um App ID gratuito. Siga os passos abaixo:

1.  Acesse o site oficial: [https://openexchangerates.org/](https://openexchangerates.org/)
2.  Clique em "Signup" ou "Get your Free API Key".
3.  Preencha o formulário de registro.
4.  Confirme seu e-mail.

## 4. Obtenção do App ID Gratuito (OpenExchangeRates)

Após o registro e confirmação do e-mail:

1.  Faça login na sua conta na OpenExchangeRates.
2.  Seu App ID será exibido no painel de controle.
3.  **Copie este App ID; ele será necessário para configurar o projeto.**

## 5. Registro na CoinGecko (API para Criptomoedas)

Para obter as cotações de criptomoedas, você precisará de uma chave de API da CoinGecko.

1.  Acesse o site oficial: [https://www.coingecko.com/en/api](https://www.coingecko.com/en/api)
2.  Clique em "Get your free API key".
3.  Crie uma conta ou faça login.
4.  Sua chave de API será exibida no seu painel de controle.

## 6. Limites das APIs (Planos Gratuitos)

### ExchangeRate-API:
*   **1.500 requisições por mês.**
*   As taxas de câmbio são atualizadas a cada 24-48 horas.

### OpenExchangeRates:
*   **1.000 requisições por mês.**
*   As taxas de câmbio são atualizadas a cada 1 hora.

### CoinGecko:
*   Limites variam conforme o plano. O plano gratuito geralmente permite um número razoável de requisições por minuto/dia.

É importante monitorar o uso para não exceder o limite. O projeto implementa um cache inteligente e um sistema de fallback entre as APIs para moedas fiduciárias, além de usar a CoinGecko especificamente para criptomoedas, otimizando o número de requisições e evitando bloqueios.

## 7. Armazenamento Seguro das Chaves de API

**ATENÇÃO:** **NUNCA** adicione suas chaves de API diretamente no código-fonte ou as envie para um repositório público (como GitHub). Para este projeto, as chaves das APIs serão carregadas de **variáveis de ambiente**.

### 🔑 Configurando `EXCHANGERATE_API_KEY` (para ExchangeRate-API)

Defina a variável de ambiente `EXCHANGERATE_API_KEY` com a sua chave da API obtida no passo 2.

*   **Windows (CMD):**
    ```cmd
    set EXCHANGERATE_API_KEY=SUA_CHAVE_AQUI
    ```
*   **Windows (PowerShell):**
    ```powershell
    $env:EXCHANGERATE_API_KEY="SUA_CHAVE_AQUI"
    ```
*   **Linux/macOS:**
    ```bash
    export EXCHANGERATE_API_KEY=SUA_CHAVE_AQUI
    ```

### 🔑 Configurando `OPENEXCHANGERATES_APP_ID` (para OpenExchangeRates)

Defina a variável de ambiente `OPENEXCHANGERATES_APP_ID` com o seu App ID da OpenExchangeRates obtido no passo 4.

*   **Windows (CMD):**
    ```cmd
    set OPENEXCHANGERATES_APP_ID=SEU_APP_ID_AQUI
    ```
*   **Windows (PowerShell):**
    ```powershell
    $env:OPENEXCHANGERATES_APP_ID="SEU_APP_ID_AQUI"
    ```
*   **Linux/macOS:**
    ```bash
    export OPENEXCHANGERATES_APP_ID=SEU_APP_ID_AQUI
    ```

### 🔑 Configurando `COINGECKO_API_KEY` (para CoinGecko)

Defina a variável de ambiente `COINGECKO_API_KEY` com a sua chave da API obtida no passo 5.

*   **Windows (CMD):**
    ```cmd
    set COINGECKO_API_KEY=SUA_CHAVE_AQUI
    ```
*   **Windows (PowerShell):
    ```powershell
    $env:COINGECKO_API_KEY="SUA_CHAVE_AQUI"
    ```
*   **Linux/macOS:**
    ```bash
    export COINGECKO_API_KEY=SUA_CHAVE_AQUI
    ```

---

### ⚠️ **Importante:**

- Para que as variáveis de ambiente sejam reconhecidas pela aplicação, elas devem ser definidas no terminal **antes** de iniciar a aplicação Java. 
- Para persistência (manter as variáveis após fechar o terminal), adicione-as ao seu perfil de usuário ou script de inicialização do sistema operacional.

### ⚙️ Configuração da API Preferencial em Tempo de Execução

- O projeto permite selecionar a API preferencial para conversões de moedas fiduciárias (ExchangeRate-API ou OpenExchangeRates) através do menu interativo da aplicação. 
  - Por padrão, a `ExchangeRate-API` é utilizada. Se a API preferencial falhar, o sistema tentará automaticamente a API de fallback configurada. 
- As conversões de criptomoedas sempre utilizarão a CoinGecko API.
