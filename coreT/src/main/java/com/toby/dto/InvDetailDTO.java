/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toby.dto;

import com.toby.entity.UnitsItems;
import com.toby.views.ItemsBarcodesDetailsView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author amr
 */
public class InvDetailDTO extends BaseEntityDTO {

    private BigDecimal quantity;
    private BigDecimal cost;
    private InvInventoryDTO inventoryDTO;
    private BigDecimal discount;
    private Integer discountType;
    private BigDecimal totalQuantity;
    private BigDecimal totalQuantityWithScrewing;
    private BigDecimal availableQuantityRow;
    private BigDecimal realQuantity;
    private Integer transferFrom;
    private BigDecimal finalQuantity;
    private BigDecimal weight;
    private Integer invPurchaseInvoiceId;
    private InvItemDTO itemId;
    private InvItemDTO itemIdBak;
    private SymbolDTO unitId;
    private BigDecimal extraCost;
    private Integer selectedId;
    private BigDecimal screwing;
    private String itemBarcode;
    private BigDecimal net;
    private Integer serial;
    private BigDecimal taxValue;
    private Integer status;
    private BigDecimal costAvarage;
    private InvItemDTO invItemParentId;
    private Integer clothNumber;
    private BigDecimal number;
    private BigDecimal total;

    private BigDecimal bounse;
    private ItemsBarcodesDetailsView itemsBarcodesDetail;
    private ItemsBarcodesDetailsView itemsBarcodesDetailTrans;
    private SymbolDTO paintColor;
    private BigDecimal length;
    private BigDecimal valueAfterDiscount;
    private UnitsItems unitsItem;
    private Integer unitsItemSelected;
    private List<UnitsItems> unitsItemList;
    private Boolean showPrice;
    private Boolean showDiscount;

    public InvDetailDTO() {
    }

    public BigDecimal getQuantity() {
        if (quantity == null) {
            quantity = BigDecimal.ZERO;
        }
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        if (cost == null) {
            cost = BigDecimal.ZERO;
        }
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getDiscount() {
        if (discount == null) {
            discount = BigDecimal.ZERO;
        }
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public Integer getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(Integer transferFrom) {
        this.transferFrom = transferFrom;
    }

    public Integer getInvPurchaseInvoiceId() {
        return invPurchaseInvoiceId;
    }

    public void setInvPurchaseInvoiceId(Integer invPurchaseInvoiceId) {
        this.invPurchaseInvoiceId = invPurchaseInvoiceId;
    }

    public InvItemDTO getItemId() {
   
        return itemId;
    }

    public void setItemId(InvItemDTO itemId) {
        this.itemId = itemId;
    }

    public SymbolDTO getUnitId() {
        return unitId;
    }

    public void setUnitId(SymbolDTO unitId) {
        this.unitId = unitId;
    }

    /**
     * @return the extraCost
     */
    public BigDecimal getExtraCost() {
        if (extraCost == null) {
            extraCost = BigDecimal.ZERO;
        }
        return extraCost;
    }

    public BigDecimal getScrewing() {
        if (screwing == null) {
            screwing = BigDecimal.ONE;
        }
        return screwing;
    }

    public void setScrewing(BigDecimal screwing) {
        this.screwing = screwing;
    }

    /**
     * @param extraCost the extraCost to set
     */
    public void setExtraCost(BigDecimal extraCost) {
        this.extraCost = extraCost;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvDetailDTO)) {
            return false;
        }
        InvDetailDTO other = (InvDetailDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return itemBarcode + " " + (itemId == null ? null : itemId.getName());
    }

    public Integer getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Integer selectedId) {
        this.selectedId = selectedId;
    }

    /**
     * @return the itemBarcode
     */
    public String getItemBarcode() {
        return itemBarcode;
    }

    /**
     * @param itemBarcode the itemBarcode to set
     */
    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    /**
     * @return the net
     */
    public BigDecimal getNet() {
        if (net == null) {
            net = BigDecimal.ZERO;
        }
        return net;
    }

