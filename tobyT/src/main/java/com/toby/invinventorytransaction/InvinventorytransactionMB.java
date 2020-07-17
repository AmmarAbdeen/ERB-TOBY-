package com.toby.invinventorytransaction;
//
import com.toby.businessservice.InvInventoryService;
import com.toby.businessservice.InvInventoryTransactionDetailService;
import com.toby.businessservice.InvInventoryTransactionService;
import com.toby.businessservice.InvItemService;
import com.toby.businessservice.InvPurchaseInvoiceDetailService;
import com.toby.businessservice.InvPurchaseInvoiceService;
import com.toby.businessservice.InvPurchaseOrderService;
import com.toby.businessservice.QuantityItemsStoreAddExitService;
import com.toby.converter.InvInventoryDTOConverter;
import com.toby.converter.InvPurchaseInvoiceDTOConverter;
import com.toby.converter.InvPurchaseOrderDTOConverter;
import com.toby.converter.InvinventorytransactionDTOConverter;
import com.toby.converter.ItemDTOConverter;
import com.toby.dto.InvInventoryDTO;
import com.toby.dto.InvInventoryTransactionDTO;
import com.toby.dto.InvInventoryTransactionDetailDTO;
import com.toby.dto.InvItemDTO;
import com.toby.dto.InvPurchaseInvoiceDTO1;
import com.toby.dto.InvPurchaseInvoiceDetailDTO;
import com.toby.dto.InvPurchaseOrderDTO;
import com.toby.entity.InvPurchaseInvoice;
import com.toby.entity.InvPurchaseInvoiceDetail;
import com.toby.entity.QuantityItemsStoreAddExit;
import com.toby.toby.BaseFormBean;
import com.toby.toby.BaseListBean;
import com.toby.toby.UserData;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

//import static sun.management.snmp.util.JvmContextFactory.getUserData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user4
 */
@Named(value = "invinventorytransactionMB")
@ViewScoped
public class InvinventorytransactionMB extends BaseFormBean {

    private InvInventoryTransactionDTO invInventoryTransactionDTO;
    private InvInventoryTransactionDetailDTO invInventoryTransactionDetailDTOSelected;
    private InvPurchaseInvoiceDTO1 invPurchaseInvoiceDTO;
    private List<InvItemDTO> invItemDTOList;
    private List<InvPurchaseOrderDTO> invPurchaseOrderDTOList;
    private List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList;
    private List<InvPurchaseInvoiceDetailDTO> invPurchaseInvoiceDetailDTOList;

    private InvinventorytransactionDTOConverter invinventorytransactionDTOConverter;
    private InvPurchaseOrderDTOConverter invPurchaseOrderDTOConverter;
    private List<InvInventoryDTO> invInventoryDTOList;
    private InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTO;
    private ItemDTOConverter itemDTOConverter;
    private InvInventoryDTOConverter invInventoryDTOConverter;
    private InvPurchaseInvoiceDTOConverter invPurchaseInvoiceDTOConverter;
    private List<QuantityItemsStoreAddExit> quantityItemsStoreAddExitList;

    private Map<Integer, BigDecimal> quantityMap;
    @EJB
    private InvInventoryTransactionDetailService invInventoryTransactionDetailService;
    @EJB
    private InvInventoryService invInventoryService;
    @EJB
    private InvPurchaseInvoiceService invPurchaseInvoiceService;
    @EJB
    private InvPurchaseInvoiceDetailService invPurchaseInvoiceDetailService;
    @EJB
    private InvItemService invItemService;
    @EJB
    private InvInventoryTransactionService invInventoryTransactionService;
    @EJB
    private InvPurchaseOrderService invPurchaseOrderService;
    @EJB
    private QuantityItemsStoreAddExitService quantityItemsStoreAddExitService;

    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        setUserData((UserData) context.getSessionMap().get("userLogInData"));

        setScreenMode((String) context.getSessionMap().get("ScreenMode"));

