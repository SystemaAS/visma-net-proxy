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
public class PrettyPrintVisleveError {

	private int levnr;
	private LocalDateTime syncDate;
	private String errorText;

	protected PrettyPrintVisleveError() {
	}

	public PrettyPrintVisleveError(int levnr,  LocalDateTime syncDate, String errorText) {
		this.levnr = levnr;
		this.syncDate = syncDate;
		this.errorText = errorText;

	}

}
