package com.mydomain.silverpay.data.dto.site.panel.accountant;

import com.mydomain.silverpay.data.common.DaysReturnDto;
import com.mydomain.silverpay.data.model.financialDB.accountant.Entry;
import com.mydomain.silverpay.data.model.financialDB.accountant.Factor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountantDashboardDto {

    private int totalEntries;
    private int totalEntryApproved;
    private int totalEntryUnApproved;
    private int totalEntryPayed;
    private int totalEntryUnPayed;
    private int totalEntryReject;
    private int totalEntryUnReject;

    private DaysReturnDto entry5Days;

    private DaysReturnDto factor5Days;


    private int totalFactor;

    private int totalEasyPay;

    private int totalSupport;

    private int totalIncInventory;

    private int totalSuccessFactor;

    private DaysReturnDto last12MonthFactors;
    private DaysReturnDto last12MonthEntries;
//    private DaysReturnDto last12MonthUsers;

    private DaysReturnDto last12MonthBankcards;
    private DaysReturnDto last12MonthGates;
    private DaysReturnDto last12MonthWallets;

    private List<Entry> last7Entries;
    private List<Factor> last7Factors;


}
