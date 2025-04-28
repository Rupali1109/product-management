package com.nowvertical.management.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories("com.nowvertical.management.product.repository")
@EntityScan("com.nowvertical.management.product.entity")
@EnableAspectJAutoProxy(proxyTargetClass = true)

public class ProductApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProductApplication.class, args);

    }

}
