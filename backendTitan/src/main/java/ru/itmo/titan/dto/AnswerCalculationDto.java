package ru.itmo.titan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCalculationDto {

//    <№ итерации>, <номер функции>, <результат функции>, <время расчета функции>
    public AnswerCalculationDto(long iter, int numberOfFunc, JsFunctionResult jsFunctionResult){
        this.answer = "<"+ iter + ">," + "<"+ numberOfFunc + ">," + "<"+ jsFunctionResult.getResult() + ">," + "<"+ jsFunctionResult.getCalcTime() + ">";
        this.msg = jsFunctionResult.getMsg();
    }

    private String answer;

    private String msg;
}
