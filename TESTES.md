# Testes Manuais do Conversor de Moedas

Este documento detalha os cenários de teste manual para o Conversor de Moedas e os resultados esperados/obtidos.

## Cenários de Teste

### 1. Teste de Conversão Normal (Moeda Fiduciária para Fiduciária)
- **Descrição:** Converter um valor positivo entre duas moedas fiduciárias válidas (ex: USD para BRL).
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda' (Opção 1).
  3. Escolher USD como moeda de origem na lista.
  4. Escolher BRL como moeda de destino na lista.
  5. Digitar um valor (ex: 100).
- **Resultado Esperado:** Exibição do valor convertido corretamente, com a taxa utilizada e símbolos das moedas. A conversão deve ser registrada no histórico. O log deve indicar a API utilizada.
- **Resultado Obtido:** [A preencher]

### 2. Teste com Valor Zero
- **Descrição:** Tentar converter um valor zero.
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda' (Opção 1).
  3. Escolher moedas de origem e destino.
  4. Digitar 0 como valor.
- **Resultado Esperado:** Mensagem de erro amigável indicando que o valor deve ser maior que zero.
- **Resultado Obtido:** [A preencher]

### 3. Teste com Valor Negativo
- **Descrição:** Tentar converter um valor negativo.
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda' (Opção 1).
  3. Escolher moedas de origem e destino.
  4. Digitar um valor negativo (ex: -50).
- **Resultado Esperado:** Mensagem de erro amigável indicando que o valor deve ser maior que zero.
- **Resultado Obtido:** [A preencher]

### 4. Teste com Mesma Moeda (origem = destino)
- **Descrição:** Converter uma moeda para ela mesma.
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda' (Opção 1).
  3. Escolher USD como moeda de origem.
  4. Escolher USD como moeda de destino.
  5. Digitar um valor (ex: 75).
- **Resultado Esperado:** Exibição do mesmo valor, com taxa de 1.0. Registro no histórico. O log deve indicar que foi uma conversão de mesma moeda.
- **Resultado Obtido:** [A preencher]

### 5. Teste sem Conexão Internet
- **Descrição:** Verificar o comportamento da aplicação sem conexão com a internet.
- **Passos:**
  1. Desconectar o computador da internet.
  2. Iniciar a aplicação.
  3. Tentar realizar uma conversão (Opção 1, 7 ou 8).
- **Resultado Esperado:** Mensagem de erro amigável sobre falha na comunicação com a API. O log deve registrar o erro de `IOException` ou `InterruptedException`.
- **Resultado Obtido:** [A preencher]

### 6. Teste com API Key Inválida
- **Descrição:** Configurar uma chave de API inválida e tentar uma conversão.
- **Passos:**
  1. Editar as variáveis de ambiente ou `config.properties` (se usado) e inserir uma chave de API inválida para `EXCHANGERATE_API_KEY` ou `OPENEXCHANGERATES_APP_ID`.
  2. Iniciar a aplicação.
  3. Tentar realizar uma conversão (Opção 1).
- **Resultado Esperado:** Mensagem de erro amigável sobre acesso negado à API ou chave inválida (Erro 401/403). O log deve registrar o erro.
- **Resultado Obtido:** [A preencher]

### 7. Teste de Histórico Vazio
- **Descrição:** Verificar a exibição do histórico quando não há conversões.
- **Passos:**
  1. Iniciar a aplicação (com histórico limpo ou vazio).
  2. Selecionar a opção 'Ver histórico de conversões' (Opção 2).
- **Resultado Esperado:** Mensagem indicando que o histórico está vazio e aguardar ENTER para voltar ao menu.
- **Resultado Obtido:** [A preencher]

### 8. Teste de Histórico com Múltiplas Entradas
- **Descrição:** Realizar múltiplas conversões para preencher o histórico e verificar a exibição.
- **Passos:**
  1. Realizar pelo menos 5 conversões diferentes.
  2. Selecionar a opção 'Ver histórico de conversões' (Opção 2).
- **Resultado Esperado:** Exibição das conversões em formato tabular, ordenadas da mais antiga para a mais recente (ou vice-versa, conforme implementação). As últimas 10 conversões devem ser visíveis.
- **Resultado Obtido:** [A preencher]

### 9. Teste de Cache de Taxas
- **Descrição:** Verificar se o cache está funcionando, reduzindo chamadas à API para taxas recentes.
- **Passos:**
  1. Iniciar a aplicação.
  2. Realizar uma conversão (ex: USD para BRL).
  3. Realizar a *mesma* conversão novamente (USD para BRL) dentro de 1 hora.
- **Resultado Esperado:** A primeira conversão deve registrar uma chamada à API no log. A segunda conversão não deve registrar uma nova chamada à API, mas sim uma mensagem de uso do cache no log.
- **Resultado Obtido:** [A preencher]

### 10. Teste de Persistência de Dados
- **Descrição:** Verificar se o histórico é salvo e carregado corretamente entre as execuções da aplicação.
- **Passos:**
  1. Iniciar a aplicação.
  2. Realizar algumas conversões.
  3. Sair da aplicação (Opção 9).
  4. Iniciar a aplicação novamente.
  5. Selecionar a opção 'Ver histórico de conversões' (Opção 2).
