/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.converter;




import com.toby.dto.TobyUserDTO;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author H
 */
public class TobyUserDTOConverter implements Converter{



    private List<TobyUserDTO> tobyUserDTOList;

    public TobyUserDTOConverter(List<TobyUserDTO> tobyUserDTOList) {
        this.tobyUserDTOList = tobyUserDTOList;
    }


    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (TobyUserDTO item : tobyUserDTOList) {
            try {
                Long.parseLong(value);
            } catch (Exception e) {
                return null;
            }
            if (!value.equals("") && !value.equals(" ") && item.getId().longValue() == Long.parseLong(value)) {
                return item;
            }
        }
        return null;
    }
    
     @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {

            if (value != null && !value.equals("") && !value.equals(" ")) {
                if (((TobyUserDTO) value).getId()!= null && ((TobyUserDTO) value).getId().longValue() != 0) {
                    TobyUserDTO codegenerator = (TobyUserDTO) value;
                    return codegenerator.getId().longValue() +"" ;
                } else {
                    return "";
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}



    
