package no.systema.visma.integration;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import no.systema.visma.dto.VistranskHeadDto;
import no.systema.visma.v1client.model.DtoValueDateTime;
import no.systema.visma.v1client.model.DtoValueDecimal;
import no.systema.visma.v1client.model.DtoValueInt32;
import no.systema.visma.v1client.model.DtoValueNullableDecimal;
import no.systema.visma.v1client.model.DtoValueNullableSupplierInvoiceTypes;
import no.systema.visma.v1client.model.DtoValueString;
import no.systema.visma.v1client.model.DtoValueNullableSupplierInvoiceTypes.ValueEnum;

/**
 * This helper is transforming proprietary SYSPED types into Visma.net types.
 * 
 * @author fredrikmoller
 *
 */
public class DtoValueHelper {

	/**
	 * Converts Object into DtoValueString.
	 * 
	 * @param Object o
	 * @return DtoValueString
	 */
	public static DtoValueString toDtoString(java.lang.Object o) {
		if (o == null || o.toString().isEmpty()) {
			return null;
		}
		
		return new DtoValueString().value(o.toString());

	}	
	
	/**
	 * Convert Object into DtoValueDecimal.<br>
	 * 
	 * @param o supports Double and BigDecimal
	 * @return DtoValueDecimal
	 */
	public static DtoValueDecimal toDtoDecimal(java.lang.Object o) {
		DtoValueDecimal dto;
		if (o == null) {
			return null;
		}
		if (o instanceof Double) {
			Double oo = ((Double) o).doubleValue();
			dto = new DtoValueDecimal().value(oo);
		} else if (o instanceof BigDecimal) {
			Double oo = ((BigDecimal) o).doubleValue();
			dto = new DtoValueDecimal().value(oo);
		} else {
			throw new IllegalArgumentException(o.getClass()+" Not supported");
		}

		return dto;

	}

	/**
	 * Convert Object into DtoValueInt32.<br>
	 * 
	 * @param o supports Integer
	 * @return DtoValueInt32
	 */
	public static DtoValueInt32 toDtoValueInt32(java.lang.Object o) {
		DtoValueInt32 dto;
		if (o == null) {
			return null;
		}
		if (o instanceof Integer) {
			Integer oo = (Integer) o;
			dto = new DtoValueInt32().value(oo);
		}  else {
			throw new IllegalArgumentException(o.getClass()+" Not supported");
		}
		
		return dto;
	}		

	/**
	 * Convert SYSPED date into DtoValueDateTime.<br>
	 * 
	 * @param aar
	 * @param mnd
	 * @param dag
	 * @return DtoValueDateTime using LocalDateTime
	 */
	public static DtoValueDateTime toDtoValueDateTime(int aar, int mnd, int dag) {
		DtoValueDateTime dto = new DtoValueDateTime();
		Objects.requireNonNull(aar, "aar is mandatory");
		Objects.requireNonNull(mnd, "mnd is mandatory");
		Objects.requireNonNull(dag, "dag is mandatory");

		if (Integer.signum(aar) == 0) {
			throw new RuntimeException("aar has wrong date-format, aar="+aar);
		}
		if (Integer.signum(mnd) == 0) {
			throw new RuntimeException("mnd has wrong date-format, mnd="+mnd);
		}
		if (Integer.signum(dag) == 0) {
			throw new RuntimeException("dag has wrong date-format, dag="+dag);
		}
		
		LocalDateTime value = LocalDateTime.of(aar, mnd, dag, 0, 0, 0);

		dto.setValue(value);

		
		return dto;
	}			
	
	
	/**
	 * Convert Object into DtoValueNullableDecimal.<br>
	 * 
	 * @param o supports Double and BigDecimal
	 * @return DtoValueNullableDecimal
	 */
	public static DtoValueNullableDecimal toDtoValueNullableDecimal(java.lang.Object o) {
		DtoValueNullableDecimal dto;
		if (o == null) {
			return null;
		}
		if (o instanceof Double) {
			Double oo = ((Double) o).doubleValue();
			dto = new DtoValueNullableDecimal().value(oo);
		} else if (o instanceof BigDecimal) {
			Double oo = ((BigDecimal) o).doubleValue();
			dto = new DtoValueNullableDecimal().value(oo);
		} else {
			throw new IllegalArgumentException(o.getClass()+" Not supported");
		}

		return dto;

	}	

	/**
	 * Return a {@linkplain DtoValueNullableSupplierInvoiceTypes} based on provided {@linkplain ValueEnum}.
	 * 
	 * @param value
	 * @return {@linkplain DtoValueNullableSupplierInvoiceTypes}
	 */
	public static DtoValueNullableSupplierInvoiceTypes getSupplierInvoiceType(ValueEnum value) {
		DtoValueNullableSupplierInvoiceTypes dto = new DtoValueNullableSupplierInvoiceTypes();
		dto.setValue(value);
	
		return dto;
		
	}	
	
	/**
	 * Return a {@linkplain FileSystemResource} from provided path.
	 * 
	 * @param path in AS400-format
	 * @return {@linkplain FileSystemResource}
	 * @throws IOException
	 */
	public static Resource getAttachment(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
        	throw new RuntimeException("Could create file on path:"+path);
        }
        return new FileSystemResource(file);	
	}
	
	
}
