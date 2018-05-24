package no.systema.visma;

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

	private int recnr;
	private int bilnr;
	private int posnr;
	private int krdaar;
	private int krdmnd;
	private int krddag;
	private int ffdaar;
	private int ffdmnd;
	private int ffddag;
	private String betbet;
	private List<VistranskLineDto> lines;
	
}
