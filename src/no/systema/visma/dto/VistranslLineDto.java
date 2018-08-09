package no.systema.visma.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * This is a placeholder for supplier invoice lines.
 * 
 * @author fredrikmoller
 *
 */
@Data
public class VistranslLineDto {

//	private BigDecimal bbelop = new BigDecimal(0);
	private BigDecimal nbelpo = new BigDecimal(0);
	private String momsk;
	private int konto;
	private int ksted;
//	private int kbarer;	
	private String biltxt;	
	private int posnr;
//	private int prosnr;
	
}
