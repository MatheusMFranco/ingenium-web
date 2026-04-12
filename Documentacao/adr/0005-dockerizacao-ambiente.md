# ADR 0005: Dockerização do Ambiente de Desenvolvimento

## Status
Aceito

## Data
2026-04-12

## Contexto
Anteriormente, cada desenvolvedor precisava instalar manualmente o MySQL e o Tomcat localmente, o que gerava inconsistências de ambiente ("funciona na minha máquina").

## Decisão
Adotamos o Docker como padrão para o ciclo de desenvolvimento:
1.  **Multi-stage Build**: O `Dockerfile` realiza o build Maven e o deploy no WildFly em uma única imagem.
2.  **Docker Compose**: Gerencia a subida simultânea do WildFly e do MySQL 8, incluindo configurações automáticas de JNDI e Datasource.
3.  **Persistence Integrity**: Uso de volumes para garantir que os dados do banco não sejam perdidos ao reiniciar o ambiente.

## Consequências
- **Positivas**: Setup de projeto em 1 minuto, ambiente idêntico para todos os desenvolvedores e facilidade de deploy em produção via containers.
- **Negativas**: Exige que a máquina do desenvolvedor suporte Docker/WSL2.
