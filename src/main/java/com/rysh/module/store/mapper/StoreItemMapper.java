package com.rysh.module.store.mapper;

import com.rysh.module.store.beans.StoreItem;

public interface StoreItemMapper {

    StoreItem findStoreItemById(String productItemId);

    StoreItem findStoreItemByIdToOrders(String productItemId);

    StoreItem findStoreItemByIdPlus(String itemId);
}