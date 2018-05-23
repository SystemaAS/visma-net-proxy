package no.systema.visma.integration;

import java.math.BigDecimal;

import no.systema.visma.v1client.model.DtoValueDecimal;
import no.systema.visma.v1client.model.DtoValueString;

public class Helper {

	/**
	 * Standardized logformat for Kundnr for convenience.
	 * 
	 * @param kundnr
	 * @return String logformat on Kundnr
	 */
	public static String logPrefix(Object kundnr) {
		StringBuilder sb = new StringBuilder();
		sb.append("::KUNDNR:").append(kundnr);
		
		return sb.toString();
		
	}
	
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
	
	
}
