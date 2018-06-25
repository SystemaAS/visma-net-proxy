package no.systema.visma.dto;

import lombok.Data;
import no.systema.jservices.common.dao.VisleveDao;

/**
 * This dto is a  stripped version of {@linkplain VisleveDao} for view in GUI.
 * 
 * @author fredrikmoller
 *
 */
@Data
public class VisleveDto {

	private String aktkod;
	private String firma;
	private int levnr; // key
	private String lnavn;
	private int postnr;
	private String valkod;
	private String spraak;
	private String betbet;
	private String land;
	private int syncda;
	private String syerro;

}
