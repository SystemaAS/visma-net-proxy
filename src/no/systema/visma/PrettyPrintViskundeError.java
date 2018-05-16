package no.systema.visma;

import java.time.LocalDateTime;

/**
 * This class is placeholder of info about deviations from syncronization. To be
 * used with FlipTable
 * 
 * @author fredrikmoller
 *
 */
public class PrettyPrintViskundeError {

	private int kundnr;
	private LocalDateTime syncDate;
	private String errorText;

	protected PrettyPrintViskundeError() {
	}

	public PrettyPrintViskundeError(int kundnr,  LocalDateTime syncDate, String errorText) {
		this.kundnr = kundnr;
		this.syncDate = syncDate;
		this.errorText = errorText;

	}

	public int getKundnr() {
		return kundnr;
	}

	public void setKundnr(int kundnr) {
		this.kundnr = kundnr;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public LocalDateTime getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(LocalDateTime syncDate) {
		this.syncDate = syncDate;
	}

}
