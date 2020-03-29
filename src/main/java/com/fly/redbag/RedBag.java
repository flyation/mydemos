package com.fly.redbag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 金额格式化方式为自己写的方法，先乘1000再除以10，调用Math的四舍五入方法，再除以100，转换为两位小数
 * 该方式会小概率造成分配后的红包的数额之和与原始红包金额有数个0.01数额的差别，需在以后版本修改
 */
public class RedBag {
    private int peopleNum;              // 红包分配人数(构造器传入)
    private double totalMoney;          // 红包总金额(构造器传入)
    private double balance;             // 红包余额
    private double minMoney;            // 抢到红包的保底金额(构造器传入)
    private List<Double> moneyList;     // 红包金额分配列表

    public RedBag(int peopleNum, double totalMoney, double minMoney) {
        this.peopleNum = peopleNum;
        this.totalMoney = totalMoney;
        this.balance = totalMoney;
        this.minMoney = minMoney;
        this.moneyList = new ArrayList<>();
    }

    // *核心方法*分配红包
    public List<Double> execute() {
        int remainPeopleNum = peopleNum;        // 未抢红包人数
        double grabMoney;                       // 每次抢到的红包金额
        while (remainPeopleNum > 0) {
            grabMoney = grab(remainPeopleNum);  // 本次抢到的红包金额
            moneyList.add(grabMoney);
            remainPeopleNum--;
        }
        return moneyList;
    }

    // 展示并返回格式化后的红包列表(需在核心方法execute()执行后执行)
    public List<Double> show() {
        ArrayList<Double> showList = new ArrayList<>();
        // 将moneyList中的元素逐个格式化到showList
        for (int i = 0, len = moneyList.size(); i < len; i++) {
            showList.add(numRound(moneyList.get(i)));
        }
        showList.forEach(System.out::println);  //打印展示
        return showList;
    }

    // 返回每一次抢到的红包金额
    private double grab(int remainPeopleNum) {
        if (remainPeopleNum == 1) {
            // 若只剩一个人未抢红包，则给其红包剩余的所有余额
            return balance;
        } else {
            // 若剩余未抢红包人数大于1，则要为剩余的每个人保留保底金额
            double maxMoney = balance - remainPeopleNum * minMoney; // 本次能抢到的最大金额
            double money = new Random().nextDouble() * maxMoney;    // 本次抢到的金额
            // 以防随机倍率为0，致使maxMoney为0
            while (money == 0) {
                money = new Random().nextDouble() * maxMoney;
            }
            balance -= money;   //更新余额
            return money;
        }

    }

    // 将红包金额格式化为两位小数点的浮点型
    private double numRound (double number) {
        int a = (int) (number * 1000);
        long b = Math.round(((double)a) / 10);
        double c = ((double)b) / 100;
        return c;
    }
}
