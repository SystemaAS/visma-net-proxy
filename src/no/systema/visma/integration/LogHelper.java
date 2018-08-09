package no.systema.visma.integration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		sb.append("::RESNR:").append(recnr).append("::BILNR::").append(bilnr);
		
		return sb.toString();
		
	}	

	/**
	 * Standardized logformat for VISTRANSL.
	 * 
	 * @param resnr
	 * @param bilnr
	 * @return String 
	 */
	public static String logPrefixSupplierInvoice(Object resnr, Object bilnr) {
		StringBuilder sb = new StringBuilder();
		sb.append("::RESNR:").append(resnr).append("::BILNR::").append(bilnr);
		
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

	/**
	 * Standardized logformat for SUPPLIER.
	 * 
	 * @param levnr
	 * @return String 
	 */
	public static String logPrefixSupplier(Object levnr) {
		StringBuilder sb = new StringBuilder();
		sb.append("::LEVNR:").append(levnr);
		
		return sb.toString();
	}

	/**
	 * Creates date bases on {@linkplain LocalDateTime}.now() <br>
	 * and puts date in int[0] and time in int[1] <br><br>
	 * 
	 * Date Pattern: DateTimeFormatter.ofPattern("yyyyMMdd") <br>
	 * Time Pattern: DateTimeFormatter.ofPattern("HHmmss")
	 * 
	 * @return int array with date in first position and time in second position.
	 */
	public static int[] getNowDato() {
		int[] dato = new int[2];
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd"); 		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");		
		LocalDateTime now = LocalDateTime.now();

		String nowDate = now.format(dateFormatter);
		String nowTime = now.format(timeFormatter);

		int syncDa = Integer.valueOf(nowDate);
		int synctm = Integer.valueOf(nowTime);	
		
		dato[0] = syncDa;
		dato[1] = synctm;
		
		return dato;
		
		
	}
	
}
