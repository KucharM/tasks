package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.shceduler.EmailScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private EmailScheduler emailScheduler;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("Manage tasks");
        functionality.add("Connection with Trello");
        functionality.add("Sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://kucharm.github.io");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("prev_msg", "New Card added");
        context.setVariable("goodbye_msg", adminConfig.getAdminName());
        context.setVariable("company_details", adminConfig.getCompanyName() + ", " + adminConfig.getCompanyMail() + ", "
                + adminConfig.getCompanyPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("adminConfig", adminConfig);
        context.setVariable("app_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String informationalMail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("admin_name", adminConfig);
        context.setVariable("tasks_url", "http://kucharm.github.io");
        context.setVariable("schedulerMsg", emailScheduler.sendMessage());
        context.setVariable("company_details", adminConfig.getCompanyName() + ", " + adminConfig.getCompanyMail() + ", "
                + adminConfig.getCompanyPhone());

        return templateEngine.process("mail/info-mail", context);
    }
}
