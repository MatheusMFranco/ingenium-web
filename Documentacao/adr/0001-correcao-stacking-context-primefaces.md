# ADR 0001: Resolução de Conflito de Empilhamento (Stacking Context) no PrimeFaces

## Status
Aceito

## Data
2026-04-12

## Contexto
Na tela de cadastro de objetivos, os campos `p:selectOneMenu` (Categoria e Tipo de Duração) estavam sendo sobrepostos visualmente pelos campos que vinham logo abaixo no layout (Grid CSS), como os campos de data e o textarea. Isso impedia o usuário de visualizar ou selecionar as primeiras opções da lista suspensa.

Tentativas iniciais de resolver apenas com `z-index` na classe do painel falharam porque o Grid CSS criava contextos de empilhamento isolados, onde a ordem no DOM (quem vem depois fica por cima) prevalecia sobre o `z-index`.

## Decisão
Decidimos aplicar duas mudanças coordenadas:
1. Adicionar o atributo `appendTo="@(body)"` em todos os componentes `<p:selectOneMenu>` da página. Isso remove o painel da lista da hierarquia do formulário e o injeta diretamente na raiz do documento HTML.
2. Definir um `z-index` global extremamente alto (`100001 !important`) para a classe `.ui-selectonemenu-panel` no arquivo `app.css`.

## Consequências
- **Positivas**: Os dropdowns agora abrem por cima de qualquer outro elemento da interface, independentemente da complexidade do layout ou da ordem no DOM.
- **Negativas**: O uso de `appendTo="@(body)"` pode exigir atenção extra caso o formulário dependa de herança de estilos CSS específicos do contêiner pai para o painel flutuante.
