package com.rysh.module.clientShoppingAddress.mapper;

import com.rysh.module.clientShoppingAddress.beans.ShoppingAddress;

import java.util.List;

public interface ShoppingAddressMapper {
    void addAddress(ShoppingAddress shoppingAddress);

    List<ShoppingAddress> findAllShoppingAddress(String userId);

    ShoppingAddress findShoppingAddressById(String shoppingAddressId);

    void deleteShoppingAddress(String shoppingAddressId);

    void updateShoppingAddress(ShoppingAddress shoppingAddress);

    void updateDefaultShoppingAddressByUserId(String userId);

    void updateDefaultShoppingAddressById(String id);
}
