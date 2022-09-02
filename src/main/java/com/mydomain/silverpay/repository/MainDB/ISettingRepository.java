package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.Setting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISettingRepository extends CrudRepository<Setting,Integer> {
}
