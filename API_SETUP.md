# Configuração da API ExchangeRate-API

Este documento explica como obter e configurar sua chave de API para o projeto Conversor de Moedas.

## 1. Registro na ExchangeRate-API

Para utilizar o serviço de taxas de câmbio, você precisará de uma chave de API gratuita. Siga os passos abaixo para se registrar:

1.  Acesse o site oficial: [https://www.exchangerate-api.com/](https://www.exchangerate-api.com/)
2.  Clique em "Sign Up" ou "Get Free API Key".
3.  Preencha o formulário de registro com suas informações (nome, e-mail, senha).
4.  Confirme seu e-mail (verifique sua caixa de entrada e spam).

## 2. Obtenção da Chave da API Gratuita

Após o registro e confirmação do e-mail:

1.  Faça login na sua conta na ExchangeRate-API.
2.  Sua chave de API será exibida no painel de controle (dashboard).
3.  Copie esta chave; ela será necessária para configurar o projeto.

## 3. Limites da API (Plano Gratuito)

O plano gratuito da ExchangeRate-API oferece:

*   **1.500 requisições por mês.**
*   As taxas de câmbio são atualizadas a cada 24-48 horas.

É importante monitorar o uso para não exceder o limite. O projeto implementará um cache para otimizar o número de requisições.

## 4. Estrutura da URL de Consulta por Pares

A API permite consultar taxas de câmbio entre pares de moedas. A estrutura básica da URL para obter a taxa de conversão entre duas moedas é:

`https://v6.exchangerate-api.com/v6/SUA_CHAVE_AQUI/pair/MOEDA_ORIGEM/MOEDA_DESTINO`

Exemplo (USD para BRL):
`https://v6.exchangerate-api.com/v6/SUA_CHAVE_AQUI/pair/USD/BRL`

## 5. Armazenamento Seguro da Chave da API

**NUNCA** adicione sua chave de API diretamente no código-fonte ou a envie para um repositório público (como GitHub).

Para este projeto, as chaves das APIs serão carregadas de variáveis de ambiente.

### Para ExchangeRate-API:
1.  Defina a variável de ambiente `EXCHANGERATE_API_KEY` com a sua chave da API.
    -   **Windows (CMD):** `set EXCHANGERATE_API_KEY=SUA_CHAVE_AQUI`
    -   **Windows (PowerShell):** `$env:EXCHANGERATE_API_KEY="SUA_CHAVE_AQUI"`
    -   **Linux/macOS:** `export EXCHANGERATE_API_KEY=SUA_CHAVE_AQUI`

### Para OpenExchangeRates (API de fallback):
1.  Defina a variável de ambiente `OPENEXCHANGERATES_APP_ID` com o seu App ID da OpenExchangeRates.
    -   **Windows (CMD):** `set OPENEXCHANGERATES_APP_ID=SEU_APP_ID_AQUI`
    -   **Windows (PowerShell):** `$env:OPENEXCHANGERATES_APP_ID="SEU_APP_ID_AQUI"`
    -   **Linux/macOS:** `export OPENEXCHANGERATES_APP_ID=SEU_APP_ID_AQUI`

**Importante:** Para que as variáveis de ambiente sejam reconhecidas pela aplicação, elas devem ser definidas no terminal *antes* de iniciar a aplicação Java. Para persistência, adicione-as ao seu perfil de usuário ou script de inicialização do sistema.