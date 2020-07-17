package com.toby.entity;

import com.toby.entity.Branch;
import com.toby.entity.InvInventory;
import com.toby.entity.InvTransfer;
import com.toby.entity.InvTransferDetail;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(InvTransfer.class)
public class InvTransfer_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvTransfer, Date> date;
    public static volatile SingularAttribute<InvTransfer, Branch> branchId;
    public static volatile SingularAttribute<InvTransfer, InvTransfer> parent;
    public static volatile CollectionAttribute<InvTransfer, InvTransferDetail> invTransferDetailCollection;
    public static volatile SingularAttribute<InvTransfer, Date> transferDate;
    public static volatile SingularAttribute<InvTransfer, Integer> serialNo;
    public static volatile CollectionAttribute<InvTransfer, InvTransfer> invTransferCollection;
    public static volatile SingularAttribute<InvTransfer, Integer> postFlag;
    public static volatile SingularAttribute<InvTransfer, InvInventory> invTo;
    public static volatile SingularAttribute<InvTransfer, InvInventory> invFrom;
    public static volatile SingularAttribute<InvTransfer, Integer> transferType;
    public static volatile SingularAttribute<InvTransfer, String> remarks;
    public static volatile SingularAttribute<InvTransfer, Integer> status;

}