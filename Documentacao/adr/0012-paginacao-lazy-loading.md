# ADR 0012: Paginação de Dados com LazyDataModel

## Status
Aceito

## Data
2026-04-12

## Contexto
Telas que listam grandes volumes de dados (como a de Objetivos) carregavam todos os registros do banco de dados em memória de uma só vez. Em um cenário real com milhares de registros, isso causaria sérios problemas de performance e risco de erro de estouro de memória (*OutOfMemory*).

## Decisão
Implementamos a paginação real no banco de dados utilizando o padrão **LazyDataModel** do PrimeFaces:
1.  **Repositórios JPA**: Adicionamos suporte a consultas paginadas usando `setFirstResult` e `setMaxResults`, além de métodos de contagem total (`count`).
2.  **LazyGoalDataModel**: Criamos uma classe especializada para orquestrar o carregamento sob demanda conforme o usuário navega nas páginas da tabela.
3.  **Habilitação na UI**: Configuramos o atributo `lazy="true"` no `<p:dataTable>`, permitindo que apenas os registros visíveis (ex: 10 por vez) sejam recuperados do banco.

## Consequências
- **Positivas**: Performance escalável e constante, independente do tamanho do banco de dados. Menor consumo de rede e memória do servidor.
- **Negativas**: Aumenta a complexidade do código no Bean e no Service, exigindo classes de modelo específicas para paginação.
