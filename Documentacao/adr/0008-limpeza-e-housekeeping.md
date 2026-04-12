# ADR 0008: Limpeza de Artefatos de Legado e IDE

## Status
Aceito

## Data
2026-04-12

## Contexto
O repositório continha arquivos de configuração de IDE (Eclipse), pastas vazias e arquivos CSS antigos que não eram mais utilizados após a modernização visual, o que tornava a estrutura do projeto confusa e pouco profissional.

## Decisão
Realizamos um processo de "Housekeeping":
1.  **Remoção de .jsfdia**: Exclusão do arquivo visual do Eclipse JSF editor.
2.  **Limpeza de CSS**: Exclusão de `cores.css`, `estilo.css`, `impressao.css` e `reset.css`, uma vez que todas as suas regras úteis foram migradas ou substituídas pelo `app.css`.
3.  **Remoção de Pastas Vazias**: Exclusão da pasta `src/main/webapp/META-INF` que estava sem conteúdo.
4.  **Atualização de .gitignore**: Adição de padrões para evitar que arquivos como `.jsfdia` voltem ao repositório.

## Consequências
- **Positivas**: Estrutura de arquivos limpa, profissional e fácil de navegar.
- **Negativas**: Nenhuma.
