package ru.itmo.titan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCalculationDto {

// non ordered    <№ итерации>, <номер функции>, <результат функции>, <время расчета функции>
    public AnswerCalculationDto(long iter, int numberOfFunc, JsFunctionResult jsFunctionResult){
        this.answer = "<"+ iter + ">," + "<"+ numberOfFunc + ">," + "<"+ jsFunctionResult.getResult() + ">," + "<"+ jsFunctionResult.getCalcTime() + ">";
        this.msg = jsFunctionResult.getMsg();
    }

//    <№ итерации>, <результат функции 1>, <время расчета функции 1>, <кол-во полученных наперед
//результатов функции 1 (еще не выданных, в связи с медленным расчетом функции 2)>, <результат
//функции 2>, <время расчета функции 2>, <кол-во полученных наперед результатов функции 2 (еще
//не выданных, в связи с медленным расчетом функции 1)>

    public AnswerCalculationDto(JsFunctionResult result1, JsFunctionResult result2, long ahead1, long ahead2){
        this.answer = "<" + result1.getIterNumber() + ">," + "<" + result1.getResult() + ">," + "<" + result1.getCalcTime() + ">," + "<" + ahead2 + ">,      "
                + "<" + result2.getIterNumber() + ">," + "<" + result2.getResult() + ">," + "<" + result2.getCalcTime() + ">," + "<" + ahead2 + ">";
        this.msg = result1.getMsg() + "     " + result2.getMsg();

    }

    private String answer;

    private String msg;
}
