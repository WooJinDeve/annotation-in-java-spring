package com.example.support;

import org.springframework.stereotype.Component;

@Component
public class CountComponent {
    private volatile static Integer count = 0;

    public void increaseCount(){
        count++;
    }

    public void initializeCount(){
        count = 0;
    }

    public Integer getCount() {
        return count;
    }
}
