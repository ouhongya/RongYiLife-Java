package com.rysh.module.serverOrders.beans;

import lombok.Data;

import java.util.List;

@Data
public class SuperHero {
    private List<ShopAddressResult> shopAddressResults;
    private List<String> couriers;
}
