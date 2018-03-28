/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;

public class InventoryAdjustmentLineDtoBuilder {
    //the instance to build
    private InventoryAdjustmentLineDto inventoryAdjustmentLineDto;

    /**
     * Default constructor to initialize the instance
     */
    public InventoryAdjustmentLineDtoBuilder() {
        inventoryAdjustmentLineDto = new InventoryAdjustmentLineDto();
    }

    public InventoryAdjustmentLineDtoBuilder description(String description) {
        inventoryAdjustmentLineDto.setDescription(description);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder extCost(Double extCost) {
        inventoryAdjustmentLineDto.setExtCost(extCost);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder inventoryItem(InventoryNumberDescriptionDto inventoryItem) {
        inventoryAdjustmentLineDto.setInventoryItem(inventoryItem);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder lineNumber(Integer lineNumber) {
        inventoryAdjustmentLineDto.setLineNumber(lineNumber);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder location(LocationDto location) {
        inventoryAdjustmentLineDto.setLocation(location);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder quantity(Double quantity) {
        inventoryAdjustmentLineDto.setQuantity(quantity);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder reasonCode(ReasonCodeDto reasonCode) {
        inventoryAdjustmentLineDto.setReasonCode(reasonCode);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder receiptNumber(String receiptNumber) {
        inventoryAdjustmentLineDto.setReceiptNumber(receiptNumber);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder unitCost(Double unitCost) {
        inventoryAdjustmentLineDto.setUnitCost(unitCost);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder uom(String uom) {
        inventoryAdjustmentLineDto.setUom(uom);
        return this;
    }

    public InventoryAdjustmentLineDtoBuilder warehouse(WarehouseIdDescriptionDto warehouse) {
        inventoryAdjustmentLineDto.setWarehouse(warehouse);
        return this;
    }
    /**
     * Build the instance with the given values
     */
    public InventoryAdjustmentLineDto build() {
        return inventoryAdjustmentLineDto;
    }
}