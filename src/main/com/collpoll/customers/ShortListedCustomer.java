package com.collpoll.customers;

/**
 * Created by shashikant on १७-०५-२०१७.
 */
public class ShortListedCustomer {
    String name;
    String userId;

    ShortListedCustomer(String name, String userId){
    this.name = name;
    this.userId = userId;
}

    @Override
    public String toString() {
        return name + "\t\t\t|\t" + userId;
    }
}

