package com.example.cacheable;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CacheTestRepository {

    private static final List<String> nameStore = new LinkedList<>();
    private static Integer count = 0;

    static {
        nameStore.addAll(
                List.of(
                        "jason",
                        "wendy",
                        "yuri",
                        "dope",
                        "jayz",
                        "zeze"
                )
        );
    }

    public List<String> findAll() {
        log.info("-------- CacheTestRepository.findAll ------- ");
        increaseCount();
        return nameStore;
    }

    public Optional<String> findByName(final String name) {
        log.info("-------- CacheTestRepository.findByName ------- ");
        increaseCount();
        return nameStore.stream()
                .filter(name::equals)
                .findFirst();
    }

    public Integer getCount() {
        return count;
    }

    private void increaseCount() {
        count++;
    }

    public void initializeCount(){
        count = 0;
    }
}

