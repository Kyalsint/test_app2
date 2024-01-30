package com.example.demo;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfServiceImpl implements PdfService {

	@Override
	public ByteArrayInputStream convertHtmlToPdf(String htmlContent) {
		  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        ITextRenderer renderer = new ITextRenderer();
	        renderer.setDocumentFromString(htmlContent);
	        renderer.layout();
	        try {
				renderer.createPDF(outputStream, false);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        renderer.finishPDF();
	        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
	        return inputStream;
	}

}
