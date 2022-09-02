package com.mydomain.silverpay.data.common;

import com.mydomain.silverpay.data.model.financialDB.accountant.Entry;
import com.mydomain.silverpay.data.model.financialDB.accountant.Factor;
import com.mydomain.silverpay.data.model.mainDB.Ticket;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UserDashboardDto {

    private int ClosedTicketCount;
    private int unClosedTicketCount;

    private List<Ticket> lastFiveTickets;


    private int totalInventory;

    private int totalEnterMoney;

    private int totalExitMoney;


    private DaysReturnDto inventory5Days;
    private DaysReturnDto enterMoney5Days;
    private DaysReturnDto exitMoney5Days;


    private int totalSuccessFactor;


//    private DaysReturnDto factors10Days;


    private List<Factor> lastTenFactors;

    private int totalSuccessEntry;

    private DaysReturnDto factors8Month;


    private List<Entry> lastTenEntries;


    private int totalFactorIncome;

    private int totalEasyPayIncome;

    private int totalSupportIncome;

    private int totalIncInventoryIncome;
















}
