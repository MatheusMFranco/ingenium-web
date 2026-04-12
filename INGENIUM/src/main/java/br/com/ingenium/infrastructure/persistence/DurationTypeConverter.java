package br.com.ingenium.infrastructure.persistence;

import br.com.ingenium.domain.model.DurationType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DurationTypeConverter implements AttributeConverter<DurationType, String> {

    @Override
    public String convertToDatabaseColumn(DurationType attribute) {
        return attribute == null ? null : attribute.getPersistenceValue();
    }

    @Override
    public DurationType convertToEntityAttribute(String dbData) {
        return DurationType.fromPersistenceValue(dbData);
    }
}
