package com.example.demo;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.FileSystemResource;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;

@Service
public class MailServiceImp implements MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public boolean sendMail(MailDetail mailDetail) {
		// Try block to check for exceptions handling
				try {

					String from = "cchawchaw86@gmail.com";
					String to = mailDetail.getRecipient();
					
					MimeMessage message = mailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message);
					
					helper.setSubject("This is an HTML Order List ");
					helper.setFrom(from);
					helper.setTo(to);

					boolean html = true;
					helper.setText(mailDetail.getMsgBody(),html);
					mailSender.send(message);
					
					
					return true;
				}

				// Catch block to handle the exceptions
				catch (Exception e) {
					return false;
				}
	}

	@Override
	public boolean sendMailWithAttachment(MailDetail mailDetail) {
		// Creating a Mime Message
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		try {

			// Setting multipart as true for attachment to be send
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(mailDetail.getRecipient());
			mimeMessageHelper.setSubject(mailDetail.getSubject());
			mimeMessageHelper.setText(mailDetail.getMsgBody());

    // Adding the file attachment
	FileSystemResource file = new FileSystemResource(new File(mailDetail.getAttachment()));

			mimeMessageHelper.addAttachment(file.getFilename(), file);

			// Sending the email with attachment
			mailSender.send(mimeMessage);
			return true;
		}

		// Catch block to handle the MessagingException
		catch (MessagingException e) {

			// Display message when exception is occurred
			return false;
		}
	}

}
