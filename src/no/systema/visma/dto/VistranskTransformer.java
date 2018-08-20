package no.systema.visma.dto;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import no.systema.jservices.common.dao.VistranskDao;
import no.systema.visma.v1client.api.CustomerInvoiceApi;

/**
 * This class transform rows in VISTRANSK into {@link VistranskHeadDto} and {@link VistranskLineDto} <br>
 * to better suite the Visma.net interfaces in {@link CustomerInvoiceApi}
 * 
 * @author fredrikmoller
 *
 */
public class VistranskTransformer {

	private static Logger logger = Logger.getLogger(VistranskTransformer.class);	
	
	/**
	 * Transform flat list of {@link VistranskDao} into composite list of {@link VistranskHeadDto}.
	 * 
	 * @param vistranskDaoList
	 * @return List of {@link VistranskHeadDto}
	 */
	public static List<VistranskHeadDto> transform(List<VistranskDao> vistranskDaoList) {
		final List<VistranskHeadDto> vistranskHeadtDtoList = new ArrayList<VistranskHeadDto>();
		
		Map<Integer, List<VistranskDao>> groupedByBilnr = 
				vistranskDaoList
					.stream()
					.collect(groupingBy(VistranskDao::getBilnr));

		groupedByBilnr.forEach((bilnr, daoList) -> {  //Head and lines to correspond to Visma.net format
			List<VistranskLineDto> vistranskLineDtoList = new ArrayList<VistranskLineDto>();
			VistranskHeadDto head = new VistranskHeadDto();
			/*every VISTRANSK contains headerinfo in below attributes, using first row to populate head.*/
			head.setFirma(daoList.get(0).getFirma());
			head.setResnr(daoList.get(0).getResnr());
			head.setBilnr(daoList.get(0).getBilnr());
			head.setBilaar(daoList.get(0).getBilaar());
			head.setBilmnd(daoList.get(0).getBilmnd());
			head.setBildag(daoList.get(0).getBildag());
			head.setKrdaar(daoList.get(0).getKrdaar());
			head.setKrdmnd(daoList.get(0).getKrdmnd());
			head.setKrddag(daoList.get(0).getKrddag());
			head.setFfdaar(daoList.get(0).getFfdaar());
			head.setFfdmnd(daoList.get(0).getFfdmnd());
			head.setFfddag(daoList.get(0).getFfddag());
			head.setBetbet(daoList.get(0).getBetbet());
			head.setPeraar(daoList.get(0).getPeraar());
			head.setPernr(daoList.get(0).getPernr());
			head.setValkox(daoList.get(0).getValkox());
			head.setValku1(daoList.get(0).getValku1());	
			head.setFakkre(daoList.get(0).getFakkre());
			
			daoList.forEach(dao -> { //Lines
				VistranskLineDto line = new VistranskLineDto();
				line.setPosnr(dao.getPosnr());
				line.setNbelpo(dao.getNbelpo());
				line.setMomsk(dao.getMomsk());
				line.setKonto(dao.getKonto());
				line.setKsted(dao.getKsted());
				line.setBiltxt(dao.getBiltxt());

				vistranskLineDtoList.add(line);
				
			});
			
			head.setLines(vistranskLineDtoList);			
			vistranskHeadtDtoList.add(head);
			
		});
		
		return vistranskHeadtDtoList;

	}
	
}
 