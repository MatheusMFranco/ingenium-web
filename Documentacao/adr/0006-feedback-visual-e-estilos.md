# ADR 0006: Sistema de Feedback Centralizado e Identidade Visual

## Status
Aceito

## Data
2026-04-12

## Contexto
A aplicação precisava de uma forma padronizada de exibir mensagens para o usuário (sucesso, erro, avisos) e de uma identidade visual moderna que substituísse o visual "puro" do PrimeFaces.

## Decisão
Implementamos:
1.  **FacesMessages.java**: Uma classe utilitária para encapsular a criação de `FacesMessage`, garantindo que todas as mensagens sigam o mesmo padrão de severidade e detalhamento.
2.  **app.css**: Um novo arquivo de estilos globais que sobrescreve o tema Saga do PrimeFaces, aplicando uma paleta de cores moderna (ink-900, accent-500) e componentes com bordas arredondadas e sombras suaves.

## Consequências
- **Positivas**: Experiência de usuário (UX) consistente e interface visualmente atraente.
- **Negativas**: Aumenta a dependência de arquivos CSS customizados que precisam ser mantidos junto com as atualizações do PrimeFaces.
