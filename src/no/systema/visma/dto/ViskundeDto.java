package no.systema.visma.dto;

import lombok.Data;
import no.systema.jservices.common.dao.ViskundeDao;

/**
 * This dto is a  stripped version of {@linkplain ViskundeDao} for view in GUI.
 * 
 * @author fredrikmoller
 *
 */
@Data
public class ViskundeDto {

	private String aktkod;
	private String firma;
	private int kundnr; // key
	private String knavn;
	private int postnr;
	private String valkod;
	private String spraak;
	private String betbet;
	private String syland;
	private int syncda;
	private String syerro;

}
