package no.systema.visma.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * This is a placeholder for invoice lines.
 * 
 * @author fredrikmoller
 *
 */
@Data
public class VistranskLineDto {

	private BigDecimal nbelpo = new BigDecimal(0);
	private String momsk;
	private int kontov;
	private int ksted;
	private int kbarer;	
	private String biltxt;	
	private int posnr;
	
}
