package com.mydomain.silverpay.data.dto.common;


import com.mydomain.silverpay.data.dto.site.panel.PaginationDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FactorPaginationDto extends PaginationDto {

    private int status;

    private int factorType;

    private int minPrice;

    private int maxPrice;

    private int minDate;

    private String maxDate;

    private String bank;


}
