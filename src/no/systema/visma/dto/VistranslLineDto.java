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

	private BigDecimal nbelpo = new BigDecimal(0);
	private String momsk;
	private int kontov;
	private int ksted;
	private String biltxt;	
	private int posnr;
	
}
