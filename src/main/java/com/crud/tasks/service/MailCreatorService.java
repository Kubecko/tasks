package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.repository.TaskRepository;
import org.thymeleaf.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.ArrayList;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    @Qualifier("templateEngine")
    public TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        ArrayList<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");
        functionality.add("Informs once a day about the number of your tasks in the database");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url","http://localhost:8888/tasks_frontend/" );
        context.setVariable("email_adress", "MKodersik@gmail.com");
        context.setVariable("button","Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("show_button", false);
        context.setVariable("is_friend" , false);
        context.setVariable("admin_config" , adminConfig);
        context.setVariable("application_functionality", functionality );
        return templateEngine.process("mail/created-trello-card-mail",context );
    }
}
