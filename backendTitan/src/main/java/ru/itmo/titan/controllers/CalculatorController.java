package ru.itmo.titan.controllers;

import org.springframework.web.bind.annotation.*;
import ru.itmo.titan.dto.CalculationDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("calculator")
@CrossOrigin(origins = "http://localhost:3000")
public class CalculatorController {

    @PostMapping("/calculate")
    public List<String> calculate(@RequestBody CalculationDto calculationDto){
        System.out.println(calculationDto.getMode());
        return new ArrayList<>();
    }
}
