package ru.itmo.titan.services;

import org.springframework.stereotype.Service;
import ru.itmo.titan.dto.AnswerCalculationDto;
import ru.itmo.titan.dto.CalculationDto;

@Service
public class CalculationService {

    public AnswerCalculationDto calculate(CalculationDto calculationDto){
        if(checkCalculationDto(calculationDto)){//valid

        }
        else {//invalid data from client

        }
        return new AnswerCalculationDto();
    }

    //validators
    private boolean checkCalculationDto(CalculationDto calculationDto){
        try {
            Integer.parseInt(calculationDto.getQuantity());
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }


}
