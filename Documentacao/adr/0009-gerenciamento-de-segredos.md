# ADR 0009: Gerenciamento de Segredos e Configurações via Variáveis de Ambiente

## Status
Aceito

## Data
2026-04-12

## Contexto
Anteriormente, credenciais sensíveis (como a senha do root do MySQL) estavam codificadas diretamente nos arquivos `docker-compose.yml` e nos scripts de configuração do WildFly (`configure-datasource.cli`). Isso é uma má prática de segurança que expõe segredos no repositório de código e dificulta a portabilidade entre ambientes (desenvolvimento, homologação, produção).

## Decisão
Adotamos o padrão **12-Factor App** para configurações:
1.  **Arquivo .env**: Criamos um arquivo `.env` local para armazenar as credenciais padrão, o qual foi adicionado ao `.gitignore`.
2.  **Docker Compose**: Atualizamos o `docker-compose.yml` para ler as variáveis `${MYSQL_ROOT_PASSWORD}` e `${MYSQL_DATABASE}`.
3.  **WildFly JBoss-CLI**: O script de criação do Datasource agora utiliza a sintaxe `${env.DB_URL}`, `${env.DB_USER}` e `${env.DB_PASSWORD}`, permitindo que o WildFly injete os valores a partir do ambiente do container.

## Consequências
- **Positivas**: Maior segurança (segredos não são commitados), facilidade de integração com CI/CD e flexibilidade para trocar de banco de dados sem alterar o código.
- **Negativas**: Exige a criação manual do arquivo `.env` ou a definição de variáveis no host para o primeiro build.
