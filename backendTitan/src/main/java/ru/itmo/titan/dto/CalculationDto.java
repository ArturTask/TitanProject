package ru.itmo.titan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalculationDto {

    private String func1;

    private String func2;

    private String quantity;

    private Boolean mode;
}
