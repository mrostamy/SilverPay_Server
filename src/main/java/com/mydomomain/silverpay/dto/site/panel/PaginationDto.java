package com.mydomomain.silverpay.dto.site.panel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {

    private int currentPage;

    private int itemPerPage;

    private int totalPage;

    private int totalItem;

}
