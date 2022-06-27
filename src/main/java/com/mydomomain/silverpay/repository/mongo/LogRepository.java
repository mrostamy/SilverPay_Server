package com.mydomomain.silverpay.repository.mongo;

import com.mydomomain.silverpay.model.mongo.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log,String> {




}
