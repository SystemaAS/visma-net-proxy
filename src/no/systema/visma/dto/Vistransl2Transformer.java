package no.systema.visma.dto;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import no.systema.jservices.common.dao.Vistransl2Dao;
import no.systema.visma.v1client.api.SupplierInvoiceApi;

/**
 * This class transform rows in VISTRANSL into {@link VistranslHeadDto} and {@link VistranslLineDto} <br>
 * to better suite the Visma.net interfaces in {@link SupplierInvoiceApi}
 * 
 * @author fredrikmoller
 *
 */
public class Vistransl2Transformer {

	/**
	 * Transform flat list of {@link VistranslDao} into composite list of {@link VistranslHeadDto}.
	 * 
	 * @param vistranslDaoList
	 * @return List of {@link VistranslHeadDto}
	 */
	public static List<VistranslHeadDto> transform(List<Vistransl2Dao> vistranslDaoList) {
		final List<VistranslHeadDto> vistranslHeadtDtoList = new ArrayList<VistranslHeadDto>();
		
		Map<Integer, List<Vistransl2Dao>> groupedByBilnr = 
				vistranslDaoList
					.stream()
					.collect(groupingBy(Vistransl2Dao::getBilnr));

		groupedByBilnr.forEach((bilnr, daoList) -> {  //Head and lines to correspond to Visma.net format
			List<VistranslLineDto> vistranslLineDtoList = new ArrayList<VistranslLineDto>();
			VistranslHeadDto head = new VistranslHeadDto();
			/*every VISTRANSL contains headerinfo in below attributes, using first row to populate head.*/
			head.setFirma(daoList.get(0).getFirma());
			head.setResnr(daoList.get(0).getResnr());
			head.setKrnr(daoList.get(0).getKrnr());
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
			head.setLkid(daoList.get(0).getLkid());	
			head.setPath(daoList.get(0).getPath());
			head.setFakkre(daoList.get(0).getFakkre());
			
			daoList.forEach(dao -> { //Lines
				VistranslLineDto line = new VistranslLineDto();
				line.setPosnr(dao.getPosnr());
				line.setNbelpo(dao.getNbelpo());
				line.setMomsk(dao.getMomsk());
				line.setKontov(dao.getKontov());
				line.setKsted(dao.getKsted());
				line.setBiltxt(dao.getBiltxt());

				vistranslLineDtoList.add(line);
				
			});
			
			head.setLines(vistranslLineDtoList);			
			vistranslHeadtDtoList.add(head);
			
		});
		
		return vistranslHeadtDtoList;

	}
	
}
 