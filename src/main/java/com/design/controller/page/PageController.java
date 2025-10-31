package com.design.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/customer")
    public String customer(Model model) {
        model.addAttribute("activeMenu", "customer");
        return "customer";
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

    @GetMapping("/quotation")
    public String quote(Model model) {
        model.addAttribute("activeMenu", "quotation");
        return "quotation";
    }

    @GetMapping("/quotation/create")
    public String createQuotation(Model model) {
        model.addAttribute("activeMenu", "quotation");
        return "quotation_create";
    }

    @GetMapping("/quotation/edit/{uuid}")
    public String editQuotation(@PathVariable String uuid, Model model) {
        model.addAttribute("activeMenu", "quotation");
        model.addAttribute("quotationUuid", uuid);
        return "quotation_edit";
    }

    @GetMapping("/quotation/view/{uuid}")
    public String viewQuotation(@PathVariable String uuid, Model model) {
        model.addAttribute("activeMenu", "quotation");
        model.addAttribute("quotationUuid", uuid);
        return "quotation_view";
    }

    @GetMapping("/shipment")
    public String shipment(Model model) {
        model.addAttribute("activeMenu", "shipment");
        return "shipment";
    }

    @GetMapping("/file")
    public String file(Model model) {
        model.addAttribute("activeMenu", "file");
        return "file";
    }

}
