package no.systema.visma.dto;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.SneakyThrows;
import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.jservices.common.util.StringUtils;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJVistranskTransformer {

	private static Logger logger = LoggerFactory.getLogger(TestJVistranskTransformer.class);	
	
	@Autowired
	VistranskDaoService vistranskDaoService;


	@Test
	public void testGrouping() {

		List<VistranskHeadDto> list = VistranskTransformer.transform( getList() );
		assertEquals(list.size(), 2);
		
	
	}		
	
	
	@Test
	public void testGetFakkre() {

		loadCustomerInvoices();
		List<VistranskHeadDto> list = VistranskTransformer.transform( vistranskDaoService.findAll(null) );
		assertEquals(2,list.size());
		
		list.forEach(headDto -> {
			logger.info(" headDto.getFakkre()="+headDto.getFakkre());
			if (headDto.getFakkre().equals("K")) {
				logger.info("is CustomerCreditNote, headDto.bilnr="+headDto.getBilnr());
			}
		});
		
	
	}	
	
	@SneakyThrows
	private void loadCustomerInvoices() {
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
			if (StringUtils.hasValue(valkox)) {
				dao.setValkox(valkox);
				dao.setValku1(new BigDecimal(valku1));
			}
			dao.setFakkre(fakkre);
			
			vistranskDaoService.create(dao);

			count++;

		}

		in.close();
		
		logger.debug(count + " VISTRANSK posts created or changed.");

	}		
	
	
	private List<VistranskDao> getList() {
		List<VistranskDao> list = new ArrayList<VistranskDao>();
		
		list.add(getVistranskDao(1, 100, 1, "T-shirt"));
		list.add(getVistranskDao(1, 100, 2, "Hat"));
		
		list.add(getVistranskDao(2, 200, 1, "T-shirt XL"));
		list.add(getVistranskDao(2, 200, 2, "Hat XL"));
		
		return list;
		
	}
	
	
	private VistranskDao getVistranskDao(int resnr, int bilnr, int posnr, String biltxt) {
		VistranskDao dao = new VistranskDao();
		dao.setResnr(resnr);
		dao.setBilnr(bilnr);
		dao.setPosnr(posnr);
		dao.setBiltxt(biltxt);
		
		dao.setAktkod("A");

		
		return dao;
	}		
	
	
	
}
