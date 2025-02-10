package com.example;  

import org.junit.jupiter.api.Test;  
import static org.junit.jupiter.api.Assertions.*;  

public class AppTest {  

    @Test  
    public void testApp() {  
        // Simple test case to verify the main method  
        assertDoesNotThrow(() -> App.main(new String[]{}));  
    }  
}
