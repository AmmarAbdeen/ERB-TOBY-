package com.toby.entity;

import com.toby.entity.TobyCompany;
import com.toby.entity.TobyUser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(BaseEntity.class)
public class BaseEntity_ { 

    public static volatile SingularAttribute<BaseEntity, TobyCompany> companyId;
    public static volatile SingularAttribute<BaseEntity, Date> modificationDate;
    public static volatile SingularAttribute<BaseEntity, TobyUser> createdBy;
    public static volatile SingularAttribute<BaseEntity, TobyUser> modifiedBy;
    public static volatile SingularAttribute<BaseEntity, Integer> id;
    public static volatile SingularAttribute<BaseEntity, Date> creationDate;

}