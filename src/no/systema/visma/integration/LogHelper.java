package no.systema.visma.integration;

import java.util.Objects;

/**
 * Helper for misc logging issues.
 * 
 * For convenience.
 * 
 * @author fredrikmoller
 *
 */
public class LogHelper {

	/**
	 * Standardized logformat for VISKUNDE.
	 * 
	 * @param kundnr
	 * @return String 
	 */
	public static String logPrefixCustomer(Object kundnr) {
		StringBuilder sb = new StringBuilder();
		sb.append("::KUNDNR:").append(kundnr);
		
		return sb.toString();
		
	}
	
	/**
	 * Standardized logformat for VISxyz.
	 * 
	 * @param xyznr
	 * @return String 
	 */
	public static String logPrefixSubaccount(Object xyznr) {
		StringBuilder sb = new StringBuilder();
		sb.append("::xyz:").append(xyznr);
		
		return sb.toString();
		
	}	
	
	
	
	/**
	 * Standardized logformat for VISTRANSK.
	 * 
	 * @param recnr
	 * @param bilnr
	 * @return String 
	 */
	public static String logPrefixCustomerInvoice(Object recnr, Object bilnr) {
		StringBuilder sb = new StringBuilder();
		sb.append("::RECNR:").append(recnr).append("::BILNR::").append(bilnr);
		
		return sb.toString();
		
	}	
	
	/**
	 * Trim text to max 800 char
	 * 
	 * @param text
	 * @return text =< 800
	 */
	public static String trimToError(String text) {
		Objects.requireNonNull(text, "text can not be null");
		if (text.length() < 800) {
			return text;
		}
		int beginIndex = text.length() - 799;  //syerro is set to 800

		return text.substring(beginIndex);		
		
	}
	
}
