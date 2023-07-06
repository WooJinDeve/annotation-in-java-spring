package com.example.cacheable;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheTestService {

    private final CacheTestRepository cacheTestRepository;

    @Cacheable
    public List<String> findAll(){
        log.info("-------- CacheTestService.findAll -------");
        return cacheTestRepository.findAll();
    }

    @Cacheable
    public String findByName(final String name){
        log.info("-------- CacheTestService.findByName -------");
        return cacheTestRepository.findByName(name)
                .orElseThrow(IllegalAccessError::new);
    }
}
