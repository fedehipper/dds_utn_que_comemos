package grupo4.dds.persistencia;

import grupo4.dds.misc.CoberturaIgnore;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
	
	@CoberturaIgnore
    @Override
    public Date convertToDatabaseColumn(LocalDate locDate) {
    	return (locDate == null ? null : Date.valueOf(locDate));
    }

	@CoberturaIgnore
    @Override
    public LocalDate convertToEntityAttribute(Date sqlDate) {
    	return (sqlDate == null ? null : sqlDate.toLocalDate());
    }
}
