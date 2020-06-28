package com.rysh.module.clientHome.beans;

import com.rysh.module.farm.beans.FarmImg;
import com.rysh.module.farm.beans.FarmItem;
import com.rysh.module.farm.beans.FarmSpec;
import lombok.Data;

@Data
public class FarmItemAndSpecAndImg {
    private FarmItem farmItem;
    private FarmImg farmImg;
    private FarmSpec farmSpec;
}
