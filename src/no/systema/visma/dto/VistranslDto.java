package no.systema.visma.dto;

import lombok.Data;
import no.systema.jservices.common.dao.VistranslDao;

/**
 * This dto is a  stripped version of {@linkplain VistranslDao} for view in GUI.
 * 
 * @author fredrikmoller
 *
 */
@Data
public class VistranslDto {

	private String aktkod;
	private String firma;
	private int resnr; //levnr
	private int bilnr; //faktura
	private int posnr;
	private String betbet;
	private int konto;
	private int ksted;
	private int kbarer;
	private String biltxt;
	private int syncda;
	private String syerro;
	
}
