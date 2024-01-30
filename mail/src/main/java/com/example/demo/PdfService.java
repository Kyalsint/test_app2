package com.example.demo;

import java.io.ByteArrayInputStream;

public interface PdfService {
	 ByteArrayInputStream convertHtmlToPdf(String htmlContent);
}
