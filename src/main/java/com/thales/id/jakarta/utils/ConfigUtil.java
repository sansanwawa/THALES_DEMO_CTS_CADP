package com.thales.id.jakarta.utils;

import com.thales.id.jakarta.dao.repository.ThalesConfigRepository;
import com.thales.id.jakarta.entities.ThalesConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ConfigUtil {


    public static String getConfigValue(ThalesConfigRepository configDao, String configName){
        Optional<ThalesConfig> conf = configDao.getConfigValue(configName);
        ThalesConfig cfg = conf.get();
        return cfg.getConfigValue();
    }

}
