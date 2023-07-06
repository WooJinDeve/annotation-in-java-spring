package com.example.cacheable;


import com.example.support.CountComponent;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CacheTestRepository {

    private static final List<String> nameStore = new LinkedList<>();
    private final CountComponent countComponent;

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
        countComponent.increaseCount();
        return nameStore;
    }

    public Optional<String> findByName(final String name) {
        log.info("-------- CacheTestRepository.findByName ------- ");
        countComponent.increaseCount();
        return nameStore.stream()
                .filter(name::equals)
                .findFirst();
    }
}

