package no.systema.visma.transaction;

import java.util.List;

import org.slf4j.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.jakewharton.fliptables.FlipTableConverters;

import no.systema.visma.dto.PrettyPrintVistranskError;
import no.systema.visma.dto.PrettyPrintVistranslError;
import no.systema.visma.integration.CustomerInvoice;
import no.systema.visma.integration.SupplierInvoice;
import no.systema.visma.testdata.LoadTestData;

/**
 * This is wrapper, acting as the testsuite to run and verify all transactions-endpoint:
 * {@linkplain CustomerInvoice}
 * {@linkplain SupplierInvoice}
 * {@linkplain JournalInvoice}
 * 
 * How-to:
 * <li> Set date in trans-files to today, fore traceability. E.g. krdmnd,krddag in vistransk.csv</li>
 * <li> Increment bilnr to avoid duplicates e.g. bilnr in vistransk.csv. Or delete them in Visma.net</li> 
 * <li> Run Junit test {@link #insertTestDataAndRunAllEndpoint}</li>
 * <li> Ensure that errorLists are empty</li>
 * 
 * Logger output-tips: Run Configurations::JRE , add separate JRE for JUnit, add jvm-arg: -Dlog4j.configuration=log4j-test.properties
 * 
 * @author fredrikmoller
 * @date 2018-10-03
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:test-configuration.xml")
public class TestJAll {
	private static Logger logger = LoggerFactory.getLogger(TestJAll.class);	

	@Autowired
	LoadTestData loadTest;

	@Autowired
	CustomerInvoiceTransactionManager customerInvoicetransactionManager;	

	@Autowired
	SupplierInvoiceTransactionManager supplierInvoicetransactionManager;	
	
	@Autowired
	JournalTransactionTransactionManager journalTransactiontransactionManager;		
	
	@Test
	public void insertTestDataAndRunAllEndpoint(){
		//CustomerInvoices
		loadTest.loadCustomerInvoices();
		List<PrettyPrintVistranskError> cierrorList = customerInvoicetransactionManager.syncronizeCustomerInvoices();
		logger.info(FlipTableConverters.fromIterable(cierrorList, PrettyPrintVistranskError.class));

		//SupplierInvoices
		loadTest.loadSupplierInvoices();
		List<PrettyPrintVistranslError> sierrorList = supplierInvoicetransactionManager.syncronizeSupplierInvoices();
		logger.info(FlipTableConverters.fromIterable(sierrorList, PrettyPrintVistranslError.class));

		//JournalTransactions
		loadTest.loadSupplierInvoicesAsJournalTransaction();
		List<PrettyPrintVistranslError> jterrorList = journalTransactiontransactionManager.syncronizeJournalTransaction();
		logger.info(FlipTableConverters.fromIterable(jterrorList, PrettyPrintVistranslError.class));
		
		
	}
	
}
