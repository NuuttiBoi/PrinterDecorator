package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FilePrinter extends PrinterDecorator{
    private PrintWriter writer;

    public FilePrinter(Printer printer) throws IOException {
        super(printer);
        writer = new PrintWriter(new FileWriter("test.txt"),true);
    }

    @Override
    public void print(String message) throws IOException {
        writer.println(message);
        System.out.println(message);
    }
}
