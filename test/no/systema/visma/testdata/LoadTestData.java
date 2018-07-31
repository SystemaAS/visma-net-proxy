package no.systema.visma.testdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.VisleveDao;
import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.VisleveDaoService;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.jservices.common.dao.services.VistranslDaoService;
import no.systema.visma.integration.Customer;
import no.systema.visma.integration.CustomerInvoice;
import no.systema.visma.integration.Supplier;
import no.systema.visma.integration.SupplierInvoice;

/**
 * This class is managing testdata from cvs-files for entities: <br>
 * <li>
 * {@link Customer}
 * {@link CustomerInvoice}
 * {@link Supplier}
 * {@link SupplierInvoice}
 * </li>
 *  
 * 
 * @author fredrikmoller
 *
 */

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class LoadTestData {
	
	private static Logger logger = Logger.getLogger(LoadTestData.class);	
	
	@Autowired
	ViskundeDaoService viskundeDaoService;
	
	@Autowired
	VistranskDaoService vistranskDaoService;
	
	
	@Autowired
	VisleveDaoService visleveDaoService;
	
	@Autowired
	VistranslDaoService vistranslDaoService;	
	
	@Test
	public void run() {
		//loadCustomers();
		//loadCustomerInvoices();
		loadSuppliers();
		
	}
	
	private void loadCustomers() {
		Resource viskundeFile = new ClassPathResource("viskunde.csv");
		Reader in = null;
		Iterable<CSVRecord> records = null;
		int count = 0;
		try {
			in = new BufferedReader(new InputStreamReader(viskundeFile.getInputStream()));
			records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			logger.error("Could not read file", e);
		}

		for (CSVRecord record : records) {

			String aktkod = record.get("aktkod");
			String firma = record.get("firma");
			String kundnr = record.get("kundnr");
			String knavn = record.get("knavn");
			String adr1 = record.get("adr1");
			String adr2 = record.get("adr2");
			String adr3 = record.get("adr3");
			String postnr = record.get("postnr");
			String valkod = record.get("valkod");
			String spraak = record.get("spraak");
			String betbet = record.get("betbet");
			String syland = record.get("syland");
			String kpers = record.get("kpers");
			String syepos = record.get("syepos");
			String tlf = record.get("tlf");

			ViskundeDao dao = new ViskundeDao();
			dao.setAktkod(aktkod);
			dao.setFirma(firma);
			dao.setKundnr(Integer.parseInt(kundnr));
			dao.setKnavn(knavn);
			dao.setAdr1(adr1);
			dao.setAdr2(adr2);
			dao.setAdr3(adr3);
			dao.setPostnr(Integer.parseInt(postnr));
			dao.setValkod(valkod);
			dao.setSpraak(spraak);
			dao.setBetbet(betbet);
			dao.setSyland(syland);
			dao.setKpers(kpers);
			dao.setSyepos(syepos);
			dao.setTlf(tlf);

			viskundeDaoService.create(dao);

			count++;

		}

		logger.debug(count + " VISKUNDE posts created or changed.");

	}
	
	private void loadCustomerInvoices() {
		Resource vistranskFile = new ClassPathResource("vistransk.csv");
		Reader in = null;
		Iterable<CSVRecord> records = null;
		int count = 0;
		try {
			in = new BufferedReader(new InputStreamReader(vistranskFile.getInputStream()));
			records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			logger.error("Could not read file", e);
		}

		for (CSVRecord record : records) {

			String aktkod = record.get("aktkod");
			String firma = record.get("firma");

			String recnr = record.get("recnr");
			String bilnr = record.get("bilnr");
			String posnr = record.get("posnr");
			String bilaar = record.get("bilaar");
			String bilmnd = record.get("bilmnd");
			String bildag = record.get("bildag");
			String peraar = record.get("peraar");
			String pernr = record.get("pernr");
			String krdaar = record.get("krdaar");
			String krdmnd = record.get("krdmnd");
			String krddag = record.get("krddag");
			String ffdaar = record.get("ffdaar");
			String ffdmnd = record.get("ffdmnd");
			String ffddag = record.get("ffddag");	
			String biltxt = record.get("biltxt");
			String betbet = record.get("betbet");
			String konto = record.get("konto");
			String ksted = record.get("ksted");
			String kbarer = record.get("kbarer");
			String momsk = record.get("momsk");  //TEGN 1			
			String bbelop = record.get("bbelop");
			

			VistranskDao dao = new VistranskDao();
			dao.setAktkod(aktkod);
			dao.setFirma(firma);
			dao.setRecnr(Integer.parseInt(recnr));
			dao.setBilnr(Integer.parseInt(bilnr));
			dao.setPosnr(Integer.parseInt(posnr));
			dao.setBilaar(Integer.parseInt(bilaar));
			dao.setBilmnd(Integer.parseInt(bilmnd));
			dao.setBildag(Integer.parseInt(bildag));
			dao.setPeraar(Integer.parseInt(peraar));
			dao.setPernr(Integer.parseInt(pernr));
			dao.setKrdaar(Integer.parseInt(krdaar));
			dao.setKrdmnd(Integer.parseInt(krdmnd));
			dao.setKrddag(Integer.parseInt(krddag));
			dao.setFfdaar(Integer.parseInt(ffdaar));
			dao.setFfdmnd(Integer.parseInt(ffdmnd));
			dao.setFfddag(Integer.parseInt(ffddag));	
			dao.setBiltxt(biltxt);
			dao.setBetbet(betbet);
			dao.setKonto(Integer.parseInt(konto));
			dao.setKsted(Integer.parseInt(ksted));
			dao.setKbarer(Integer.parseInt(kbarer));
//			dao.setProsnr(55);
			dao.setMomsk(momsk);  
			dao.setBbelop(new BigDecimal(bbelop));
			
			vistranskDaoService.create(dao);

			count++;

		}

		logger.debug(count + " VISTRANSK posts created or changed.");

	}	
	
	private void loadSuppliers() {
		Resource visleveFile = new ClassPathResource("visleve.csv");
		Reader in = null;
		Iterable<CSVRecord> records = null;
		int count = 0;
		try {
			in = new BufferedReader(new InputStreamReader(visleveFile.getInputStream()));
			records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			logger.error("Could not read file", e);
		}

		for (CSVRecord record : records) {

			String aktkod = record.get("aktkod");
			String firma = record.get("firma");
			String levnr = record.get("levnr");
			String rnrale = record.get("rnrale");
			String lnavn = record.get("lnavn");
			String adr1 = record.get("adr1");
			String adr2 = record.get("adr2");
			String adr3 = record.get("adr3");
			String postnr = record.get("postnr");
			String betbet = record.get("betbet");
			String land = record.get("land");
			String kpers = record.get("kpers");
			String tlf = record.get("tlf");

			VisleveDao dao = new VisleveDao();
			dao.setAktkod(aktkod);
			dao.setFirma(firma);
			dao.setLevnr(Integer.parseInt(levnr));
			dao.setLnavn(lnavn);
			dao.setRnrale(rnrale);
			dao.setAdr1(adr1);
			dao.setAdr2(adr2);
			dao.setAdr3(adr3);
			dao.setPostnr(Integer.parseInt(postnr));
			dao.setBetbet(betbet);
			dao.setLand(land);
			dao.setKpers(kpers);
			dao.setTlf(tlf);

			visleveDaoService.create(dao);

			count++;

		}

		logger.debug(count + " VISLEVE posts created or changed.");

	}	
	
}
