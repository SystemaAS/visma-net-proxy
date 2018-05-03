package no.systema.visma;

import java.time.LocalDate;

/**
 * This class is placeholder of info about deviations from syncronization. To be
 * used with FlipTable
 * 
 * @author fredrikmoller
 *
 */
public class PrettyPrintViskundeError {

	private int kundnr;
	private LocalDate syncDate;
	private String errorText;

	protected PrettyPrintViskundeError() {
	}

	public PrettyPrintViskundeError(int kundnr,  LocalDate syncDate, String errorText) {
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

	public LocalDate getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(LocalDate syncDate) {
		this.syncDate = syncDate;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

}