- **Resultado Esperado:** O histórico deve ser exibido com as conversões realizadas na sessão anterior.
- **Resultado Obtido:** [A preencher]

### 11. Teste de Fallback da API
- **Descrição:** Verificar se a aplicação usa a API de fallback quando a API primária falha.
- **Passos:**
  1. Certifique-se de que `OPENEXCHANGERATES_APP_ID` esteja configurado com uma chave válida.
  2. Configurar a variável de ambiente `api.preferred` para `EXCHANGERATE_API` (ou deixar como padrão).
  3. Inserir uma chave de API *inválida* para `EXCHANGERATE_API_KEY`.
  4. Iniciar a aplicação.
  5. Tentar realizar uma conversão (Opção 1).
- **Resultado Esperado:** O log deve registrar a falha da API primária (ExchangeRate-API) e o uso bem-sucedido da API de fallback (OpenExchangeRates). A conversão deve ser realizada com sucesso.
- **Resultado Obtido:** [A preencher]

### 12. Teste de Exportação de Histórico para TXT
- **Descrição:** Verificar se o histórico é exportado corretamente para um arquivo TXT.
- **Passos:**
  1. Realizar algumas conversões.
  2. Selecionar a opção 'Ver histórico de conversões' (Opção 2).
  3. Escolher 's' para exportar o histórico quando solicitado.
- **Resultado Esperado:** Um arquivo TXT deve ser criado na pasta `logs` com o histórico formatado. Uma mensagem de sucesso deve ser exibida no console.
- **Resultado Obtido:** [A preencher]

### 13. Teste de Conversão de Criptomoeda para Moeda Fiduciária
- **Descrição:** Converter um valor de uma criptomoeda para uma moeda fiduciária (ex: BTC para USD).
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter Criptomoeda para Moeda' (Opção 7).
  3. Escolher BTC como criptomoeda de origem na lista.
  4. Escolher USD como moeda de destino na lista.
  5. Digitar um valor (ex: 1).
- **Resultado Esperado:** Exibição do valor convertido corretamente, com a taxa utilizada e símbolos das moedas. A conversão deve ser registrada no histórico. O log deve indicar o uso da CoinGecko API.
- **Resultado Obtido:** [A preencher]

### 14. Teste de Conversão de Criptomoeda para Criptomoeda
- **Descrição:** Converter um valor de uma criptomoeda para outra (ex: BTC para ETH).
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter Criptomoeda para Criptomoeda' (Opção 8).
  3. Escolher BTC como criptomoeda de origem na lista.
  4. Escolher ETH como criptomoeda de destino na lista.
  5. Digitar um valor (ex: 1).
- **Resultado Esperado:** Exibição do valor convertido corretamente, com a taxa utilizada e símbolos das moedas. A conversão deve ser registrada no histórico. O log deve indicar o uso da CoinGecko API.
- **Resultado Obtido:** [A preencher]

### 15. Teste de Conversão de Moeda Fiduciária para Criptomoeda (Inválido)
- **Descrição:** Tentar converter um valor de uma moeda fiduciária para uma criptomoeda usando a opção de conversão fiduciária.
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda' (Opção 1).
  3. Escolher USD como moeda de origem.
  4. Tentar escolher BTC como moeda de destino (não deve aparecer na lista de moedas fiduciárias).
- **Resultado Esperado:** A criptomoeda não deve ser listada como opção de destino para conversão fiduciária. Se, por algum erro, for possível selecionar, uma mensagem de erro deve ser exibida indicando que a conversão não é suportada por esta função.
- **Resultado Obtido:** [A preencher]

### 16. Teste de Seleção de API
- **Descrição:** Verificar se a seleção de API preferencial funciona corretamente.
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Selecionar API de Câmbio' (Opção 6).
  3. Escolher 'OpenExchangeRates' (Opção 2).
  4. Voltar ao menu principal e realizar uma conversão (Opção 1).
- **Resultado Esperado:** O log deve indicar que a OpenExchangeRates API está sendo usada como primária para a conversão.
- **Resultado Obtido:** [A preencher]

### 17. Teste de Visualização de Estatísticas
- **Descrição:** Verificar se as estatísticas são exibidas corretamente.
- **Passos:**
  1. Realizar algumas conversões.
  2. Selecionar a opção 'Ver Estatísticas' (Opção 5).
- **Resultado Esperado:** Exibição do total de conversões, moeda mais convertida, valor total convertido por moeda, média de valores convertidos e o gráfico ASCII de frequência de moedas. Aguardar ENTER para voltar ao menu.
- **Resultado Obtido:** [A preencher]

### 18. Teste de Limpeza de Histórico
- **Descrição:** Verificar se o histórico é limpo corretamente.
- **Passos:**
  1. Realizar algumas conversões.
  2. Selecionar a opção 'Limpar histórico' (Opção 3).
  3. Confirmar a limpeza com 's'.
  4. Selecionar a opção 'Ver histórico de conversões' (Opção 2).
- **Resultado Esperado:** O histórico deve estar vazio. O log deve registrar a limpeza do histórico.
- **Resultado Obtido:** [A preencher]
