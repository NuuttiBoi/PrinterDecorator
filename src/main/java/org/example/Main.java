package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private Printer printer;
    public static void main(String[] args) throws Exception {

        FilePrinter filePrinter = new FilePrinter(new BasicPrinter());
        filePrinter.print("695");

        EncryptedPrinter encryptedPrinter = new EncryptedPrinter(new BasicPrinter());
        encryptedPrinter.print("695");

    }
}
