# ADR 0003: Migração para Jakarta EE 10 e WildFly 38

## Status
Aceito

## Data
2026-04-12

## Contexto
O projeto original utilizava tecnologias legadas (Tomcat 8, Java 8 e MySQL 5). Essa stack dificultava a adoção de recursos modernos de produtividade, segurança e performance, além de dificultar o suporte a containers modernos.

## Decisão
Decidimos modernizar a stack tecnológica para:
1.  **Jakarta EE 10**: Migração do namespace `javax.*` para `jakarta.*`, utilizando as versões mais recentes das especificações (Faces 4, CDI 4, JPA 3.1).
2.  **Java 17**: Utilização de uma versão LTS moderna do Java com suporte a Records e melhorias no runtime.
3.  **WildFly 38.0.0.Final**: Servidor de aplicação robusto e totalmente compatível com o perfil Jakarta EE 10 Web Profile.

## Consequências
- **Positivas**: Acesso a APIs modernas, melhor suporte a injeção de dependência (CDI), maior segurança e compatibilidade com ecossistemas de nuvem.
- **Negativas**: Quebra de compatibilidade com servidores de aplicação antigos (como Tomcat 8/9).
