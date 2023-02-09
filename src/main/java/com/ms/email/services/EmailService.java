package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository repository;
    private final JavaMailSender emailSender;

    @Transactional
    public EmailModel sendEmail(EmailModel model) {
        model.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(message.getFrom());
            message.setTo(model.getEmailTo());
            message.setSubject(model.getSubject());
            message.setText(model.getText());
            emailSender.send(message);

            model.setStatusEmail(StatusEmail.SENT);
        } catch (MailException ex) {
            model.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return repository.save(model);
        }
    }

    public Page<EmailModel> findAll(Pageable pageable) { return repository.findAll(pageable); }

    public Optional<EmailModel> findById(UUID emailId) { return repository.findById(emailId); }
}
