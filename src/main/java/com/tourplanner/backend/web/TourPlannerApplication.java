package com.tourplanner.backend.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.tourplanner.backend.dal.repository"})
public class TourPlannerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TourPlannerApplication.class, args);
    }
}
