package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.InvStripDetail;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvStrip.class)
public class InvStrip_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvStrip, Branch> branchId;
    public static volatile SingularAttribute<InvStrip, Date> stripDate;
    public static volatile SingularAttribute<InvStrip, Integer> serial;
    public static volatile SingularAttribute<InvStrip, InvInventory> inventoryId;
    public static volatile CollectionAttribute<InvStrip, InvStripDetail> invStripDetailCollection;
    public static volatile SingularAttribute<InvStrip, String> stripDocument;
    public static volatile SingularAttribute<InvStrip, Integer> type;
    public static volatile SingularAttribute<InvStrip, String> remarks;

}