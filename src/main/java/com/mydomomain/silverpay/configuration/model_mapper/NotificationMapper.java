package com.mydomomain.silverpay.configuration.model_mapper;

import com.mydomomain.silverpay.dto.site.panel.notification.NotificationUpdateDto;
import com.mydomomain.silverpay.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper {

    NotificationMapper instance= Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Notification notificationUpdateMapper(NotificationUpdateDto updateDto);

}
