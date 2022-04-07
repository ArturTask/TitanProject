package ru.itmo.titan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.itmo.titan.dto.AnswerCalculationDto;
import ru.itmo.titan.dto.CalculationDto;
import ru.itmo.titan.services.CalculationService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@RequestMapping("calculator")
@CrossOrigin(origins = "http://localhost:3000")
public class CalculatorController {

    @Autowired
    private CalculationService calculationService;

    @PostMapping("/calculate")
    public Flux<AnswerCalculationDto> calculate(@RequestBody CalculationDto calculationDto) {
//        System.out.println("da");
        return calculationService.calculate(calculationDto);
    }


//    @GetMapping("/hello")
//    public Mono<AnswerCalculationDto> hello() {
//        Mono.defer(() -> {
//            return Mono.just(new AnswerCalculationDto());
//        })
//                .delaySubscription(Duration.ofSeconds(10)).subscribe();
//        return Mono.empty();
//    }

    @GetMapping(value = "/3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> get3() {
        System.out.println("get3 start");
        Flux<String> result = Flux.fromStream(IntStream.range(1, 10).mapToObj(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux data--" + i;
        }));
        System.out.println("get3 end");
        return result;

    }

}