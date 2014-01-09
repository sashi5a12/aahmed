package com.netpace.device.utils;

import java.util.UUID;

public class UniqueStringGenerator {
    
    public static String generateUniqueString(){
        return UUID.randomUUID().toString();
    }
    
}
