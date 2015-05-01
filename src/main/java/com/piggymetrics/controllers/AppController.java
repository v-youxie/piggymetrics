package com.piggymetrics.controllers;

import com.piggymetrics.domain.User;
import com.piggymetrics.helpers.JsonMapper;
import com.piggymetrics.service.UserService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private JsonMapper mapper;

    static final Logger logger = Logger.getLogger(AppController.class);

	@RequestMapping("/")
	public String launchApp(ModelMap model, Principal principal, HttpServletRequest request) {

        try {
            User current = userService.getUser(principal.getName(), request);
            model.addAttribute("user", mapper.writeValueAsString(current));
            model.addAttribute("authorized", current.isAuthorized());
        } catch (NullPointerException e) {
            model.addAttribute("authorized", false);
        } catch (Exception e) {
            logger.error("App Conroller", e);
        }

        return "app/base";
    }

	@RequestMapping("/demo")
	public String launchDemoApp(ModelMap model, HttpServletRequest request) {

        try {
            User demo = userService.getDemoUser(request);
            model.addAttribute("user", mapper.writeValueAsString(demo));
            model.addAttribute("authorized", true);
            model.addAttribute("demo", true);
        } catch (Exception e) {
            logger.error("App Demo Conroller", e);
        }

        return "app/base";
    }

}