        if (getScreenMode() != null && getScreenMode().equalsIgnoreCase("Edit")) {
            try {
                Integer invinventoryidselected = (Integer) context.getSessionMap().get("invinventoryidselected");
                if (invInventoryTransactionDTO == null) {
                    invInventoryTransactionDTO
                            = invInventoryTransactionService.getInvInventoryTransactionDTO(invinventoryidselected, getUserData().getUser());
                    invInventoryTransactionDTO.setInvInventoryTransactionDetailDTOList(invInventoryTransactionDetailService.selectDetailsInvInvetoryByIdDTO(invinventoryidselected, getUserData().getUser()));
                    BigDecimal sumQuantity = new BigDecimal(0);
                    for (InvInventoryTransactionDetailDTO dTO : invInventoryTransactionDTO.getInvInventoryTransactionDetailDTOList()) {
                        if (dTO.getQuantity() != null) {
                            sumQuantity = sumQuantity.add(dTO.getQuantity());
                        }
                    }
                    invInventoryTransactionDTO.setSumQuantity(sumQuantity);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
                getInvInventoryDTOList();
                getInvPurchaseInvoiceDTOList();
  getInvPurchaseOrderDTOList();
         getInvItemDTOList();
    
    }
    public void reset() {
        invInventoryTransactionDTO = new InvInventoryTransactionDTO();
        InvInventoryTransactionDetailDTO invInventoryTransactionDetailDTO = new InvInventoryTransactionDetailDTO();
    }

    public void sumQuantity() {
//        if (invInventoryTransactionDetailDTOSelected != null && invInventoryTransactionDetailDTOSelected.getQuantity() != null
//                && invInventoryTransactionDetailDTOSelected.getQuantity().compareTo(BigDecimal.ZERO) > 0
//                && invInventoryTransactionDetailDTOSelected.getQuantity().compareTo(invInventoryTransactionDetailDTOSelected.getAvailableQuantity()) <= 0) {
//
//        } else {
//            invInventoryTransactionDetailDTOSelected.setQuantity(getQuantityMap().get(invInventoryTransactionDetailDTOSelected.getItemId().getId()));
//
//        }
        BigDecimal sumQuantity = new BigDecimal(0);
        if (invInventoryTransactionDetailDTOSelected != null && invInventoryTransactionDetailDTOSelected.getItemId() != null) {
            for (InvInventoryTransactionDetailDTO detaildto : invInventoryTransactionDTO.getInvInventoryTransactionDetailDTOList()) {
                if (detaildto.getQuantity() != null) {
                    sumQuantity = sumQuantity.add(detaildto.getQuantity());
                }
            }
        }
        invInventoryTransactionDTO.setSumQuantity(sumQuantity);
    }

    public void changeInventory() {
        if (invInventoryTransactionDTO != null && invInventoryTransactionDTO.getInvInventoryId() != null && invInventoryTransactionDTO.getInvInventoryId().getId() != null) {
            quantityMap = quantityItemsStoreAddExitService.findInventoryDTOList(getUserData().getUser(), invInventoryTransactionDTO.getInvInventoryId().getId());
            showDetail();
        }
    }

    public void addRow() {
        if (validateDetails()) {
            InvInventoryTransactionDetailDTO invInventoryTransactionDetailDTO = new InvInventoryTransactionDetailDTO();
            invInventoryTransactionDetailDTO.setIndex(getIndex());
            invInventoryTransactionDTO.getInvInventoryTransactionDetailDTOList().add(invInventoryTransactionDetailDTO);
            invInventoryTransactionDetailDTOSelected = invInventoryTransactionDetailDTO;
        }
    }

    public boolean validateDetails() {
        boolean bool = true;
        for (InvInventoryTransactionDetailDTO invInventoryTransactionDetailDTO : invInventoryTransactionDTO.getInvInventoryTransactionDetailDTOList()) {
            if (invInventoryTransactionDetailDTO.getItemId() == null || invInventoryTransactionDetailDTO.getQuantity() == null) {
                bool = false;
            }
        }
        return bool;
    }

    public void validateItem() {
        if (invInventoryTransactionDetailDTOSelected != null && invInventoryTransactionDetailDTOSelected.getItemId() != null) {

            invInventoryTransactionDetailDTOSelected.setAvailableQuantity(getQuantityMap().get(invInventoryTransactionDetailDTOSelected.getItemId().getId()));
        }
        if (getQuantityMap().get(invInventoryTransactionDetailDTOSelected.getItemId().getId()) != null && getQuantityMap().get(invInventoryTransactionDetailDTOSelected.getItemId().getId()).compareTo(BigDecimal.ZERO) > 0) {
            invInventoryTransactionDetailDTOSelected.setAvailableQuantity(getQuantityMap().get(invInventoryTransactionDetailDTOSelected.getItemId().getId()));
        } else {
            invInventoryTransactionDetailDTOSelected.setAvailableQuantity(getQuantityMap().get(invInventoryTransactionDetailDTOSelected.getItemId().getId()));
        }
    }

    public String save() {

        invInventoryTransactionService.saveInvInventorryTransaction(invInventoryTransactionDTO, getUserData().getUser());
//        if (!invInventoryTransactionDTO.getMsg().isEmpty()) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", invInventoryTransactionDTO.getMsg()));
//        }
        try {

            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

            exit("../invinventorytransaction/invinventorytransactionList.xhtml");
            return "";

        } catch (Exception e) {
            saveError(e, "InvInventoryTransactionListMB", "goToAdd");
            return null;
        }

    }

    public void showDetail() {

        invInventoryTransactionDTO = invInventoryTransactionService.getInvInventoryTransactionDetailsByPurchaseIdFromView(invInventoryTransactionDTO, invInventoryTransactionDTO.getInvpurchaseinvoiceId(), invInventoryTransactionDTO.getInvInventoryId().getId(), getUserData().getUser());
        invInventoryTransactionDetailDTOSelected = invInventoryTransactionDTO.getInvInventoryTransactionDetailDTOList().get(0);

    }

    public InvInventoryTransactionDTO getInvInventoryTransactionDTO() {
        if (invInventoryTransactionDTO == null) {
            invInventoryTransactionDTO = new InvInventoryTransactionDTO();
            invInventoryTransactionDTO.setInvInventoryTransactionDetailDTOList(new ArrayList<>());
            invInventoryTransactionDTO.setInvInventoryId(invInventoryDTOList.get(0));
        }

        return invInventoryTransactionDTO;
    }

    public void setInvInventoryTransactionDTO(InvInventoryTransactionDTO invInventoryTransactionDTO) {
        this.invInventoryTransactionDTO = invInventoryTransactionDTO;
    }

    public List<InvItemDTO> getInvItemDTOList() {
        if (invItemDTOList == null || invItemDTOList.isEmpty()) {
            invItemDTOList = invItemService.findInvItemDTOList(getUserData().getUser());
            setItemDTOConverter(new ItemDTOConverter(invItemDTOList));
        }
        return invItemDTOList;
    }

    public void setInvItemDTOList(List<InvItemDTO> invItemDTOList) {
        this.invItemDTOList = invItemDTOList;
    }

    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getScreenName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public InvinventorytransactionDTOConverter getInvinventorytransactionDTOConverter() {
        return invinventorytransactionDTOConverter;
    }

    public void setInvinventorytransactionDTOConverter(InvinventorytransactionDTOConverter invinventorytransactionDTOConverter) {
        this.invinventorytransactionDTOConverter = invinventorytransactionDTOConverter;
    }

    public List<InvPurchaseOrderDTO> getInvPurchaseOrderDTOList() {
        if (invPurchaseOrderDTOList == null || invPurchaseOrderDTOList.isEmpty()) {
            invPurchaseOrderDTOList = invPurchaseOrderService.findInvPurchaseOrederrDTO(getUserData().getUser());
            setInvPurchaseOrderDTOConverter(new InvPurchaseOrderDTOConverter(invPurchaseOrderDTOList));
        }
        return invPurchaseOrderDTOList;
    }

    public void setInvPurchaseOrderDTOList(List<InvPurchaseOrderDTO> invPurchaseOrderDTOList) {
        this.invPurchaseOrderDTOList = invPurchaseOrderDTOList;
    }

    public InvPurchaseOrderDTOConverter getInvPurchaseOrderDTOConverter() {
        return invPurchaseOrderDTOConverter;
    }

    public void setInvPurchaseOrderDTOConverter(InvPurchaseOrderDTOConverter invPurchaseOrderDTOConverter) {
        this.invPurchaseOrderDTOConverter = invPurchaseOrderDTOConverter;
    }

    public List<InvPurchaseInvoiceDTO1> getInvPurchaseInvoiceDTOList() {
        if (invPurchaseInvoiceDTOList == null || invPurchaseInvoiceDTOList.isEmpty()) {
//            invPurchaseInvoiceDTOList = invPurchaseInvoiceService.findInvPurchaseInvoiceDTOListByReceved(getUserData().getUser());
            invPurchaseInvoiceDTOList = invPurchaseInvoiceService.getPurchaseIdFromView(getUserData().getUser());

            setInvPurchaseInvoiceDTOConverter(new InvPurchaseInvoiceDTOConverter(invPurchaseInvoiceDTOList));
        }
        return invPurchaseInvoiceDTOList;
    }

    public void setInvPurchaseInvoiceDTOList(List<InvPurchaseInvoiceDTO1> invPurchaseInvoiceDTOList) {
        this.invPurchaseInvoiceDTOList = invPurchaseInvoiceDTOList;
    }

    public ItemDTOConverter getItemDTOConverter() {
        return itemDTOConverter;
    }

    public void setItemDTOConverter(ItemDTOConverter itemDTOConverter) {
        this.itemDTOConverter = itemDTOConverter;
    }

    public InvInventoryTransactionDetailDTO getInvInventoryTransactionDetailDTOSelected() {
        if (invInventoryTransactionDetailDTOSelected == null) {
            invInventoryTransactionDetailDTOSelected = new InvInventoryTransactionDetailDTO();

        }

        return invInventoryTransactionDetailDTOSelected;
    }

    public void setInvInventoryTransactionDetailDTOSelected(InvInventoryTransactionDetailDTO invInventoryTransactionDetailDTOSelected) {
        this.invInventoryTransactionDetailDTOSelected = invInventoryTransactionDetailDTOSelected;
    }

    public List<InvInventoryDTO> getInvInventoryDTOList() {
        if (invInventoryDTOList == null || invInventoryDTOList.isEmpty()) {
            invInventoryDTOList = invInventoryService.findInventoryDTOListBYBranshID(getUserData().getUser());
            setInvInventoryDTOConverter(new InvInventoryDTOConverter(invInventoryDTOList));
        }

        return invInventoryDTOList;
    }

    public void setInvInventoryDTOList(List<InvInventoryDTO> invInventoryDTOList) {
        this.invInventoryDTOList = invInventoryDTOList;

    }

    public InvInventoryDTOConverter getInvInventoryDTOConverter() {
        return invInventoryDTOConverter;
    }

    public void setInvInventoryDTOConverter(InvInventoryDTOConverter invInventoryDTOConverter) {
        this.invInventoryDTOConverter = invInventoryDTOConverter;
    }

    public InvPurchaseInvoiceDTOConverter getInvPurchaseInvoiceDTOConverter() {
        return invPurchaseInvoiceDTOConverter;
    }

    public void setInvPurchaseInvoiceDTOConverter(InvPurchaseInvoiceDTOConverter invPurchaseInvoiceDTOConverter) {
        this.invPurchaseInvoiceDTOConverter = invPurchaseInvoiceDTOConverter;
    }

    public Map<Integer, BigDecimal> getQuantityMap() {
        if (quantityMap == null) {
            quantityMap = new HashMap<>();
        }
        return quantityMap;
    }

    public void setQuantityItemsStoreAddExitList(List<QuantityItemsStoreAddExit> quantityItemsStoreAddExitList) {
        this.quantityItemsStoreAddExitList = quantityItemsStoreAddExitList;
    }

    public InvPurchaseInvoiceDetailDTO getInvPurchaseInvoiceDetailDTO() {
        return invPurchaseInvoiceDetailDTO;
    }

    public void setInvPurchaseInvoiceDetailDTO(InvPurchaseInvoiceDetailDTO invPurchaseInvoiceDetailDTO) {
        this.invPurchaseInvoiceDetailDTO = invPurchaseInvoiceDetailDTO;
    }

    public InvPurchaseInvoiceDTO1 getInvPurchaseInvoiceDTO() {
        return invPurchaseInvoiceDTO;
    }

    public void setInvPurchaseInvoiceDTO(InvPurchaseInvoiceDTO1 invPurchaseInvoiceDTO) {
        this.invPurchaseInvoiceDTO = invPurchaseInvoiceDTO;
    }

    public List<InvPurchaseInvoiceDetailDTO> getInvPurchaseInvoiceDetailDTOList() {

        if (invPurchaseInvoiceDetailDTOList == null && invPurchaseInvoiceDetailDTOList.isEmpty()) {
            invPurchaseInvoiceDetailDTOList = invPurchaseInvoiceDetailService.getInvPurchaseInvoiceDetailsByPurchaseId(invInventoryTransactionDTO.getInvpurchaseinvoiceId().getId(), getUserData().getUser());

        }
        return invPurchaseInvoiceDetailDTOList;
    }

    public void setInvPurchaseInvoiceDetailDTOList(List<InvPurchaseInvoiceDetailDTO> invPurchaseInvoiceDetailDTOList) {
        this.invPurchaseInvoiceDetailDTOList = invPurchaseInvoiceDetailDTOList;
    }

}
