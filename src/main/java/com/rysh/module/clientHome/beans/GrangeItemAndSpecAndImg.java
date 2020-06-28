package com.rysh.module.clientHome.beans;

import com.rysh.module.grange.beans.GrangeImg;
import com.rysh.module.grange.beans.GrangeItem;
import com.rysh.module.grange.beans.GrangeSpec;
import lombok.Data;

@Data
public class GrangeItemAndSpecAndImg {
    private GrangeItem grangeItem;
    private GrangeSpec grangeSpec;
    private GrangeImg grangeImg;
}
