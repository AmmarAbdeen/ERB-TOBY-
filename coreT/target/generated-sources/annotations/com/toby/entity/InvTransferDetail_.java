package com.toby.entity;

import com.toby.entity.InvItem;
import com.toby.entity.InvTransfer;
import com.toby.entity.InvTransferDetail;
import com.toby.entity.Symbol;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(InvTransferDetail.class)
public class InvTransferDetail_ extends BaseEntity_ {

    public static volatile SingularAttribute<InvTransferDetail, BigDecimal> amount;
    public static volatile SingularAttribute<InvTransferDetail, Integer> transferFrom;
    public static volatile SingularAttribute<InvTransferDetail, InvTransferDetail> selectedId;
    public static volatile SingularAttribute<InvTransferDetail, String> itemBarcode;
    public static volatile SingularAttribute<InvTransferDetail, InvItem> invItemId;
    public static volatile SingularAttribute<InvTransferDetail, BigDecimal> finalQuantity;
    public static volatile CollectionAttribute<InvTransferDetail, InvTransferDetail> invTransferDetailCollection;
    public static volatile SingularAttribute<InvTransferDetail, InvTransfer> invTransferId;
    public static volatile SingularAttribute<InvTransferDetail, BigDecimal> screwing;
    public static volatile SingularAttribute<InvTransferDetail, BigDecimal> balance;
    public static volatile SingularAttribute<InvTransferDetail, Integer> postFlag;
    public static volatile SingularAttribute<InvTransferDetail, Symbol> unitId;
    public static volatile SingularAttribute<InvTransferDetail, Integer> status;

}