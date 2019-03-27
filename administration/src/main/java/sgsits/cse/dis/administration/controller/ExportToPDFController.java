/*package sgsits.cse.dis.administration.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class ExportToPDFController {
	
	@RequestMapping(value = "generatePDF", method = RequestMethod.GET)
	public void exportToPDF(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		OutputStream os = null;
		try {
			
			Data data = new Data("8596582","CHANDRA SHEKHAR","03/30/2016","$2,000.00","1005205",
					"452562");

			StringTemplate page = getStringTemplate();
			page.setAttribute("data",data);
			
			String content = page.toString();

			final HtmlCleaner htmlCleaner = new HtmlCleaner();
			final TagNode tagNode = htmlCleaner.clean(content);
			content = htmlCleaner.getInnerHtml(tagNode);

			os = response.getOutputStream();

			Document document = null;

			document = new Document(PageSize.A4);

			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final PdfWriter writer = PdfWriter.getInstance(document, baos);
			document.open();

			final HtmlPipelineContext htmlContext = new HtmlPipelineContext();

			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

			final CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
			final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, new HtmlPipeline(htmlContext,
					new PdfWriterPipeline(document, writer)));
			final XMLWorker worker = new XMLWorker(pipeline, true);
			final XMLParser parser = new XMLParser(worker);

			try {
				parser.parse(new StringReader(content));
			} catch (final Exception e) {
				e.printStackTrace();
				
			}

			document.close();
			response.setContentType("Content-Type: text/html; charset=UTF-8");
			response.addHeader(
					"Content-Disposition",
					"attachment; filename=employee_details.pdf");
			response.setContentLength(baos.size());
			baos.writeTo(os);
		} catch (final IOException | DocumentException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	StringTemplate getStringTemplate() {
		final StringTemplateGroup group = new StringTemplateGroup("Generators");
		return group.getInstanceOf("cwn");
	}
}
*/