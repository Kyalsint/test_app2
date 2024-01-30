package com.example.demo;



public interface MailService {
	boolean sendMail(MailDetail mailDetail);
	boolean sendMailWithAttachment(MailDetail mailDetail);

}
