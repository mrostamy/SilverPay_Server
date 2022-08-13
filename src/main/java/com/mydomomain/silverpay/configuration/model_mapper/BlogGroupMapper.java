package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.dto.site.panel.bank_card.BankCardUserDetailDto;
import com.mydomomain.silverpay.dto.site.panel.blogGroup.BlogGroupCreateUpdateDto;
import com.mydomomain.silverpay.dto.site.panel.blogGroup.BlogGroupReturnDto;
import com.mydomomain.silverpay.dto.site.panel.users.BankCardReturnDto;
import com.mydomomain.silverpay.dto.site.panel.users.BankCardUpdateDto;
import com.mydomomain.silverpay.model.BankCard;
import com.mydomomain.silverpay.model.blog.BlogGroup;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BlogGroupMapper {

    BlogGroupMapper instance = Mappers.getMapper(BlogGroupMapper.class);

    BlogGroupReturnDto blogGroupToReturnDtoMapper(BlogGroup blogGroup);

    BlogGroup blogGroupMapper(BlogGroupCreateUpdateDto blogGroupReturnDto );

    List<BlogGroupReturnDto> BlogGroupMapper(List<BlogGroup> BlogGroup);

}
