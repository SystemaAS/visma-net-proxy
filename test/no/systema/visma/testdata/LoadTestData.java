package no.systema.visma.testdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import lombok.SneakyThrows;
import no.systema.jservices.common.dao.ViskundeDao;
import no.systema.jservices.common.dao.VisleveDao;
import no.systema.jservices.common.dao.VistranshDao;
import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.VistranslDao;
import no.systema.jservices.common.dao.services.ViskundeDaoService;
import no.systema.jservices.common.dao.services.VisleveDaoService;
import no.systema.jservices.common.dao.services.VistranshDaoService;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.jservices.common.dao.services.VistranslDaoService;
import no.systema.jservices.common.util.StringUtils;
import no.systema.visma.integration.Customer;
import no.systema.visma.integration.CustomerInvoice;
import no.systema.visma.integration.JournalTransaction;
import no.systema.visma.integration.Supplier;
import no.systema.visma.integration.SupplierInvoice;

/**
 * This class is managing testdata from cvs-files for entities: <br>
 * <li>
 * {@link Customer}
 * {@link CustomerInvoice}
 * {@link Supplier}
 * {@link SupplierInvoice}
 * {@link JournalTransaction}
 * </li>
 *  
 * 
 * @author fredrikmoller
 *
 */


@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
@Service
public class LoadTestData {
	
	private static Logger logger = LoggerFactory.getLogger(LoadTestData.class);	
	
	@Autowired
	ViskundeDaoService viskundeDaoService;
	
	@Autowired
	VistranskDaoService vistranskDaoService;
	
	@Autowired
	VisleveDaoService visleveDaoService;
	
	@Autowired
	VistranslDaoService vistranslDaoService;	

	@Autowired
	VistranshDaoService vistranshDaoService;		
	
	
	@Test
	public void runAll() {
		loadCustomers();
		loadCustomerInvoices();
		loadSuppliers();
		loadSupplierInvoices();
		loadSupplierInvoicesAsJournalTransaction();
	}
	
	@SneakyThrows
	private void loadCustomers() {
		viskundeDaoService.deleteAll(null);
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
			String sypoge = record.get("sypoge");
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
			dao.setSypoge(sypoge);
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

		in.close();
		
		logger.debug(count + " VISKUNDE posts created or changed.");

	}
	
	@SneakyThrows
	public void loadCustomerInvoices() {
		vistranskDaoService.deleteAll(null);
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
			String resnr = record.get("resnr");
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
			String kontov = record.get("kontov");
			String ksted = record.get("ksted");
			String momsk = record.get("momsk"); 		
			String nbelpo = record.get("nbelpo");
			String valkox = record.get("valkox");
			String valku1 = record.get("valku1");
			String fakkre = record.get("fakkre");
			
			VistranskDao dao = new VistranskDao();
			dao.setAktkod(aktkod);
			dao.setFirma(firma);
			dao.setResnr(Integer.parseInt(resnr));
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
			dao.setKontov(Integer.parseInt(kontov));
			dao.setKsted(Integer.parseInt(ksted));
			dao.setMomsk(momsk);  
			dao.setNbelpo(new BigDecimal(nbelpo));
			dao.setFakkre(fakkre);
			dao.setPath("/Users/fredrikmoller/git/visma-net-proxy/test/mr_bean.pdf");
			if (StringUtils.hasValue(valkox)) {
				dao.setValkox(valkox);
				dao.setValku1(new BigDecimal(valku1));
			}
			
			vistranskDaoService.create(dao);

			count++;

		}

		in.close();
		
