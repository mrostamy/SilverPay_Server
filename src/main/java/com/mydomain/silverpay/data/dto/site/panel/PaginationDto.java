package com.mydomain.silverpay.data.dto.site.panel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {

    private int pageNumber = 0;

    private int pageSize = 10;

    public void setPageSize(int pageSize) {

        int tPageSize = 12;

        this.pageSize = Math.min(pageSize, tPageSize);


    }

    private String filter;

    private String sortDirection;
//
//    private int currentPage;
//
//    private int itemPerPage;
//
//    private int totalPage;
//
//    private int totalItem;

}
