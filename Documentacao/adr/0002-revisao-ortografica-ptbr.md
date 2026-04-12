# ADR 0002: Padronização Ortográfica e de Acentuação (PT-BR)

## Status
Aceito

## Data
2026-04-11

## Contexto
O projeto apresentava diversas inconsistências ortográficas nos textos visíveis ao usuário final (labels, placeholders, mensagens de erro). Muitas palavras estavam sem acentuação correta (ex: "Usuario", "Gestao", "Descricao") ou sem cedilha ("Navegacao"). Isso prejudicava a experiência do usuário e a percepção de qualidade do software.

## Decisão
Realizamos uma revisão sistemática em todos os arquivos de interface (`.xhtml`) e arquivos de mensagens (`.properties`). Aplicamos a gramática correta da língua portuguesa (Brasil), garantindo o uso de acentos agudos, circunflexos, tils e cedilhas.

## Consequências
- **Positivas**: Interface mais profissional e mensagens de erro gramaticalmente corretas.
- **Negativas**: Nenhuma. O impacto foi estritamente textual e visual, sem afetar a lógica de negócio ou a integridade dos dados.
