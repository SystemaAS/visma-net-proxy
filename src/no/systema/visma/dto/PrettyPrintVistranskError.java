package no.systema.visma.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * This class is placeholder of info about deviations from syncronization. To be
 * used with FlipTable
 * 
 * @author fredrikmoller
 *
 */
@Data
public class PrettyPrintVistranskError {

	private final int recnr;
	private final int bilnr;
	private final int posnr;
	private final LocalDateTime syncDate;
	private final String errorText;

}
