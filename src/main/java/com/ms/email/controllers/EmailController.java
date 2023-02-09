package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emails")
public class EmailController {

    Logger logger = LogManager.getLogger(EmailController.class);

    private final EmailService emailService;


    @PostMapping
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto dto) {
        EmailModel model = new EmailModel();
        BeanUtils.copyProperties(dto, model);
        emailService.sendEmail(model);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<EmailModel>> getAllEmails(@PageableDefault(page = 0, size = 5, sort = "emailId",
            direction = Sort.Direction.DESC) Pageable pageable) {
        logger.trace("TRACE");
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.warn("WARN");
        logger.error("ERROR");
        logger.fatal("FATAL");
        return ResponseEntity.ok(emailService.findAll(pageable));
    }

    @GetMapping("/{emailId}")
    public ResponseEntity<EmailModel> getOneEmail(@PathVariable(value="emailId") UUID emailId){
        return emailService.findById(emailId).map(emailModel ->
                ResponseEntity.status(HttpStatus.OK).body(emailModel)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
