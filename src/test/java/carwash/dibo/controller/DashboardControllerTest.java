package carwash.dibo.controller;

import carwash.dibo.model.WorkingDay;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

class DashboardControllerTest {

    interface WorkingDayRepository extends JpaRepository<WorkingDay, Long> {
        @Cacheable("workingDayList")
        Object findTop7ByOrderByIdDesc();
    }

    @Configuration
    @EnableCaching
    static class Config {

        // Simulating your caching configuration
        @Bean
        CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("workingDaysList");
        }

        // A repository mock instead of the real proxy
        @Bean
        WorkingDayRepository myRepo() {
            return Mockito.mock(WorkingDayRepository.class);
        }
    }

    @Autowired
    CacheManager cacheManager;
    @Autowired
    WorkingDayRepository workingDayRepository;

    @Test
    void getWorkingDays() {
        Object first = new Object();
        Object second = new Object();

        Mockito.when(workingDayRepository.findTop7ByOrderByIdDesc()).thenReturn(first,second);
    }
}