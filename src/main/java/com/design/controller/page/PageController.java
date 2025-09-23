package com.design.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/supplier")
    public String supplier(Model model) {
        model.addAttribute("activeMenu", "supplier");
        return "supplier";
    }

    @GetMapping("/item")
    public String item(Model model) {
        model.addAttribute("activeMenu", "item");
        return "item";
    }

    @GetMapping("/product")
    public String product(Model model) {
        model.addAttribute("activeMenu", "product");
        return "product";
    }

    @GetMapping("/quote")
    public String quote(Model model) {
        model.addAttribute("activeMenu", "quote");
        return "quote";
    }

    @GetMapping("/shipment")
    public String shipment(Model model) {
        model.addAttribute("activeMenu", "shipment");
        return "shipment";
    }

}
