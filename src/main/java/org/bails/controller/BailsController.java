package org.bails.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Karl Bennett
 */

@Controller
public class BailsController {

    @ModelAttribute("message")
    public String getMessage() {
        return "This text is coming from the controller right to your page.";
    }

    @RequestMapping("/index.html")
    public String index(@ModelAttribute("message") String message) {
        return "PrototypeOldPage";
    }
}
