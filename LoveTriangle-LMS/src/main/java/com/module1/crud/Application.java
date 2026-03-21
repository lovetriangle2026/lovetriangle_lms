package com.module1.crud;

import com.module1.crud.main.SystemRouter;

public class Application {
    public static void main(String[] args) {

        AppConfig.createSystemRouter().start();
    }
}