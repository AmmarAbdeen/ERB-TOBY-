package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvOpenningBalanceItem.class)
public class InvOpenningBalanceItem_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvOpenningBalanceItem, Date> date;
    public static volatile SingularAttribute<InvOpenningBalanceItem, Branch> branchId;
    public static volatile SingularAttribute<InvOpenningBalanceItem, Integer> serial;
    public static volatile SingularAttribute<InvOpenningBalanceItem, String> documentStrip;
    public static volatile SingularAttribute<InvOpenningBalanceItem, InvInventory> invInventoryId;
    public static volatile SingularAttribute<InvOpenningBalanceItem, String> remark;

}