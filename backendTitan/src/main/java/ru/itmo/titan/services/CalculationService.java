package ru.itmo.titan.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.itmo.titan.dto.AnswerCalculationDto;
import ru.itmo.titan.dto.CalculationDto;
import ru.itmo.titan.dto.JsFunctionResult;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class CalculationService {

    public CalculationService() {
        interval = Flux.interval(Duration.ofSeconds(1l));
    }

    private static Flux<Long> interval;

    public Flux<AnswerCalculationDto> calculate(CalculationDto calculationDto){
        if(checkCalculationDto(calculationDto)){//valid
            Long quantity = Long.parseLong(calculationDto.getQuantity());
            Flux<AnswerCalculationDto> firstFunc = Flux.from(interval)//func 1
                    .takeUntil((i)-> i > quantity-2)
                    .map((i)->(new AnswerCalculationDto(i,1,translateToJava(calculationDto.getFunc1(),i))))
                    ;
            Flux<AnswerCalculationDto> secondFunc = Flux.from(interval) //func 2
                    .takeUntil((i)-> i > quantity-2)
                    .map((i)->(new AnswerCalculationDto(i,2,translateToJava(calculationDto.getFunc2(),i))))
                    ;
            return Flux.merge(firstFunc,secondFunc);
        }
        else {//invalid data from client
            return Flux.just(new AnswerCalculationDto("","Invalid input"));
        }

    }

    //for javascript functions
    private JsFunctionResult translateToJava(String code, long i){
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");
        Invocable invocableEngine = (Invocable) jsEngine;
        try {
            Long start = System.nanoTime();
            jsEngine.eval(code);
            String result = String.valueOf(invocableEngine.invokeFunction("func",i));
            Long end = System.nanoTime();
            return new JsFunctionResult(result,end-start,"Success");
        } catch (ScriptException | NoSuchMethodException ex) {
            return new JsFunctionResult(null,0L,ex.getMessage());
        }
    }

    //validators
    private boolean checkCalculationDto(CalculationDto calculationDto){
        try {
            Integer.parseInt(calculationDto.getQuantity());
            if(calculationDto.getMode()==null){
                return false;
            }
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }


}
