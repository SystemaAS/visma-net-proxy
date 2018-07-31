package no.systema.visma.dto;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import no.systema.jservices.common.dao.VistranslDao;
import no.systema.visma.v1client.api.SupplierInvoiceApi;

/**
 * This class transform rows in VISTRANSL into {@link VistranslHeadDto} and {@link VistranslLineDto} <br>
 * to better suite the Visma.net interfaces in {@link SupplierInvoiceApi}
 * 
 * @author fredrikmoller
 *
 */
public class VistranslTransformer {

	/**
	 * Transform flat list of {@link VistranslDao} into composite list of {@link VistranslHeadDto}.
	 * 
	 * @param vistranslDaoList
	 * @return List of {@link VistranslHeadDto}
	 */
	public static List<VistranslHeadDto> transform(List<VistranslDao> vistranslDaoList) {
		final List<VistranslHeadDto> vistranslHeadtDtoList = new ArrayList<VistranslHeadDto>();
		final List<VistranslLineDto> vistranslLineDtoList = new ArrayList<VistranslLineDto>();
		
		Map<Integer, List<VistranslDao>> groupedByBilnr = 
				vistranslDaoList
					.stream()
					.collect(groupingBy(VistranslDao::getBilnr));

		groupedByBilnr.forEach((bilnr, daoList) -> {  //Head and lines to correspond to Visma.net format
			VistranslHeadDto head = new VistranslHeadDto();
			/*every VISTRANSL contains headerinfo in below attributes, using first row to populate head.*/
			head.setFirma(daoList.get(0).getFirma());
			head.setRecnr(daoList.get(0).getRecnr());
			head.setRefnr(daoList.get(0).getRefnr());
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
			
			daoList.forEach(dao -> { //Lines
				VistranslLineDto line = new VistranslLineDto();
				line.setPosnr(dao.getPosnr());
				line.setBbelop(dao.getBbelop());
				line.setMomsk(dao.getMomsk());
				line.setKonto(dao.getKonto());
				line.setKsted(dao.getKsted());
				line.setKbarer(dao.getKbarer());
				line.setBiltxt(dao.getBiltxt());
				line.setProsnr(dao.getProsnr());

				vistranslLineDtoList.add(line);
				
			});
			
			head.setLines(vistranslLineDtoList);			
			vistranslHeadtDtoList.add(head);
			
		});
		
		return vistranslHeadtDtoList;

	}
	
}
 