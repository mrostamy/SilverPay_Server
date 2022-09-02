package com.mydomain.silverpay.data.common;

import com.mydomain.silverpay.data.dto.site.panel.blog.BlogReturnDto;
import com.mydomain.silverpay.data.dto.site.panel.upload.users.UserBlogInfoDto;
import com.mydomain.silverpay.data.model.mainDB.Ticket;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminDashboardDto {

    private int ClosedTicketCount;
    private int unClosedTicketCount;

    private List<Ticket> lastFiveTickets;

    private int totalInventory;

    private DaysReturnDto inventory5Days;

    private DaysReturnDto enterMoney5Days;

    private int totalEnterMoney;

    private int totalExitMoney;

    private DaysReturnDto exitMoney5Days;


    private int totalBlogCount;
    private int approvedBlogCount;
    private int unApprovedBlogCount;

    private DaysReturnDto totalBlog5Days;
    private DaysReturnDto unApprovedBlog5Days;
    private DaysReturnDto approvedBlog5Days;

    private List<BlogReturnDto> last7Blogs;

    private List<UserBlogInfoDto> last10UsersBlogInfo;


}
