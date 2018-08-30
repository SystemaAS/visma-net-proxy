package no.systema.visma.dto;

import lombok.Data;
import no.systema.jservices.common.dao.VistranshDao;

/**
 * This dto is a  stripped version of {@linkplain VistranshDao} for view in GUI.
 * 
 * @author fredrikmoller
 *
 */
@Data
public class VistranshDto {

	private String aktkod;
	private String firma;
	private int resnr; //levnr
	private int bilnr; //faktura
	private int posnr;
	private String betbet;
	private int kontov;
	private int ksted;
	private int kbarer;
	private String biltxt;
	private String fakkre;
	private String valkox;
	private int syncda;
	private String syerro;
	
}
