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
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CalculationService {

    public CalculationService() {
        interval = Duration.ofNanos(1l);
    }

    private static Duration interval;

    public Flux<AnswerCalculationDto> calculate(CalculationDto calculationDto){
        String checkMsg = checkCalculationDto(calculationDto);
        if(checkMsg.equals("Success")){//valid
            Long quantity = Long.parseLong(calculationDto.getQuantity());
            if(!calculationDto.getMode()) { //ordered = false
                Flux<AnswerCalculationDto> firstFunc = Flux.interval(interval)//func 1
                        .onBackpressureDrop()
                        .takeWhile((i) -> i < quantity)
                        .map((i) -> new AnswerCalculationDto(i, 1, translateToJava(calculationDto.getFunc1(), i)));
                Flux<AnswerCalculationDto> secondFunc = Flux.interval(interval) //func 2
                        .onBackpressureDrop()
                        .takeWhile((i) -> i < quantity)
                        .map((i) -> new AnswerCalculationDto(i, 2, translateToJava(calculationDto.getFunc2(), i)));
                return Flux.merge(firstFunc, secondFunc);
            }
            else {
                AtomicInteger func1Total = new AtomicInteger();
                AtomicInteger func2Total = new AtomicInteger();
                Flux<JsFunctionResult> firstCode = Flux.interval(interval)
                        .takeWhile((i) -> i < quantity)
                        .doOnNext((i) -> func1Total.getAndIncrement())
                        .map((i) -> translateToJava(calculationDto.getFunc1(), i));
                Flux<JsFunctionResult> secondCode = Flux.interval(interval)
                        .takeWhile((i) -> i < quantity)
                        .doOnNext((i) -> func1Total.getAndIncrement())
                        .map((i) -> translateToJava(calculationDto.getFunc2(), i));
                return firstCode.zipWith(secondCode, ((jsFunctionResult, jsFunctionResult2) -> new AnswerCalculationDto(jsFunctionResult,jsFunctionResult2,func1Total.get()-jsFunctionResult2.getIterNumber()-1, func2Total.get())));
            }
        }
        else {//invalid data from client
            return Flux.just(new AnswerCalculationDto("",checkMsg));
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
            return new JsFunctionResult(result,end-start,"Success",i);
        } catch (ScriptException | NoSuchMethodException ex) {
            return new JsFunctionResult(null,0L,ex.getMessage(),i);
        }
    }

    //validators
    private String checkCalculationDto(CalculationDto calculationDto){
        try {
            //quantity and mode check
            int quantity = Integer.parseInt(calculationDto.getQuantity());
            if(quantity<=0){
                return "Invalid quantity";
            }
            if(calculationDto.getMode()==null){
                return "Invalid mode";
            }

            // func check
            if(translateToJava(calculationDto.getFunc1(),0).getResult()==null || translateToJava(calculationDto.getFunc2(),0).getResult()==null){
                return "Invalid func1 or func2";
            }
        }
        catch (NumberFormatException e){
            return "Unparsable quantity";
        }
        return "Success";
    }





}
