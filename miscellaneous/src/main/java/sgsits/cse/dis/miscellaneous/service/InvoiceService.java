package sgsits.cse.dis.miscellaneous.service;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import sgsits.cse.dis.miscellaneous.model.TelephoneComplaintModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class InvoiceService {

	Logger log = LogManager.getLogger(InvoiceService.class);

	private final String telephone_template = "/jasper/Tele_comp.jrxml";

	public void generateInvoiceFor(TelephoneComplaintModel teleComp) throws IOException {

		File pdfFile = File.createTempFile("my-tele-comp", ".pdf");

		log.info(String.format("Pdf path : %s", pdfFile.getAbsolutePath()));

		ArrayList<TelephoneComplaintModel> dataList = new ArrayList<>();
		
		dataList.add(teleComp);
		
		try (FileOutputStream pos = new FileOutputStream(pdfFile)) {
			// Load invoice jrxml template.
			final JasperReport report = loadTemplate();

			// Create parameters map.
			final Map<String, Object> parameters = parameters(teleComp);

			// Create an empty datasource.
			//final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
			
			// Render as PDF.
			//JasperReportsUtils.renderAsPdf(report, null, dataSource, pos);
			JasperReportsUtils.renderAsPdf(report, parameters, beanColDataSource, pos);

		} catch (final Exception e) {
			log.error(String.format("An error occured during PDF creation: %s", e));
		}
	}

	// Fill template order parameters
	private Map<String, Object> parameters(TelephoneComplaintModel teleComp) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("teleComp", teleComp);
		return parameters;
	}

	// Load invoice jrxml template
	private JasperReport loadTemplate() throws JRException {
		final InputStream reportInputStream = getClass().getResourceAsStream(telephone_template);
		final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
		return JasperCompileManager.compileReport(jasperDesign);
	}

}