    /**
     * @param net the net to set
     */
    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public BigDecimal getTaxValue() {
        if (taxValue == null) {
            taxValue = BigDecimal.ZERO;
        }
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    /**
     * @return the finalQuantity
     */
    public BigDecimal getFinalQuantity() {
        if (finalQuantity == null) {
            finalQuantity = BigDecimal.ZERO;
        }
        return finalQuantity;
    }

    /**
     * @param finalQuantity the finalQuantity to set
     */
    public void setFinalQuantity(BigDecimal finalQuantity) {
        this.finalQuantity = finalQuantity;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the weight
     */
    public BigDecimal getWeight() {
        if (weight == null) {
            weight = BigDecimal.ZERO;
        }
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * @return the costAvarage
     */
    public BigDecimal getCostAvarage() {
        if (costAvarage == null) {
            costAvarage = BigDecimal.ZERO;
        }
        return costAvarage;
    }

    /**
     * @param costAvarage the costAvarage to set
     */
    public void setCostAvarage(BigDecimal costAvarage) {
        this.costAvarage = costAvarage;
    }

    /**
     * @return the itemsBarcodesDetail
     */
    public ItemsBarcodesDetailsView getItemsBarcodesDetail() {
        return itemsBarcodesDetail;
    }

    /**
     * @param itemsBarcodesDetail the itemsBarcodesDetail to set
     */
    public void setItemsBarcodesDetail(ItemsBarcodesDetailsView itemsBarcodesDetail) {
        this.itemsBarcodesDetail = itemsBarcodesDetail;
    }

    /**
     * @return the itemsBarcodesDetailTrans
     */
    public ItemsBarcodesDetailsView getItemsBarcodesDetailTrans() {
        return itemsBarcodesDetailTrans;
    }

    /**
     * @param itemsBarcodesDetailTrans the itemsBarcodesDetailTrans to set
     */
    public void setItemsBarcodesDetailTrans(ItemsBarcodesDetailsView itemsBarcodesDetailTrans) {
        this.itemsBarcodesDetailTrans = itemsBarcodesDetailTrans;
    }

    /**
     * @return the paintColor
     */
    public SymbolDTO getPaintColor() {
        return paintColor;
    }

    /**
     * @param paintColor the paintColor to set
     */
    public void setPaintColor(SymbolDTO paintColor) {
        this.paintColor = paintColor;
    }

    /**
     * @return the length
     */
    public BigDecimal getLength() {
        if (length == null) {
            length = BigDecimal.ZERO;
        }
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(BigDecimal length) {
        this.length = length;
    }

    /**
     * @return the valueAfterDiscount
     */
    public BigDecimal getValueAfterDiscount() {
        return valueAfterDiscount;
    }

    /**
     * @param valueAfterDiscount the valueAfterDiscount to set
     */
    public void setValueAfterDiscount(BigDecimal valueAfterDiscount) {
        this.valueAfterDiscount = valueAfterDiscount;
    }

    /**
     * @return the invItemParentId
     */
    public InvItemDTO getInvItemParentId() {
        if (invItemParentId == null) {
            invItemParentId = new InvItemDTO();
        }
        return invItemParentId;
    }

    /**
     * @param invItemParentId the invItemParentId to set
     */
    public void setInvItemParentId(InvItemDTO invItemParentId) {
        this.invItemParentId = invItemParentId;
    }

    /**
     * @return the clothNumber
     */
    public Integer getClothNumber() {
        if (clothNumber == null) {
            clothNumber = 1;
        }
        return clothNumber;
    }

    /**
     * @param clothNumber the clothNumber to set
     */
    public void setClothNumber(Integer clothNumber) {
        this.clothNumber = clothNumber;
    }

    /**
     * @return the number
     */
    public BigDecimal getNumber() {
        if (number == null) {
            number = BigDecimal.ZERO;

        }

        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    /**
     * @return the totalQuantityRow
     */
    public BigDecimal getTotalQuantity() {
        if (totalQuantity == null) {
            totalQuantity = BigDecimal.ZERO;

        }

        return totalQuantity;
    }

    /**
     * @param totalQuantity
     */
    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    /**
     * @return the availableQuantityRow
     */
    public BigDecimal getAvailableQuantityRow() {
        if (availableQuantityRow == null) {
            availableQuantityRow = BigDecimal.ZERO;

        }

        return availableQuantityRow;
    }

    /**
     * @param availableQuantityRow the availableQuantityRow to set
     */
    public void setAvailableQuantityRow(BigDecimal availableQuantityRow) {
        this.availableQuantityRow = availableQuantityRow;
    }

    /**
     * @return the bounse
     */
    public BigDecimal getBounse() {
        if (bounse == null) {
            bounse = BigDecimal.ZERO;
        }

        return bounse;
    }

    /**
     * @param bounse the bounse to set
     */
    public void setBounse(BigDecimal bounse) {
        this.bounse = bounse;
    }

    /**
     * @return the inventoryDTO
     */
    public InvInventoryDTO getInventoryDTO() {
        return inventoryDTO;
    }

    /**
     * @param inventoryDTO the inventoryDTO to set
     */
    public void setInventoryDTO(InvInventoryDTO inventoryDTO) {
        this.inventoryDTO = inventoryDTO;
    }

    /**
     * @return the itemIdBak
     */
    public InvItemDTO getItemIdBak() {
        return itemIdBak;
    }

    /**
     * @param itemIdBak the itemIdBak to set
     */
    public void setItemIdBak(InvItemDTO itemIdBak) {
        this.itemIdBak = itemIdBak;
    }

    /**
     * @return the unitsItem
     */
    public UnitsItems getUnitsItem() {
        return unitsItem;
    }

    /**
     * @param unitsItem the unitsItem to set
     */
    public void setUnitsItem(UnitsItems unitsItem) {
        this.unitsItem = unitsItem;
    }

    /**
     * @return the showPrice
     */
    public Boolean getShowPrice() {
        return showPrice;
    }

    /**
     * @param showPrice the showPrice to set
     */
    public void setShowPrice(Boolean showPrice) {
        this.showPrice = showPrice;
    }

    /**
     * @return the showDiscount
     */
    public Boolean getShowDiscount() {
        return showDiscount;
    }

    /**
     * @param showDiscount the showDiscount to set
     */
    public void setShowDiscount(Boolean showDiscount) {
        this.showDiscount = showDiscount;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        if(total==null){
            total=BigDecimal.ZERO;
        }
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the unitsItemList
     */
    public List<UnitsItems> getUnitsItemList() {
        return unitsItemList;
    }

    /**
     * @param unitsItemList the unitsItemList to set
     */
    public void setUnitsItemList(List<UnitsItems> unitsItemList) {
        this.unitsItemList = unitsItemList;
    }

    /**
     * @return the unitsItemSelected
     */
    public Integer getUnitsItemSelected() {
        return unitsItemSelected;
    }

    /**
     * @param unitsItemSelected the unitsItemSelected to set
     */
    public void setUnitsItemSelected(Integer unitsItemSelected) {
        this.unitsItemSelected = unitsItemSelected;
    }

    /**
     * @return the totalQuantityWithScrewing
     */
    public BigDecimal getTotalQuantityWithScrewing() {
        if (totalQuantityWithScrewing == null) {
            totalQuantityWithScrewing = BigDecimal.ZERO;
        }
        return totalQuantityWithScrewing;
    }

    /**
     * @param totalQuantityWithScrewing the totalQuantityWithScrewing to set
     */
    public void setTotalQuantityWithScrewing(BigDecimal totalQuantityWithScrewing) {
        this.totalQuantityWithScrewing = totalQuantityWithScrewing;
    }

    /**
     * @return the realQuantity
     */
    public BigDecimal getRealQuantity() {
        return realQuantity;
    }

    /**
     * @param realQuantity the realQuantity to set
     */
    public void setRealQuantity(BigDecimal realQuantity) {
        this.realQuantity = realQuantity;
    }

}
