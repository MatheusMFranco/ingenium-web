<p align="center">
  <img src="INGENIUM/src/main/webapp/resources/img/logo.png" alt="INGENIUM Logo" width="200">
</p>

# 🎯 INGENIUM Web

Aplicação web para gestão de usuários, categorias e objetivos pessoais, modernizada para **Jakarta EE 10** rodando em **WildFly 38**.

## 🚀 Stack Tecnológica

- **Java 17**
- **Jakarta EE 10** (Faces 4, CDI 4, JPA 3.1, Servlet 6)
- **PrimeFaces 14** (Versão Jakarta)
- **WildFly 38.0.0.Final**
- **MySQL 8**
- **Maven 3.9+**
- **Docker Compose v2**

## 🏗️ Arquitetura de Código

O backend está organizado seguindo padrões de camadas para melhor manutenibilidade:

- `br.com.ingenium.domain.model`: Entidades e Enums de domínio.
- `br.com.ingenium.application.service`: Camada de serviços e regras de negócio.
- `br.com.ingenium.infrastructure.persistence`: Repositórios JPA e conversores de dados.
- `br.com.ingenium.web`: Beans (Backing Beans), Filtros e Sessão da camada JSF/CDI.

## 💾 Persistência de Dados

- Configuração via `persistence.xml` em modo **JTA** integrado ao datasource do WildFly.
- JNDI: `java:/jdbc/IngeniumDS`
- Evolução de Schema: Gerenciada automaticamente pelo Hibernate (`update`).
- **Tabelas Principais**: `usuario`, `categoria`, `objetivo`.

## 🐳 Subindo o Ambiente com Docker

Certifique-se de estar na raiz do projeto e execute:

```powershell
docker compose down -v
docker compose up -d --build
docker compose ps
```

### 🔗 URLs Úteis
- **Aplicação**: [http://localhost:8080/INGENIUM/login.xhtml](http://localhost:8080/INGENIUM/login.xhtml)
- **WildFly Admin**: [http://localhost:9990](http://localhost:9990)
- **SonarQube**: [http://localhost:9000](http://localhost:9000) (admin / 123456)

## 🛡️ Qualidade e Testes

O projeto utiliza um pipeline rigoroso de qualidade:

- **Análise Estática**: SonarQube com **Quality Gate Agressivo** (meta de 90% de cobertura).
- **Cobertura de Código**: Jacoco integrado ao Maven.
- **Testes de Mutação**: Pitest para validar a eficácia da suíte de testes.
- **Provisionamento Automático**: O SonarQube é configurado automaticamente no startup via serviço `sonar-provisioner`.

### Comandos de Verificação
```powershell
cd INGENIUM
# Rodar testes unitários e cobertura local
mvn clean verify

# Rodar análise completa e enviar ao Sonar
mvn sonar:sonar "-Dsonar.host.url=http://localhost:9000" "-Dsonar.login=admin" "-Dsonar.password=123456"

# Rodar testes de mutação
mvn pitest:mutationCoverage
```

## 🛠️ Build Local (Maven)

Caso queira gerar o pacote fora do Docker:

```powershell
cd INGENIUM
mvn -U clean verify
```

## 📐 Estrutura de Infraestrutura

- **Dockerfile**: Build multi-stage (Maven -> WildFly) com provisionamento automático via `jboss-cli`.
- **docker-compose.yml**: Orquestração completa (WildFly, MySQL, SonarQube, Postgres, Provisioner).
- **docker/wildfly/**: Scripts de configuração do Datasource e definição de módulos.

## 👥 Colaboradores Originalmente Identificados

O produto INGENIUM é fruto de um trabalho acadêmico dedicado a proporcionar melhor controle e organização de metas pessoais.

- **HIAGO PRATES**
- **JEAN CARVALHO**
- **MATHEUS FRANCO**
- **WESLEY OLIVEIRA**

---

## 📄 Decisões Arquiteturais (ADRs)

Para entender as mudanças recentes e escolhas técnicas, consulte a pasta `Documentacao/adr/`.

- [📘 ADR 0001 - Correção de Stacking Context (UI)](Documentacao/adr/0001-correcao-stacking-context-primefaces.md)
- [📘 ADR 0002 - Padronização Ortográfica e de Acentuação](Documentacao/adr/0002-revisao-ortografica-ptbr.md)
- [📘 ADR 0003 - Migração Jakarta EE 10 e WildFly 38](Documentacao/adr/0003-migracao-jakarta-ee-wildfly.md)
- [📘 ADR 0004 - Reestruturação de Pacotes e Camadas](Documentacao/adr/0004-reestruturacao-pacotes-camadas.md)
- [📘 ADR 0005 - Dockerização do Ambiente Local](Documentacao/adr/0005-dockerizacao-ambiente.md)
- [📘 ADR 0006 - Feedback Visual e Estilos Modernos](Documentacao/adr/0006-feedback-visual-e-estilos.md)
- [📘 ADR 0007 - Segurança e Sessão com CDI](Documentacao/adr/0007-seguranca-e-sessao.md)
- [📘 ADR 0008 - Limpeza de Artefatos Legados e IDE](Documentacao/adr/0008-limpeza-e-housekeeping.md)
- [📘 ADR 0009 - Gerenciamento de Segredos (Env Vars)](Documentacao/adr/0009-gerenciamento-de-segredos.md)
- [📘 ADR 0010 - Criptografia de Senhas (Elytron)](Documentacao/adr/0010-criptografia-senhas-elytron.md)
- [📘 ADR 0011 - Observabilidade e Logging (JUL)](Documentacao/adr/0011-observabilidade-e-logging.md)
- [📘 ADR 0012 - Paginação Eficiente (Lazy Loading)](Documentacao/adr/0012-paginacao-lazy-loading.md)
- [📘 ADR 0013 - Testes de Unidade (JUnit 5)](Documentacao/adr/0013-testes-unidade-junit-mockito.md)
- [📘 ADR 0014 - Qualidade Contínua (Sonar, Jacoco, Pitest)](Documentacao/adr/0014-qualidade-continua-sonar-jacoco-pitest.md)
