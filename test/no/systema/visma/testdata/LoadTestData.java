package no.systema.visma.testdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

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
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.VisleveDaoService;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.jservices.common.dao.services.VistranslDaoService;
import no.systema.visma.integration.Customer;
import no.systema.visma.integration.CustomerInvoice;
import no.systema.visma.integration.DtoValueHelper;
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
		loadCustomers();
		//loadCustmerInvoices();
		
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

		logger.debug(count + " VISKUNDE poster skapade.");

	}
	
}
