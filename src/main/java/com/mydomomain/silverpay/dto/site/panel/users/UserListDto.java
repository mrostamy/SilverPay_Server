package com.mydomomain.silverpay.dto.site.panel.users;

import com.mydomomain.silverpay.dto.site.panel.bank_card.BankCardUserDetailDto;
import com.mydomomain.silverpay.dto.site.panel.photo.PhotoUserDetailDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListDto {

    private String name;

    private String username;

    private String phoneNumber;

    private List<PhotoUserDetailDto> photos;

    private List<BankCardUserDetailDto> bankCards;




}
