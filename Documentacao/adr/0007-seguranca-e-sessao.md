# ADR 0007: Filtro de Autenticação e Gestão de Sessão com CDI

## Status
Aceito

## Data
2026-04-12

## Contexto
Era necessário proteger as páginas internas da aplicação contra acesso não autorizado e gerenciar os dados do usuário logado de forma segura e eficiente durante a sessão.

## Decisão
Implementamos:
1.  **AuthenticationFilter**: Um `HttpFilter` que intercepta requisições para páginas protegidas (como as pastas `/uc/`) e redireciona usuários não autenticados para a página de login.
2.  **UserSession**: Um Bean CDI com escopo `@SessionScoped` que armazena o estado do usuário (como nome e objeto User), facilitando o acesso a esses dados em qualquer parte do sistema sem depender de variáveis de sessão manuais.

## Consequências
- **Positivas**: Segurança robusta na camada web e facilidade de manipulação de dados de sessão via injeção de dependência.
- **Negativas**: Introduz uma pequena sobrecarga no processamento de cada requisição devido à filtragem.
