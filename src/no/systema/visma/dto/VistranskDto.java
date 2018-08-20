package no.systema.visma.dto;

import lombok.Data;
import no.systema.jservices.common.dao.VistranskDao;

/**
 * This dto is a  stripped version of {@linkplain VistranskDao} for view in GUI.
 * 
 * @author fredrikmoller
 *
 */
@Data
public class VistranskDto {

	private String aktkod;
	private String firma;
	private int resnr; //kundnr
	private int bilnr; //faktura
	private int posnr;
	private String betbet;
	private int konto;
	private int ksted;
	private int kbarer;
	private String biltxt;
	private String fakkre;
	private int syncda;
	private String syerro;
	
}
