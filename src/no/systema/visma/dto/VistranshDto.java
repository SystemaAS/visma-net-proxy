package no.systema.visma.dto;

import java.math.BigDecimal;

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

	private String firma;
	private int bilnr; //faktura
	private int posnr;
	private BigDecimal nbelpo;
	private int kontov;
	private int ksted;
	private String biltxt;
	private String fakkre;
	private String valkox;
	private int syncda;
	private String syerro;
	
}
