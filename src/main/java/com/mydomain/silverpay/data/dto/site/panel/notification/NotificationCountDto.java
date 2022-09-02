package com.mydomain.silverpay.data.dto.site.panel.notification;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationCountDto
{
    private int unVerifiedBlogCount=0 ;
    private int unClosedTicketCount=0 ;
    private int unCheckedEntry=0 ;
    private int unSpecifiedEntry=0 ;
    private int unVerifiedGateInPastSevenDays=0 ;
    private int unVerifiedBankCardsInPastSevenDays=0 ;
    private int unVerifiedDocumentsCount=0 ;

}
