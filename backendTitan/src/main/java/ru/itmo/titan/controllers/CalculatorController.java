package ru.itmo.titan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.itmo.titan.dto.AnswerCalculationDto;
import ru.itmo.titan.dto.CalculationDto;
import ru.itmo.titan.services.CalculationService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("calculator")
@CrossOrigin(origins = "http://localhost:3000")
public class CalculatorController {

    @Autowired
    private CalculationService calculationService;

    @PostMapping("/calculate")
    public  Flux<AnswerCalculationDto> calculate(@RequestBody CalculationDto calculationDto){
//        System.out.println(calculationDto.getMode());
        return calculationService.calculate(calculationDto);
    }
}
