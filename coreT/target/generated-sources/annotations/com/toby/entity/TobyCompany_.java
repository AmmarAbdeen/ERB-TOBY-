package com.toby.entity;

import com.toby.entity.TobyUser;
import com.toby.entity.TobyUserYear;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:56")
@StaticMetamodel(TobyCompany.class)
public class TobyCompany_ extends BaseEntity_ {

    public static volatile SingularAttribute<TobyCompany, String> image;
    public static volatile SingularAttribute<TobyCompany, String> code;
    public static volatile SingularAttribute<TobyCompany, String> address;
    public static volatile ListAttribute<TobyCompany, TobyUser> tobyUserList;
    public static volatile SingularAttribute<TobyCompany, String> nameEn;
    public static volatile SingularAttribute<TobyCompany, String> core_business;
    public static volatile SingularAttribute<TobyCompany, String> phone;
    public static volatile SingularAttribute<TobyCompany, String> responsible;
    public static volatile SingularAttribute<TobyCompany, String> name;
    public static volatile SingularAttribute<TobyCompany, byte[]> logo;
    public static volatile SingularAttribute<TobyCompany, Integer> rowCountList;
    public static volatile CollectionAttribute<TobyCompany, TobyUserYear> tobyUserYearCollection;
    public static volatile SingularAttribute<TobyCompany, String> fax;
    public static volatile SingularAttribute<TobyCompany, Integer> rowCountMasterDetails;
    public static volatile SingularAttribute<TobyCompany, String> addressEn;

}