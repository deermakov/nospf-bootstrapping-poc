package ru.nospf.fw.dbconfig;

import io.jsondb.JsonDBTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * todo Document type JsonDbConfig
 */
@Configuration
public class JsonDbConfig {

    //Actual location on disk for database files, process should have read-write permissions to this folder
    @Value("${jsondb.dbFilesLocation}")
    private String dbFilesLocation;

    @Bean
    public JsonDBTemplate jsonDBTemplate() throws FileNotFoundException  {
        //Java package name where POJO's are present
        String baseScanPackage = "ru.nospf.domain";

        JsonDBTemplate jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, baseScanPackage);

        return jsonDBTemplate;
    }
}
