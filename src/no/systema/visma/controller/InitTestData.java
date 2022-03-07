package no.systema.visma.controller;

import java.math.BigDecimal;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import lombok.SneakyThrows;
import no.systema.jservices.common.dao.VistranskDao;
import no.systema.jservices.common.dao.services.VistranskDaoService;
import no.systema.jservices.common.util.StringUtils;

/**
 * FOR TESTPURPOSE ONLY
 * 
 * 
 * 
 * @author fredrikmoller
 *
 */
//TODO: Remove when loadtests are over!!
@Service
public class InitTestData {
	private static Logger logger = LoggerFactory.getLogger(InitTestData.class.getName());	

	@Autowired
	VistranskDaoService vistranskDaoService;	
	
	@SneakyThrows
	void loadCustomerInvoicesAlot(int rows) {
		vistranskDaoService.deleteAll(null);
		int count = 1;

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		
		while (count <= rows) {

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
			dao.setPath("/pdf/arc/fa20180284203PIPbrEjAeI.pdf");
			if (StringUtils.hasValue(valkox)) {
				dao.setValkox(valkox);
				dao.setValku1(new BigDecimal(valku1));
			}
			
			vistranskDaoService.create(dao);

			count++;

		}

		stopWatch.stop();
		logger.info(String.format("Stopwatch-time in millis %s on %s rows.", stopWatch.getTotalTimeMillis(), count));

		logger.debug(count + " VISTRANSK posts created.");

	}	

	
	
	
}
