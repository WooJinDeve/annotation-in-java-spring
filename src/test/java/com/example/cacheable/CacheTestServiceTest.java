package com.example.cacheable;


import static org.assertj.core.api.Assertions.*;

import com.example.support.CountComponent;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheTestServiceTest {

    @Autowired
    private CacheTestService cacheTestService;

    @Autowired
    private CountComponent countComponent;

    @AfterEach
    void init(){
        countComponent.initializeCount();
    }

    @Test
    void findAll(){
        //when
        List<String> actual = cacheTestService.findAll();
        List<String> cacheActual1 = cacheTestService.findAll();
        List<String> cacheActual2 = cacheTestService.findAll();

        Integer countActual = countComponent.getCount();

        //then
        assertThat(actual)
                .containsAll(cacheActual1)
                .containsAll(cacheActual2);
        assertThat(countActual).isEqualTo(1);
    }

    @Test
    void findById(){
        //when
        String jasonActual = cacheTestService.findByName("jason");
        String cacheJasonActual = cacheTestService.findByName("jason");

        String zezeActual = cacheTestService.findByName("zeze");
        String zezeJasonActual = cacheTestService.findByName("zeze");

        Integer countActual = countComponent.getCount();

        //then
        assertThat(jasonActual).isEqualTo(cacheJasonActual);
        assertThat(zezeActual).isEqualTo(zezeJasonActual);
        assertThat(countActual).isEqualTo(2);
    }

}