package com.toby.entity;

import com.toby.entity.Symbol;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-07-06T15:17:55")
@StaticMetamodel(GeneralSymbol.class)
public class GeneralSymbol_ extends BaseEntity_ {

    public static volatile SingularAttribute<GeneralSymbol, Integer> serial;
    public static volatile SetAttribute<GeneralSymbol, Symbol> symbolSet;
    public static volatile SingularAttribute<GeneralSymbol, String> name;

}