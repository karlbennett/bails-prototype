package org.bails.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Karl Bennett
 */

@Controller
public class BailsController {

    @RequestMapping("/index.html")
    public String index() {
        return "/index";
    }
}
