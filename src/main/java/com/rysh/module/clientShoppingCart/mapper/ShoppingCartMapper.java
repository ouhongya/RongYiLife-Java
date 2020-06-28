package com.rysh.module.clientShoppingCart.mapper;

import com.rysh.module.clientShoppingCart.beans.ShoppingCart;

import java.util.List;

public interface ShoppingCartMapper {
    List<ShoppingCart> findShoppingCartByUserId(String userId);

    void updateShoppcart(ShoppingCart shoppingCart);

    void addShoppingCart(ShoppingCart shoppingCart);

    void emptyShoppingCartByUserId(String userId);

    void deleteShoppingCartById(String id);

    ShoppingCart findShoppingCartById(String id);

}
