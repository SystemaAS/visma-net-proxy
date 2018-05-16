package no.systema.visma;

import java.time.LocalDate;

import lombok.Data;

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
	private LocalDate syncda;
	private String syerro;

}