		logger.debug(count + " VISTRANSK posts created.");

	}	

	@SneakyThrows
	private void loadCustomerInvoicesAlot() {
		vistranskDaoService.deleteAll(null);
		int count = 1;

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		
		while (count <= 5) {

			String aktkod = "A";
			String firma = "SY";
			String resnr = "111";
			String bilnr = "999";
			String posnr = String.valueOf(count);
			String bilaar = "2018";
			String bilmnd = "9";
			String bildag = "17";
			String peraar = "2018";
			String pernr = "9";
			String krdaar = "2018";
			String krdmnd = "9";
			String krddag = "17";
			String ffdaar = "2018";
			String ffdmnd = "10";
			String ffddag = "05";	
			String biltxt = "biltxt"+count;
			String betbet = "14";
			String kontov = "3010";
			String ksted = "3";
			String momsk = "0"; 		
			String nbelpo = "25";
			String valkox = null;
			String valku1 = null;
			String fakkre = "F";
			
			VistranskDao dao = new VistranskDao();
			dao.setAktkod(aktkod);
			dao.setFirma(firma);
			dao.setResnr(Integer.parseInt(resnr));
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
			dao.setKontov(Integer.parseInt(kontov));
			dao.setKsted(Integer.parseInt(ksted));
			dao.setMomsk(momsk);  
			dao.setNbelpo(new BigDecimal(nbelpo));
			dao.setFakkre(fakkre);
			dao.setPath("/Users/fredrikmoller/git/visma-net-proxy/test/mr_bean.pdf");
			if (StringUtils.hasValue(valkox)) {
				dao.setValkox(valkox);
				dao.setValku1(new BigDecimal(valku1));
			}
			
			vistranskDaoService.create(dao);

			count++;

		}

		stopWatch.stop();
		System.out.println(String.format("Stopwatch-time in millis %s on %s rows.", stopWatch.getTotalTimeMillis(), count));
		System.out.println("stopWatch.prettyPrint="+stopWatch.prettyPrint()+ " on "+count+" rows.");
		System.out.println("stopWatch.shortSummary()+="+stopWatch.shortSummary()+ " on "+count+" rows.");
		System.out.println("stopWatch.toString="+stopWatch.toString()+ " on "+count+" rows.");
		System.out.println("stopWatch="+stopWatch+ " on "+count+" rows.");
		logger.debug(count + " VISTRANSK posts created.");

	}	
	
	
	@SneakyThrows
	private void loadSuppliers() {
		visleveDaoService.deleteAll(null);
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
			String lnavn = record.get("lnavn");
			String rnrale = record.get("rnrale");
			String adr1 = record.get("adr1");
			String adr2 = record.get("adr2");
			String adr3 = record.get("adr3");
			String postnr = record.get("postnr");
			String postnu = record.get("postnu");		
			String betbet = record.get("betbet");
			String valkod = record.get("valkod");
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
			dao.setPostnu(postnu);
			dao.setBetbet(betbet);
			dao.setValkod(valkod);
			dao.setLand(land);
			dao.setKpers(kpers);
			dao.setTlf(tlf);

			visleveDaoService.create(dao);

			count++;

		}

		in.close();
		
		logger.debug(count + " VISLEVE posts created.");

	}	

	@SneakyThrows
	public  void loadSupplierInvoices() {
		vistranslDaoService.deleteAll(null);
		Resource vistranslFile = new ClassPathResource("vistransl.csv");
		Reader in = null;
		Iterable<CSVRecord> records = null;
		int count = 0;
		try {
			in = new BufferedReader(new InputStreamReader(vistranslFile.getInputStream()));
			records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			logger.error("Could not read file", e);
		}

		for (CSVRecord record : records) {

			String aktkod = record.get("aktkod");
			String firma = record.get("firma");
			String resnr = record.get("resnr");
			String refnr = record.get("refnr");
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
			String kontov = record.get("kontov");
			String ksted = record.get("ksted");
			String momsk = record.get("momsk");  		
			String nbelpo = record.get("nbelpo");
			String valkox = record.get("valkox");
			String valku1 = record.get("valku1");
			String krnr = record.get("krnr");
			String lkid = record.get("lkid");
			String fakkre = record.get("fakkre");

			VistranslDao dao = new VistranslDao();
			dao.setAktkod(aktkod);
			dao.setFirma(firma);
			dao.setResnr(Integer.parseInt(resnr));
			dao.setRefnr(Integer.parseInt(refnr));
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
			dao.setKontov(Integer.parseInt(kontov));
			dao.setKsted(Integer.parseInt(ksted));
			dao.setMomsk(momsk);  
			dao.setNbelpo(new BigDecimal(nbelpo));
			if (StringUtils.hasValue(valkox)) {
				dao.setValkox(valkox);
				dao.setValku1(new BigDecimal(valku1));
			}
			dao.setKrnr(krnr);
			dao.setLkid(lkid);
			dao.setFakkre(fakkre);
			dao.setPath("/Users/fredrikmoller/git/visma-net-proxy/test/mr_bean.pdf");
			
			vistranslDaoService.create(dao);

			count++;

		}
		
		in.close();

		logger.debug(count + " VISTRANSL posts.");

	}		
	
	@SneakyThrows
	public void loadSupplierInvoicesAsJournalTransaction() {
		vistranshDaoService.deleteAll(null);
		Resource vistranslFile = new ClassPathResource("vistransh.csv");
		Reader in = null;
		Iterable<CSVRecord> records = null;
		int count = 0;
		try {
			in = new BufferedReader(new InputStreamReader(vistranslFile.getInputStream()));
			records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
		} catch (IOException e) {
			logger.error("Could not read file", e);
		}

		for (CSVRecord record : records) {

			String firma = record.get("firma");
			String bilnr = record.get("bilnr");
			String posnr = record.get("posnr");
			String bilaar = record.get("bilaar");
			String bilmnd = record.get("bilmnd");
			String bildag = record.get("bildag");
			String peraar = record.get("peraar");
			String pernr = record.get("pernr");
			String ffdaar = record.get("ffdaar");
			String ffdmnd = record.get("ffdmnd");
			String ffddag = record.get("ffddag");	
			String biltxt = record.get("biltxt");
			String betbet = record.get("betbet");
			String kontov = record.get("kontov");
			String ksted = record.get("ksted");
			String momsk = record.get("momsk");  		
			String nbelpo = record.get("nbelpo");
			String valkox = record.get("valkox");
			String valku1 = record.get("valku1");
			String krnr = record.get("krnr");
			String lkid = record.get("lkid");
			String fakkre = record.get("fakkre");

			VistranshDao dao = new VistranshDao();
			dao.setFirma(firma);
			dao.setBilnr(Integer.parseInt(bilnr));
			dao.setPosnr(Integer.parseInt(posnr));
			dao.setBilaar(Integer.parseInt(bilaar));
			dao.setBilmnd(Integer.parseInt(bilmnd));
			dao.setBildag(Integer.parseInt(bildag));
			dao.setPeraar(Integer.parseInt(peraar));
			dao.setPernr(Integer.parseInt(pernr));
			dao.setFfdaar(Integer.parseInt(ffdaar));
			dao.setFfdmnd(Integer.parseInt(ffdmnd));
			dao.setFfddag(Integer.parseInt(ffddag));	
			dao.setBiltxt(biltxt);
			dao.setBetbet(betbet);
			dao.setKontov(Integer.parseInt(kontov));
			dao.setKsted(Integer.parseInt(ksted));
			dao.setMomsk(momsk);  
			dao.setNbelpo(new BigDecimal(nbelpo));
			if (StringUtils.hasValue(valkox)) {
				dao.setValkox(valkox);
				dao.setValku1(new BigDecimal(valku1));
			}
			dao.setKrnr(krnr);
			dao.setLkid(lkid);
			dao.setFakkre(fakkre);
//			dao.setPath("/Users/fredrikmoller/git/visma-net-proxy/test/headf.pdf");
			
			vistranshDaoService.create(dao);

			count++;

		}
		
		in.close();

		logger.debug(count + " VISTRANSH posts created.");

	}
	
	
	
	
	
	
	
	
}
