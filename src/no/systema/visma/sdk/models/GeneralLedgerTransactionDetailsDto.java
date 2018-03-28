/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class GeneralLedgerTransactionDetailsDto 
        implements java.io.Serializable {
    private static final long serialVersionUID = 4658704818703443602L;
    private AccountNumberDto account;
    private String batchNumber;
    private Double begBalance;
    private BranchNumberDto branch;
    private Double creditAmount;
    private Double currBegBalance;
    private Double currCreditAmount;
    private Double currDebitAmount;
    private String currency;
    private Double currEndingBalance;
    private Double debitAmount;
    private String description;
    private Double endingBalance;
    private LedgerDescriptionDto ledger;
    private Integer lineNumber;
    private String module;
    private String period;
    private String refNumber;
    private String subaccount;
    private Date tranDate;
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("account")
    public AccountNumberDto getAccount ( ) { 
        return this.account;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("account")
    public void setAccount (AccountNumberDto value) { 
        this.account = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("batchNumber")
    public String getBatchNumber ( ) { 
        return this.batchNumber;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("batchNumber")
    public void setBatchNumber (String value) { 
        this.batchNumber = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("begBalance")
    public Double getBegBalance ( ) { 
        return this.begBalance;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("begBalance")
    public void setBegBalance (Double value) { 
        this.begBalance = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("branch")
    public BranchNumberDto getBranch ( ) { 
        return this.branch;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("branch")
    public void setBranch (BranchNumberDto value) { 
        this.branch = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("creditAmount")
    public Double getCreditAmount ( ) { 
        return this.creditAmount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("creditAmount")
    public void setCreditAmount (Double value) { 
        this.creditAmount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("currBegBalance")
    public Double getCurrBegBalance ( ) { 
        return this.currBegBalance;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("currBegBalance")
    public void setCurrBegBalance (Double value) { 
        this.currBegBalance = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("currCreditAmount")
    public Double getCurrCreditAmount ( ) { 
        return this.currCreditAmount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("currCreditAmount")
    public void setCurrCreditAmount (Double value) { 
        this.currCreditAmount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("currDebitAmount")
    public Double getCurrDebitAmount ( ) { 
        return this.currDebitAmount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("currDebitAmount")
    public void setCurrDebitAmount (Double value) { 
        this.currDebitAmount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("currency")
    public String getCurrency ( ) { 
        return this.currency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("currency")
    public void setCurrency (String value) { 
        this.currency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("currEndingBalance")
    public Double getCurrEndingBalance ( ) { 
        return this.currEndingBalance;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("currEndingBalance")
    public void setCurrEndingBalance (Double value) { 
        this.currEndingBalance = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("debitAmount")
    public Double getDebitAmount ( ) { 
        return this.debitAmount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("debitAmount")
    public void setDebitAmount (Double value) { 
        this.debitAmount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("description")
    public String getDescription ( ) { 
        return this.description;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("description")
    public void setDescription (String value) { 
        this.description = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("endingBalance")
    public Double getEndingBalance ( ) { 
        return this.endingBalance;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("endingBalance")
    public void setEndingBalance (Double value) { 
        this.endingBalance = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("ledger")
    public LedgerDescriptionDto getLedger ( ) { 
        return this.ledger;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("ledger")
    public void setLedger (LedgerDescriptionDto value) { 
        this.ledger = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("lineNumber")
    public Integer getLineNumber ( ) { 
        return this.lineNumber;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("lineNumber")
    public void setLineNumber (Integer value) { 
        this.lineNumber = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("module")
    public String getModule ( ) { 
        return this.module;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("module")
    public void setModule (String value) { 
        this.module = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("period")
    public String getPeriod ( ) { 
        return this.period;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("period")
    public void setPeriod (String value) { 
        this.period = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("refNumber")
    public String getRefNumber ( ) { 
        return this.refNumber;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("refNumber")
    public void setRefNumber (String value) { 
        this.refNumber = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("subaccount")
    public String getSubaccount ( ) { 
        return this.subaccount;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("subaccount")
    public void setSubaccount (String value) { 
        this.subaccount = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("tranDate")
    public Date getTranDate ( ) { 
        return this.tranDate;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("tranDate")
    public void setTranDate (Date value) { 
        this.tranDate = value;
    }
 
}
 