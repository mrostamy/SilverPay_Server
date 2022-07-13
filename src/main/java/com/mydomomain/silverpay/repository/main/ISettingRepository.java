package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.Setting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISettingRepository extends CrudRepository<Setting,Integer> {
}
