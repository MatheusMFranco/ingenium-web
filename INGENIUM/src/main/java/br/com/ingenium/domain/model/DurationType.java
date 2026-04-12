package br.com.ingenium.domain.model;

import java.util.Arrays;

public enum DurationType {

    WEEKLY("Semanal"),
    MONTHLY("Mensal"),
    YEARLY("Anual");

    private final String persistenceValue;

    DurationType(String persistenceValue) {
        this.persistenceValue = persistenceValue;
    }

    public String getLabel() {
        return persistenceValue;
    }

    public String getPersistenceValue() {
        return persistenceValue;
    }

    public static DurationType fromPersistenceValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String normalized = value.trim();
        return Arrays.stream(values())
            .filter(type -> type.persistenceValue.equalsIgnoreCase(normalized) || type.name().equalsIgnoreCase(normalized))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Tipo de duração inválido: " + value));
    }
}
