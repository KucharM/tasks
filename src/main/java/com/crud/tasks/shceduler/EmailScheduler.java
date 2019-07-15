package com.crud.tasks.shceduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    @Autowired
    private SimpleEmailService simpleEmailService;
    @Autowired
    private TaskRepository repository;
    @Autowired
    private AdminConfig config;

    private static final String SUBJECT = "Tasks: Once a day mail";

//    @Scheduled(cron = "0 0 10 * * *")
//    @Scheduled(fixedDelay = 10000)
//    public void sendInformationEmail() {
//        long size = repository.count();
//        String tasks = "tasks";
//
//        if (size == 1)
//            tasks = "task";
//
//        simpleEmailService.send(new Mail(
//                config.getAdminMail(),
//                SUBJECT,
//                "Currently in database you got: " + size + " " + tasks
//        ));
//    }

}
