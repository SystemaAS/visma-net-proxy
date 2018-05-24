package no.systema.visma.integration;

/**
 * This helper is making prefixed logpost standardized.
 * 
 * For easier tracability.
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
	 * Standardized logformat for VISTRANSK.
	 * 
	 * @param recnr
	 * @param bilnr
	 * @param posnr
	 * @return String 
	 */
	public static String logPrefixCustomerInvoice(Object recnr, Object bilnr, Object posnr) {
		StringBuilder sb = new StringBuilder();
		sb.append("::RECNR:").append(recnr).append("::BILNR::").append(bilnr).append("::POSNR::").append(posnr);
		
		return sb.toString();
		
	}	
	
	
	
	
}
