# Testes Manuais do Conversor de Moedas

Este documento detalha os cenários de teste manual para o Conversor de Moedas e os resultados esperados/obtidos.

## Cenários de Teste

### 1. Teste de Conversão Normal
- **Descrição:** Converter um valor positivo entre duas moedas válidas (ex: USD para BRL).
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda'.
  3. Escolher USD como moeda de origem.
  4. Escolher BRL como moeda de destino.
  5. Digitar um valor (ex: 100).
- **Resultado Esperado:** Exibição do valor convertido corretamente, com a taxa utilizada e símbolos das moedas. A conversão deve ser registrada no histórico.
- **Resultado Obtido:** [A preencher]

### 2. Teste com Valor Zero
- **Descrição:** Tentar converter um valor zero.
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda'.
  3. Escolher moedas de origem e destino.
  4. Digitar 0 como valor.
- **Resultado Esperado:** Mensagem de erro indicando que o valor deve ser maior que zero.
- **Resultado Obtido:** [A preencher]

### 3. Teste com Valor Negativo
- **Descrição:** Tentar converter um valor negativo.
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda'.
  3. Escolher moedas de origem e destino.
  4. Digitar um valor negativo (ex: -50).
- **Resultado Esperado:** Mensagem de erro indicando que o valor deve ser maior que zero.
- **Resultado Obtido:** [A preencher]

### 4. Teste com Mesma Moeda (origem = destino)
- **Descrição:** Converter uma moeda para ela mesma.
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda'.
  3. Escolher USD como moeda de origem.
  4. Escolher USD como moeda de destino.
  5. Digitar um valor (ex: 75).
- **Resultado Esperado:** Exibição do mesmo valor, com taxa de 1.0. Registro no histórico.
- **Resultado Obtido:** [A preencher]

### 5. Teste sem Conexão Internet
- **Descrição:** Verificar o comportamento da aplicação sem conexão com a internet.
- **Passos:**
  1. Desconectar o computador da internet.
  2. Iniciar a aplicação.
  3. Tentar realizar uma conversão.
- **Resultado Esperado:** Mensagem de erro amigável sobre falha na comunicação com a API. O log deve registrar o erro.
- **Resultado Obtido:** [A preencher]

### 6. Teste com API Key Inválida
- **Descrição:** Configurar uma chave de API inválida e tentar uma conversão.
- **Passos:**
  1. Editar `config.properties` e inserir uma chave de API inválida para `api.key`.
  2. Iniciar a aplicação.
  3. Tentar realizar uma conversão.
- **Resultado Esperado:** Mensagem de erro amigável sobre acesso negado à API ou chave inválida. O log deve registrar o erro.
- **Resultado Obtido:** [A preencher]

### 7. Teste de Histórico Vazio
- **Descrição:** Verificar a exibição do histórico quando não há conversões.
- **Passos:**
  1. Iniciar a aplicação (com histórico limpo ou vazio).
  2. Selecionar a opção 'Ver histórico de conversões'.
- **Resultado Esperado:** Mensagem indicando que o histórico está vazio.
- **Resultado Obtido:** [A preencher]

### 8. Teste de Histórico com 100+ Entradas
- **Descrição:** Realizar múltiplas conversões para preencher o histórico e verificar a exibição das últimas 10.
- **Passos:**
  1. Realizar mais de 10 conversões diferentes.
  2. Selecionar a opção 'Ver histórico de conversões'.
- **Resultado Esperado:** Exibição das últimas 10 conversões em formato tabular. As conversões mais antigas devem ter sido removidas da exibição (mas não do arquivo, se a persistência estiver funcionando).
- **Resultado Obtido:** [A preencher]

### 9. Teste de Cache de Taxas
- **Descrição:** Verificar se o cache está funcionando, reduzindo chamadas à API.
- **Passos:**
  1. Iniciar a aplicação.
  2. Realizar uma conversão (ex: USD para BRL).
  3. Realizar a *mesma* conversão novamente (USD para BRL).
- **Resultado Esperado:** A primeira conversão deve registrar uma chamada à API no log. A segunda conversão (se dentro do período de cache) não deve registrar uma nova chamada à API, mas sim uma mensagem de uso do cache.
- **Resultado Obtido:** [A preencher]

### 10. Teste de Persistência de Dados
- **Descrição:** Verificar se o histórico é salvo e carregado corretamente entre as execuções da aplicação.
- **Passos:**
  1. Iniciar a aplicação.
  2. Realizar algumas conversões.
  3. Sair da aplicação.
  4. Iniciar a aplicação novamente.
  5. Selecionar a opção 'Ver histórico de conversões'.
- **Resultado Esperado:** O histórico deve ser exibido com as conversões realizadas na sessão anterior.
- **Resultado Obtido:** [A preencher]

### 11. Teste de Fallback da API
- **Descrição:** Verificar se a aplicação usa a API de fallback quando a API primária falha.
- **Passos:**
  1. Configurar `config.properties` para usar uma API primária (ex: `api.preferred=EXCHANGERATE_API`).
  2. Inserir uma chave de API inválida para a API primária (`api.key=INVALIDA`).
  3. Iniciar a aplicação.
  4. Tentar realizar uma conversão.
- **Resultado Esperado:** A aplicação deve tentar a API primária, falhar, e então tentar a API de fallback. O log deve registrar a falha da primária e o uso da fallback.
- **Resultado Obtido:** [A preencher]

### 12. Teste de Exportação de Histórico para TXT
- **Descrição:** Verificar se o histórico é exportado corretamente para um arquivo TXT.
- **Passos:**
  1. Realizar algumas conversões.
  2. Selecionar a opção 'Ver histórico de conversões'.
  3. Escolher 's' para exportar o histórico.
- **Resultado Esperado:** Um arquivo TXT deve ser criado na pasta `logs` com o histórico formatado.
- **Resultado Obtido:** [A preencher]

### 13. Teste de Conversão de Criptomoeda para Moeda Fiduciária
- **Descrição:** Converter um valor de uma criptomoeda para uma moeda fiduciária (ex: BTC para USD).
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter Criptomoeda para Moeda Fiduciária'.
  3. Escolher BTC como moeda de origem.
  4. Escolher USD como moeda de destino.
  5. Digitar um valor (ex: 1).
- **Resultado Esperado:** Exibição do valor convertido corretamente, com a taxa utilizada e símbolos das moedas. A conversão deve ser registrada no histórico.
- **Resultado Obtido:** [A preencher]

### 14. Teste de Conversão de Criptomoeda para Criptomoeda
- **Descrição:** Converter um valor de uma criptomoeda para outra (ex: BTC para ETH).
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter Criptomoeda para Criptomoeda'.
  3. Escolher BTC como moeda de origem.
  4. Escolher ETH como moeda de destino.
  5. Digitar um valor (ex: 1).
- **Resultado Esperado:** Exibição do valor convertido corretamente, com a taxa utilizada e símbolos das moedas. A conversão deve ser registrada no histórico.
- **Resultado Obtido:** [A preencher]

### 15. Teste de Conversão de Moeda Fiduciária para Criptomoeda (Inválido)
- **Descrição:** Tentar converter um valor de uma moeda fiduciária para uma criptomoeda.
- **Passos:**
  1. Iniciar a aplicação.
  2. Selecionar a opção 'Converter moeda'.
  3. Escolher USD como moeda de origem.
  4. Escolher BTC como moeda de destino.
  5. Digitar um valor (ex: 100).
- **Resultado Esperado:** Mensagem de erro indicando que não é possível converter uma moeda fiduciária para uma criptomoeda com esta função.
- **Resultado Obtido:** [A preencher]