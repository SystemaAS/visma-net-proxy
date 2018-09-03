package no.systema.visma.integration;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import no.systema.jservices.common.dao.VistranskDao;
import no.systema.visma.v1client.RFC3339DateFormat;
import no.systema.visma.v1client.model.DtoValueDecimal;

public class TestJHelper {

	private static Logger logger = Logger.getLogger(TestJHelper.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToDtoDecimalBigDecimal() {
		BigDecimal o = new BigDecimal(12.66).setScale(3, BigDecimal.ROUND_UP);
		DtoValueDecimal result = DtoValueHelper.toDtoValueDecimal(o);
		assertEquals(o.doubleValue(), result.getValue().doubleValue(), 0);

	}

	@Test
	public void testToDtoDecimalDouble() {

		Double o = new Double(12.99);
		DtoValueDecimal result = DtoValueHelper.toDtoValueDecimal(o);
		assertEquals(o.doubleValue(), result.getValue().doubleValue(), 0);

	}

	@Test
	public void testDate() {
		// https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		logger.info("now, NO timezone" + LocalDateTime.now());

		LocalDateTime date = LocalDateTime.now();
		String text = date.format(dateTimeFormatter);
		LocalDateTime parsedDate = LocalDateTime.parse(text, dateTimeFormatter);

		logger.info("now, timezone" + LocalDateTime.now(ZoneId.systemDefault()));

		logger.info("parsedDate " + parsedDate);

		VistranskDao dao = getVistranskDao(0, 0, 0, null);
		LocalDateTime value = LocalDateTime.of(dao.getKrdaar(), dao.getKrdmnd(), dao.getKrddag(), 0, 0, 0, 0);

		logger.info("value=" + value);

		logger.info("value, format=" + value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		RFC3339DateFormat format = new RFC3339DateFormat();

		StringBuffer sb = new StringBuffer();
		logger.info("format=" + format.format(value, sb, null));

	}
	
	@Test(expected=DateTimeException.class)  //For Oscars Date testing
	public final void testIllegalDate() {
		String dateString = "20180231";
		String dateFormat = "uuuuMMdd";
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter
				.ofPattern(dateFormat)
				.withResolverStyle(ResolverStyle.STRICT);
		
		LocalDateTime dateTime = LocalDateTime.parse(dateString, dateTimeFormatter);	
		
	}
	
	private VistranskDao getVistranskDao(int resnr, int bilnr, int posnr, String biltxt) {
		VistranskDao dao = new VistranskDao();
		dao.setResnr(resnr);
		dao.setBilnr(bilnr);
		dao.setPosnr(posnr);
		dao.setBiltxt(biltxt);
		dao.setAktkod("A");
		dao.setKrdaar(2018);
		dao.setKrdmnd(5);
		dao.setKrddag(25);
		dao.setFfdaar(2018);
		dao.setFfdmnd(5);
		dao.setFfddag(25);
		dao.setMomsk("32");
		dao.setKontov(3000);
		dao.setKbarer(1000);
		dao.setKsted(4000);
		dao.setBetbet("14");
		dao.setPeraar(2018);
		dao.setPernr(5);

		return dao;
	}

}
