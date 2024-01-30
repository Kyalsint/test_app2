package com.example.demo;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MailController {
	
    @Autowired
    private PdfService pdfService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@GetMapping("/order")
	public String emailPage() {
		return "myorder";
	}
	//Sending email
	@PostMapping("/send-mail")
	public String sendMail(@RequestParam("recipient") String recipient,@RequestParam("subject") String subject, @RequestParam("msgBody") String msgBody) {
		
		String context="<h3>Order List </h3> <hr> "
				+ "<table> <tr> <td> TShirt </td> <td> price: 20000 Ks x qty : 1</td> </tr>"
				+ " <tr> <td> Shoes </td> <td> price: 300000 Ks x  qty :1  </td> </tr>"
				+ "<tr><td colspan=2> Grand total : 320000 Ks </td> </tr> </table>";
		
	
		
		MailDetail mail=new MailDetail();
	     mail.setRecipient(recipient);
	     mail.setSubject(subject);
	     mail.setMsgBody(context);
		
		if(mailService.sendMail(mail)) {
			return "mailsuccess";
		}
		else {
			return "mailfail";
		}
		
		
	}
	//Sending email with attachment
	@PostMapping("/send-mail-attachment")
	public String sendMailWithAttachment(@RequestParam("recipient") String recipient,@RequestParam("subject") String subject, @RequestParam("msgBody") String msgBody)
	{
		
		
		String context="<h3>Order List </h3> <hr> "
				+ "<table> <tr> <td> TShirt </td> <td> price: 20000 Ks x qty : 1</td> </tr>"
				+ " <tr> <td> Shoes </td> <td> price: 300000 Ks x  qty :1  </td> </tr>"
				+ "<tr><td colspan=2> Grand total : 320000 Ks </td> </tr> </table>";
		
		
		 MailDetail mail=new MailDetail();
	     mail.setRecipient(recipient);
	     mail.setSubject(subject);
	     mail.setMsgBody(context);
	     
	     //String fileName=StringUtils.cleanPath(file.getOriginalFilename());
	     //mail.setAttachment(fileName);
	     
	     
	
	if(mailService.sendMailWithAttachment(mail)) {
		return "mailsuccess";
	}
	else {
		return "mailfail";
	}
	
	
	}
	
	
	@PostMapping("/generatePdfFile")
    public void generatePdfFile(HttpServletResponse response, String contentToGenerate) throws IOException {
        ByteArrayInputStream byteArrayInputStream = pdfService.convertHtmlToPdf(contentToGenerate);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=file.pdf");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }


}
