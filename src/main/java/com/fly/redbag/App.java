package com.fly.redbag;

import java.util.List;

public class App {
    public static void main(String[] args) {
        RedBag redBag = new RedBag(11, 10, 0.01);
        List<Double> list = redBag.execute();
        redBag.show();
    }
}
