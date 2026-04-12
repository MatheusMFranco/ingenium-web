# ADR 0011: Observabilidade e Logging Estruturado (JUL)

## Status
Aceito

## Data
2026-04-12

## Contexto
O sistema não possuía rastreabilidade de eventos. Falhas de login, erros de negócio ou exceções críticas ocorriam sem deixar registros no servidor, o que impossibilitava o diagnóstico de problemas em produção por parte da equipe de SRE/Operações.

## Decisão
Implementamos um sistema de logging utilizando a API nativa do Java (**java.util.logging - JUL**), que é o padrão bem integrado ao WildFly:
1.  **Instrumentação de Beans**: Adicionamos Loggers nas classes `AuthenticationBean`, `GoalBean` e `CategoryBean`.
2.  **Níveis de Log**:
    - `INFO`: Registra sucessos em operações críticas (Login, Criação de Meta).
    - `WARNING`: Registra falhas de validação ou tentativas negadas.
    - `SEVERE`: Registra erros inesperados do sistema com o stack trace completo.
3.  **Auditoria**: Passamos a logar o ID do usuário em cada ação de mutação (Create/Update/Delete) para fins de auditoria básica.

## Consequências
- **Positivas**: Facilidade de depuração, monitoramento em tempo real via logs do container e histórico de uso do sistema.
- **Negativas**: Leve aumento no uso de I/O de disco para escrita de logs, o que deve ser gerenciado por políticas de rotação de log do WildFly.
