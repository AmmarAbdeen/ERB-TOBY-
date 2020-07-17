
package com.toby.invPurchaseOrder;

import com.toby.businessservice.InvPurchaseOrderDetailsService;
import com.toby.businessservice.InvPurchaseOrderService;
import com.toby.businessservice.ItemsBarcodesDetailsViewService;
import com.toby.dto.InvPurchaseOrderDTO;
import com.toby.toby.BaseFormBean;
import com.toby.views.ItemsBarcodesDetailsView;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


@Named(value = "invPurchaseOrderListMB")
@ViewScoped
public class invPurchaseOrderListMB extends BaseFormBean implements Serializable {

    private List<InvPurchaseOrderDTO> invPurchaseOrderDTOs;
    private InvPurchaseOrderDTO invPurchaseOrderDTOSelected;
    private InvPurchaseOrderDTO invPurchaseOrderDTO;
    private List<ItemsBarcodesDetailsView> itemsBarcodesDetailsViewList;

    @EJB
    private InvPurchaseOrderService invPurchaseOrderService;
    @EJB
    private InvPurchaseOrderDetailsService invPurchaseOrderDetailsService;
    @EJB
    private ItemsBarcodesDetailsViewService itemsBarcodesDetailsViewService;

    @Override
    @PostConstruct
    public void init() {

    }

    @Override
    public void load() {

    }

    public void delete() {
        try {
            if (invPurchaseOrderDTOSelected != null) {
                try {
                    invPurchaseOrderService.deletePurchaseOrder(invPurchaseOrderDTOSelected, getUserData().getUser());
                    getInvPurchaseOrderDTOs().remove(invPurchaseOrderDTOSelected);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "DELETED"));
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "UNIQE_KEY_ERROR"));
                }
            }
        } catch (Exception e) {
            saveError(e, "invPurchaseOrderListMB", "delete");
        }
    }

    public String goToAdd() {
        try {
            getContext().getSessionMap().put("ScreenMode", "Add");
            exit("../invPurchaseOrder/invPurchaseOrderForm.xhtml");
            return "";
        } catch (Exception e) {
            saveError(e, "invPurchaseOrderListMB", "goToAdd");
            return null;
        }
    }

    public String goToEdit() {
        try {
            if (getInvPurchaseOrderDTOSelected() != null && getInvPurchaseOrderDTOSelected().getId() > 0) {
                getContext().getSessionMap().put("invPurchaseOrderDTOSelected", getInvPurchaseOrderDTOSelected().getId());
                getContext().getSessionMap().put("ScreenMode", "Edit");
                exit("../invPurchaseOrder/invPurchaseOrderForm.xhtml");
                return "";
            } else {
                return "";
            }
        } catch (Exception e) {
            saveError(e, "invPurchaseOrderListMB", "goToEdit");
            return null;
        }
    }

    @Override
    public String getScreenName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void print() {
        try {
//            invPurchaseOrderDTO = invPurchaseOrderService.findPurchaseOrderByPurchaseOrderId(invPurchaseOrderDTOSelected.getId(), getUserData().getUser());
//            List<InvPurchaseOrderDetail> invPurchaseOrderDetailsList = invPurchaseOrderService.getALLPurchaseOrderDetailsByPurchaseOrder(invPurchaseOrderDTOSelected.getId());
//            List<InvPurchaseOrderDetailEntity> invPurchaseOrderDetailEntityList = new ArrayList<>();
//            BigDecimal total = BigDecimal.ZERO, totalQuatity = BigDecimal.ZERO, totalNet = BigDecimal.ZERO, totalNetAfterDiscount = BigDecimal.ZERO;
//            Map<String, ItemsBarcodesDetailsView> itemsBarcodeMap = new HashMap<>();
//            getInvPurchaseOrderParent().fillItemMap(itemsBarcodeMap, itemsBarcodesDetailsViewList, getUserData().getUserBranch().getId());
//            getInvPurchaseOrderParent().mapInvPurchaseOrderToInvPurchaseOrderEntity(invPurchaseOrder,
//                    invPurchaseOrderDetailsList,
//                    invPurchaseOrderEntity,
//                    invPurchaseOrderDetailEntityList,
//                    itemsBarcodeMap, total, totalQuatity, totalNet, totalNetAfterDiscount);
//            getInvPurchaseOrderParent().print(invPurchaseOrderEntity, invPurchaseOrderDetailEntityList, getUserData());
        } catch (Exception e) {
            saveError(e, "invPurchaseOrderListMB", "print");

        }
    }

    public InvPurchaseOrderService getInvPurchaseOrderService() {
        return invPurchaseOrderService;
    }

    public void setInvPurchaseOrderService(InvPurchaseOrderService invPurchaseOrderService) {
        this.invPurchaseOrderService = invPurchaseOrderService;
    }

    public List<InvPurchaseOrderDTO> getInvPurchaseOrderDTOs() {
        if (invPurchaseOrderDTOs == null || invPurchaseOrderDTOs.isEmpty()) {
            invPurchaseOrderDTOs = invPurchaseOrderService.getALLPurchaseOrderByBranchId(getUserData().getUserBranch().getId(), getUserData().getUser());
        }
        return invPurchaseOrderDTOs;
    }

    public void setInvPurchaseOrderDTOs(List<InvPurchaseOrderDTO> invPurchaseOrderDTOs) {
        this.invPurchaseOrderDTOs = invPurchaseOrderDTOs;
    }

    @Override
    public String save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public InvPurchaseOrderDTO getInvPurchaseOrderDTOSelected() {
        return invPurchaseOrderDTOSelected;
    }

    public void setInvPurchaseOrderDTOSelected(InvPurchaseOrderDTO invPurchaseOrderDTOSelected) {
        this.invPurchaseOrderDTOSelected = invPurchaseOrderDTOSelected;
    }

    public InvPurchaseOrderDTO getInvPurchaseOrderDTO() {
        return invPurchaseOrderDTO;
    }

    public void setInvPurchaseOrderDTO(InvPurchaseOrderDTO invPurchaseOrderDTO) {
        this.invPurchaseOrderDTO = invPurchaseOrderDTO;
    }

}
