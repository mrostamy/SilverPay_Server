package com.mydomain.silverpay.data.dto.site.panel.upload.users;

import com.mydomain.silverpay.data.dto.site.panel.bank_card.BankCardUserDetailDto;
import com.mydomain.silverpay.data.dto.site.panel.photo.PhotoUserDetailDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListDto {

    private String id;

    private String name;

    private String username;

    private String phoneNumber;

    private List<PhotoUserDetailDto> photos;

    private List<BankCardUserDetailDto> bankCards;




}
