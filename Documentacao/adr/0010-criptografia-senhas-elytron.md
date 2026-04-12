# ADR 0010: Criptografia de Senhas com WildFly Elytron (BCrypt)

## Status
Aceito

## Data
2026-04-12

## Contexto
Até então, as senhas dos usuários eram armazenadas em texto plano no banco de dados. Em caso de vazamento de dados, todas as credenciais estariam expostas, o que viola princípios básicos de segurança e regulamentações como a LGPD.

## Decisão
Implementamos a criptografia de senhas utilizando o algoritmo **BCrypt** através da API nativa do servidor de aplicação (**WildFly Elytron**):
1.  **Habilitação de Módulo**: Adicionamos o módulo `org.wildfly.security.elytron` ao `jboss-deployment-structure.xml`.
2.  **Serviço PasswordHasher**: Criamos um serviço CDI para encapsular a lógica de geração de hash e verificação, utilizando `PasswordFactory` e o formato `ModularCrypt` para persistência.
3.  **Refatoração de Autenticação**: O sistema agora busca o usuário apenas pelo login e realiza a verificação do hash em memória (camada de aplicação), impossibilitando a descoberta de senhas via queries SQL simples.

## Consequências
- **Positivas**: Proteção robusta contra ataques de dicionário e rainbow tables. As senhas reais nunca são salvas ou trafegadas após o hash inicial.
- **Negativas**: Usuários criados anteriormente (em texto plano) perderão o acesso e precisarão ter suas senhas resetadas/atualizadas para o novo formato de hash.
