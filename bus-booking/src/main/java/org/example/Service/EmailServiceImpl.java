package org.example.Service;


import org.example.Dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender javaMailSender;



    @Value("${spring.mail.username}") private String sender;

    @Override
    public String sendSimpleMail(RequestDto requestDto) {
        try {



            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();



            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo("puabhijith7@gmail.com");
            mailMessage.setText("Hi Abhijith \n \n YOUR BOOKING IS SUCCESFULL \n \n Source :"+requestDto.source()+"\nDestination :"+requestDto.destination());
            mailMessage.setSubject("BUS TICKET BOOKED");



            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Successfull";
        }
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
