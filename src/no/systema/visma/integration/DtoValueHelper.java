package no.systema.visma.integration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import no.systema.visma.v1client.model.DtoValueDateTime;
import no.systema.visma.v1client.model.DtoValueDecimal;
import no.systema.visma.v1client.model.DtoValueInt32;
import no.systema.visma.v1client.model.DtoValueString;

/**
 * This helper is transforming proprietary SYSPED types into Visma.net types.
 * 
 * @author fredrikmoller
 *
 */
public class DtoValueHelper {

	/**
	 * Converts Object into DtoValueString.
	 * 
	 * @param Object o
	 * @return DtoValueString
	 */
	public static DtoValueString toDtoString(java.lang.Object o) {
		if (o == null) {
			return null;
		}

		return new DtoValueString().value(o.toString());

	}	
	
	/**
	 * Convert Object into DtoValueDecimal.<br>
	 * 
	 * @param o supports Double and BigDecimal
	 * @return DtoValueDecimal
	 */
	public static DtoValueDecimal toDtoDecimal(java.lang.Object o) {
		DtoValueDecimal dto;
		if (o == null) {
			return null;
		}
		if (o instanceof Double) {
			Double oo = ((Double) o).doubleValue();
			dto = new DtoValueDecimal().value(oo);
		} else if (o instanceof BigDecimal) {
			Double oo = ((BigDecimal) o).doubleValue();
			dto = new DtoValueDecimal().value(oo);
		} else {
			throw new IllegalArgumentException(o.getClass()+" Not supported");
		}

		return dto;

	}

	/**
	 * Convert Object into DtoValueInt32.<br>
	 * 
	 * @param o supports Integer
	 * @return DtoValueInt32
	 */
	public static DtoValueInt32 toDtoValueInt32(java.lang.Object o) {
		DtoValueInt32 dto;
		if (o == null) {
			return null;
		}
		if (o instanceof Integer) {
			Integer oo = (Integer) o;
			dto = new DtoValueInt32().value(oo);
		}  else {
			throw new IllegalArgumentException(o.getClass()+" Not supported");
		}
		
		return dto;
	}		

	/**
	 * Convert SYSPED date into DtoValueDateTime.<br>
	 * 
	 * @param aar
	 * @param mnd
	 * @param dag
	 * @return DtoValueDateTime using LocalDateTime
	 */
	public static DtoValueDateTime toDtoValueDateTime(int aar, int mnd, int dag) {
		DtoValueDateTime dto = new DtoValueDateTime();
		Objects.requireNonNull(aar, "aar is mandatory");
		Objects.requireNonNull(mnd, "mnd is mandatory");
		Objects.requireNonNull(aar, "dag is mandatory");

		LocalDateTime value = LocalDateTime.of(aar, mnd, dag, 0, 0, 0);  //TODO verify

		dto.setValue(value);

		
		return dto;
	}			
	
	
	
	
}
