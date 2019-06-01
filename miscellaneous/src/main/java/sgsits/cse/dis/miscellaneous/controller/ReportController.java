package sgsits.cse.dis.miscellaneous.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sgsits.cse.dis.miscellaneous.model.TelephoneComplaintModel;
import sgsits.cse.dis.miscellaneous.service.InvoiceService;

@CrossOrigin(origins = "*")
@RestController
public class ReportController {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public void reportGeneration() throws IOException {
        TelephoneComplaintModel teleComp = new TelephoneComplaintModel(2, "2019-05-28", "Not working", 2489, "2019-05-30");
        invoiceService.generateInvoiceFor(teleComp);
	}
}
