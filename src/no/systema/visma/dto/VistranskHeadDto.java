package no.systema.visma.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * This is a placeholder for Invoice header.
 * 
 * @author fredrikmoller
 *
 */
@Data
public class VistranskHeadDto {

	private String firma;
	private int resnr;
	private int bilnr;
	private int bilaar;
	private int bilmnd;
	private int bildag;
	private int krdaar;
	private int krdmnd;
	private int krddag;
	private int ffdaar;
	private int ffdmnd;
	private int ffddag;
	private String betbet;
	private int peraar;
	private int pernr;
	private String valkox;
	private BigDecimal valku1 = new BigDecimal(0);	
	private String fakkre;
	private String path;
	private List<VistranskLineDto> lines;
	
}
