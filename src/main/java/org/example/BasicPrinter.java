package org.example;

import java.io.IOException;

public class BasicPrinter implements Printer{
    @Override
    public void print(String message) throws Exception {
        System.out.println(message);
    }
}
