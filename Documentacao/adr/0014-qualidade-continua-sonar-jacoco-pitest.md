# ADR 0014: Integração Contínua com Jacoco, Pitest e SonarQube (Quality Gate Agressivo)

## Status
Aceito

## Data
2026-04-12

## Contexto
Para garantir que o sistema crítico mantenha um alto padrão de confiabilidade e manutenibilidade, era necessário automatizar a medição de métricas de qualidade e cobertura de testes. Apenas "ter testes" não é suficiente; é preciso garantir que eles sejam eficazes e cubram a maior parte da lógica de negócio.

## Decisão
Implementamos um pipeline de qualidade robusto:
1.  **Jacoco**: Integrado ao build Maven para medir a cobertura de linhas e branches. Definimos um limite mínimo de **90% de cobertura** para o build ser considerado bem-sucedido.
2.  **Pitest**: Introduzimos testes de mutação para validar a força da suíte de testes. O Pitest altera o código-fonte (mutações) e verifica se os testes falham; se o teste passar mesmo com o código alterado, a mutação "sobrevive", indicando um teste fraco.
3.  **SonarQube**: Dockerizado para análise estática contínua.
4.  **Quality Gate Agressivo**: Configuração de um portão de qualidade que bloqueia o projeto caso:
    - Cobertura seja inferior a 90%.
    - Existam bugs ou vulnerabilidades críticas.
    - O índice de duplicação de código seja elevado.

## Consequências
- **Positivas**: Redução drástica de débitos técnicos, garantia de que novas funcionalidades venham acompanhadas de testes rigorosos e maior segurança contra regressões.
- **Negativas**: O tempo de build aumenta significativamente devido aos testes de mutação. Desenvolvedores podem enfrentar frustração inicial ao terem builds rejeitados por métricas de cobertura.
