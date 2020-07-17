package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.InvUpdateDetail;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvUpdate.class)
public class InvUpdate_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvUpdate, Date> date;
    public static volatile SingularAttribute<InvUpdate, Branch> branchId;
    public static volatile SingularAttribute<InvUpdate, Integer> postFlag;
    public static volatile SingularAttribute<InvUpdate, Integer> serial;
    public static volatile SingularAttribute<InvUpdate, String> document;
    public static volatile SingularAttribute<InvUpdate, InvInventory> inventoryId;
    public static volatile CollectionAttribute<InvUpdate, InvUpdateDetail> invUpdateDetailCollection;
    public static volatile SingularAttribute<InvUpdate, String> remarks;

}