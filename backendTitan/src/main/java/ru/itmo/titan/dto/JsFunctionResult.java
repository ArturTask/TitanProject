package ru.itmo.titan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsFunctionResult {

    private String result;

    private Long calcTime;

    private String msg;
}
