package no.systema.visma.integration;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import no.systema.visma.v1client.model.DtoValueDecimal;

public class TestJHelper {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToDtoDecimalBigDecimal() {
		BigDecimal o = new BigDecimal(12.66).setScale(3, BigDecimal.ROUND_UP); 
		DtoValueDecimal result = Helper.toDtoDecimal(o);
		assertEquals(o.doubleValue(), result.getValue().doubleValue(),0);
		
	}

	@Test
	public void testToDtoDecimalDouble() {

		Double o = new Double(12.99); 
		DtoValueDecimal result = Helper.toDtoDecimal(o);
		assertEquals(o.doubleValue(), result.getValue().doubleValue(),0);
		
	}	
	
	
	
}
