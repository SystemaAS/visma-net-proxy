package no.systema.visma;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

	
	/**
	 * Transform list of {@link VistranskDao} into {@link List<VistranskHeadDto>
	 * 
	 * @param List<VistranskDao> vistranskDaoList
	 * @return List<VistranskHeadDto>
	 */
	public static List<VistranskHeadDto> transform(List<VistranskDao> vistranskDaoList) {
		final List<VistranskHeadDto> vistranskHeadtDtoList = new ArrayList<VistranskHeadDto>();
		final List<VistranskLineDto> vistranskLineDtoList = new ArrayList<VistranskLineDto>();
		
		Map<Integer, List<VistranskDao>> groupedByBilnr = 
				vistranskDaoList
					.stream()
					.collect(groupingBy(VistranskDao::getBilnr));

		groupedByBilnr.forEach((bilnr, daoList) -> {  //Head and lines to correspond to Visma.net format
			VistranskHeadDto head = new VistranskHeadDto();
			head.setRecnr(daoList.get(0).getRecnr());
			head.setBilnr(daoList.get(0).getBilnr());
			head.setPosnr(daoList.get(0).getPosnr());
			head.setKrdaar(daoList.get(0).getKrdaar());
			head.setKrdmnd(daoList.get(0).getKrdmnd());
			head.setKrddag(daoList.get(0).getKrddag());
			head.setFfdaar(daoList.get(0).getFfdaar());
			head.setFfdmnd(daoList.get(0).getFfdmnd());
			head.setFfddag(daoList.get(0).getFfddag());
			head.setBetbet(daoList.get(0).getBetbet());
			
			daoList.forEach(dao -> {
				//Lines
				VistranskLineDto line = new VistranskLineDto();
				line.setBbelop(dao.getBbelop());
				line.setMomsk(dao.getMomsk());
				line.setKonto(dao.getKonto());
				line.setKsted(dao.getKsted());
				line.setKbarer(dao.getKbarer());
				line.setBiltxt(dao.getBiltxt());

				vistranskLineDtoList.add(line);
				
			});
			
			head.setLines(vistranskLineDtoList);			
			vistranskHeadtDtoList.add(head);
			
		});
		
//		vistranskHeadtDtoList.forEach(head -> System.out.println(ReflectionToStringBuilder.toString(head, ToStringStyle.MULTI_LINE_STYLE) ));
		
		return vistranskHeadtDtoList;
	}
	
}
 