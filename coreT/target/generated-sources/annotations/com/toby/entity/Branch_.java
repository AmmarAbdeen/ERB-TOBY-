package com.toby.entity;

import com.toby.define.CompanyActivityClassEnum;
import com.toby.entity.GeneralBudgetGuide;
import com.toby.entity.GlAccount;
import com.toby.entity.TobyUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:57")
@StaticMetamodel(Branch.class)
public class Branch_ extends BaseEntity_ {

    public static volatile CollectionAttribute<Branch, GlAccount> glAccountCollection;
    public static volatile SingularAttribute<Branch, String> image;
    public static volatile SingularAttribute<Branch, String> nameAr;
    public static volatile SingularAttribute<Branch, String> address3;
    public static volatile SingularAttribute<Branch, String> address2;
    public static volatile SingularAttribute<Branch, String> address1;
    public static volatile SingularAttribute<Branch, String> mobile;
    public static volatile SingularAttribute<Branch, String> nameEn;
    public static volatile SingularAttribute<Branch, String> taxCode;
    public static volatile SingularAttribute<Branch, String> phone;
    public static volatile SingularAttribute<Branch, Integer> serial;
    public static volatile CollectionAttribute<Branch, GeneralBudgetGuide> generalbudgetguideCollection;
    public static volatile SingularAttribute<Branch, CompanyActivityClassEnum> companyActivity;
    public static volatile CollectionAttribute<Branch, TobyUser> tobyUserCollection;
    public static volatile SingularAttribute<Branch, String> fax;
    public static volatile SingularAttribute<Branch, String> iconPath;
    public static volatile SingularAttribute<Branch, String> email;

}