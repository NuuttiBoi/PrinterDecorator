package org.example;

public class PrinterDecorator implements Printer{
    private Printer printer;
    public PrinterDecorator(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void print(String message) throws Exception {
        printer.print(message);
    }
}
