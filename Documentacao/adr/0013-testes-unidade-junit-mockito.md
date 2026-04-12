# ADR 0013: Implementação de Testes de Unidade (JUnit 5 + Mockito)

## Status
Aceito

## Data
2026-04-12

## Contexto
O projeto carecia de uma suíte de testes automatizados, o que tornava qualquer refatoração arriscada e dependente de testes manuais exaustivos em todas as telas. Para garantir a qualidade e a manutenção da lógica de negócio a longo prazo, é essencial ter testes automatizados na camada de serviço.

## Decisão
Introduzimos a base para testes automatizados:
1.  **Stack Técnica**: Adotamos o **JUnit 5 (Jupiter)** como framework de execução e o **Mockito** para isolamento de dependências (mocks).
2.  **Estrutura**: Criamos a pasta `src/test/java` seguindo o padrão Maven.
3.  **Cobertura Inicial**: Implementamos testes para a classe `UserService`, validando regras críticas como criptografia de senha no registro, unicidade de e-mail e validações de tamanho de campo.

## Consequências
- **Positivas**: Maior confiança no código, documentação viva das regras de negócio através dos testes e detecção precoce de bugs.
- **Negativas**: Leve aumento no tempo de build (segundos) e esforço adicional de desenvolvimento para manter os testes atualizados.
