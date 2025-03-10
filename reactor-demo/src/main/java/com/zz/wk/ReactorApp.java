package com.zz.wk;

import com.zz.wk.net.MainReactor;

public class ReactorApp {
    public static void main(String[] args) {
        new Thread(new MainReactor(7778),"main_reactor").start();
    }
}