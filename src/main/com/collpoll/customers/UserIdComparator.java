package com.collpoll.customers;

import java.util.Comparator;

/**
 * Created by shashikant on १७-०५-२०१७.
 */
public class UserIdComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        ShortListedCustomer s1 = (ShortListedCustomer) o1;
        ShortListedCustomer s2 = (ShortListedCustomer) o2;
        return s1.userId.compareTo(s2.userId);
    }
}
