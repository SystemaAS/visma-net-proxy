package no.systema.visma.dto;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import no.systema.jservices.common.dao.VistranshDao;
import no.systema.visma.v1client.api.JournalTransactionApi;

/**
 * This class transform rows in VISTRANSH into {@link VistranshHeadDto} and {@link VistranshLineDto} <br>
 * to better suite the Visma.net interfaces in {@link JournalTransactionApi}
 * 
 * @author fredrikmoller
 *
 */
public class VistranshTransformer {
	/**
	 * Transform flat list of {@link VistranshDao} into composite list of {@link VistranshHeadDto}.
	 * 
	 * @param vistranshDaoList
	 * @return List of {@link VistranshHeadDto}
	 */
	public static List<VistranshHeadDto> transform(List<VistranshDao> vistranshDaoList) {
		final List<VistranshHeadDto> vistranshHeadtDtoList = new ArrayList<VistranshHeadDto>();
		
		Map<Integer, List<VistranshDao>> groupedByBilnr = 
				vistranshDaoList
					.stream()
					.collect(groupingBy(VistranshDao::getBilnr));

		groupedByBilnr.forEach((bilnr, daoList) -> { 
			List<VistranshLineDto> vistranshLineDtoList = new ArrayList<VistranshLineDto>();
			VistranshHeadDto head = new VistranshHeadDto();
			/*every VISTRANSH contains headerinfo in below attributes, using first row to populate head.*/
			head.setFirma(daoList.get(0).getFirma());
			head.setBilnr(daoList.get(0).getBilnr());
			head.setBilaar(daoList.get(0).getBilaar());
			head.setBilmnd(daoList.get(0).getBilmnd());
			head.setBildag(daoList.get(0).getBildag());
			head.setKrdaar(daoList.get(0).getKrdaar());
			head.setKrdmnd(daoList.get(0).getKrdmnd());
			head.setKrddag(daoList.get(0).getKrddag());
			head.setPeraar(daoList.get(0).getPeraar());
			head.setPernr(daoList.get(0).getPernr());
			head.setValkox(daoList.get(0).getValkox());
			head.setValku1(daoList.get(0).getValku1());	
			head.setPath(daoList.get(0).getPath());
			head.setFakkre(daoList.get(0).getFakkre());
			
			daoList.forEach(dao -> { //Lines
				VistranshLineDto line = new VistranshLineDto();
				line.setPosnr(dao.getPosnr());
				line.setNbelpo(dao.getNbelpo());
				line.setMomsk(dao.getMomsk());
				line.setKontov(dao.getKontov());
				line.setKsted(dao.getKsted());
				line.setBiltxt(dao.getBiltxt());
				line.setFakkre(dao.getFakkre());

				vistranshLineDtoList.add(line);
				
			});
			
			head.setLines(vistranshLineDtoList);			
			vistranshHeadtDtoList.add(head);
			
		});
		
		return vistranshHeadtDtoList;

	}
	
}
 