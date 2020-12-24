package br.com.jamade.costumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class CostumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CostumerApplication.class, args);
    }

}
