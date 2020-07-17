/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.converter;

import com.toby.dto.InvPurchaseInvoiceDTO1;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author m_els
 */
public class InvPurchaseInvoiceDTOConverter implements Converter {

    private List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList;

    public InvPurchaseInvoiceDTOConverter(List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList) {
        this.invPurchaseInvoiceDTOList = invPurchaseInvoiceDTOList;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for (InvPurchaseInvoiceDTO1 purchaseInvoice : invPurchaseInvoiceDTOList) {
            try {
                Long.parseLong(value);
            } catch (Exception e) {
                return null;
            }
            if (!value.equals("") && !value.equals(" ") && purchaseInvoice.getId().longValue() == Long.parseLong(value)) {
                return purchaseInvoice;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {

            if (value != null && !value.equals("") && !value.equals(" ")) {
                if (((InvPurchaseInvoiceDTO1) value).getId() != null && ((InvPurchaseInvoiceDTO1) value).getId().longValue() != 0) {
                    InvPurchaseInvoiceDTO1 codegenerator = (InvPurchaseInvoiceDTO1) value;
                    return codegenerator.getId().longValue() + "";
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
