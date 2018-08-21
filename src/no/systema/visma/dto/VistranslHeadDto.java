package no.systema.visma.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * This is a placeholder for Supplier Invoice header.
 * 
 * @author fredrikmoller
 *
 */
@Data
public class VistranslHeadDto {

	private String firma;
	private int resnr;
	private String krnr;
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
	private String lkid;
	private String valkox;
	private String path;
	private String fakkre;
	private BigDecimal valku1 = new BigDecimal(0);	
	private List<VistranslLineDto> lines;
	
}
