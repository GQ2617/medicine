package com.zgq.medicine;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@MapperScan("com.zgq.medicine.dao")
public class MedicineApplication {
    public static void main(String[] args) {
        SpringApplication.run(MedicineApplication.class, args);
        log.info("项目启动成功...");
    }


}
