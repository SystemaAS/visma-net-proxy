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
	private int kontov;
	private int ksted;
	private String momsk;
	private String biltxt;
	private String fakkre;
	private String valkox;
	private int syncda;
	private String syerro;
	
}
