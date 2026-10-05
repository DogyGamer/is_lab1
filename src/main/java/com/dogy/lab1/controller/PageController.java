package com.dogy.lab1.controller;

import com.dogy.lab1.model.FuelType;
import com.dogy.lab1.model.VehicleType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PageController {

    @ModelAttribute("vehicleTypes")
    public VehicleType[] vehicleTypes() {
        return VehicleType.values();
    }

    @ModelAttribute("fuelTypes")
    public FuelType[] fuelTypes() {
        return FuelType.values();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @GetMapping("/view")
    public String view() {
        return "view";
    }

    @GetMapping("/special")
    public String special() {
        return "special";
    }

    @GetMapping("/coordinates")
    public String coordinates() {
        return "coordinates";
    }
}
