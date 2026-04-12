# ADR 0004: Reestruturação de Pacotes e Arquitetura em Camadas

## Status
Aceito

## Data
2026-04-12

## Contexto
O código-fonte estava anteriormente organizado de forma plana ou seguindo um MVC genérico, o que misturava lógica de persistência com regras de negócio e componentes web.

## Decisão
Restruturamos o backend seguindo princípios de **Separação de Preocupações (SoC)** e camadas lógicas:
1.  `br.com.ingenium.domain.model`: Contém as entidades de banco de dados e tipos básicos, livre de lógica de aplicação.
2.  `br.com.ingenium.application.service`: Contém a lógica de negócio pura e os serviços que orquestram os casos de uso (Cadastro, Exclusão, etc).
3.  `br.com.ingenium.infrastructure.persistence`: Implementa os Repositórios (DAO moderno) usando JPA, isolando a tecnologia de banco de dados.
4.  `br.com.ingenium.web`: Camada de interface, contendo Beans CDI que gerenciam o estado da tela e a integração com o PrimeFaces.

## Consequências
- **Positivas**: Facilidade de manutenção, maior testabilidade (especialmente na camada de serviço) e clareza sobre onde cada parte da lógica deve residir.
- **Negativas**: Aumento na quantidade de classes/pacotes devido à decomposição.
