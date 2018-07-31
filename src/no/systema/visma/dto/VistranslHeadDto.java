package no.systema.visma.dto;

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
	private int recnr;
	private int refnr;
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
	private List<VistranslLineDto> lines;
	
}
