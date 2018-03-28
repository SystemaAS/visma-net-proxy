/*
 * VismaNetAPILib
 *
 * This file was automatically generated by APIMATIC v2.0 ( https://apimatic.io ).
 */
package no.systema.visma.sdk.models;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class GeneralLedgerBalanceDto 
        implements java.io.Serializable {
    private static final long serialVersionUID = 5260872350704001357L;
    private AccountNumberDto account;
    private BalanceTypeEnum balanceType;
    private Double beginningBalance;
    private Double beginningBalanceInCurrency;
    private BranchNumberDto branch;
    private String currencyId;
    private String financialPeriod;
    private Date lastModifiedDateTime;
    private LedgerDescriptionDto ledger;
    private Double periodToDateCredit;
    private Double periodToDateCreditInCurrency;
    private Double periodToDateDebit;
    private Double periodToDateDebitInCurrency;
    private Integer subaccountId;
    private Boolean yearClosed;
    private Double yearToDateBalance;
    private Double yearToDateBalanceInCurrency;
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
    @JsonGetter("balanceType")
    public BalanceTypeEnum getBalanceType ( ) { 
        return this.balanceType;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("balanceType")
    public void setBalanceType (BalanceTypeEnum value) { 
        this.balanceType = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("beginningBalance")
    public Double getBeginningBalance ( ) { 
        return this.beginningBalance;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("beginningBalance")
    public void setBeginningBalance (Double value) { 
        this.beginningBalance = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("beginningBalanceInCurrency")
    public Double getBeginningBalanceInCurrency ( ) { 
        return this.beginningBalanceInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("beginningBalanceInCurrency")
    public void setBeginningBalanceInCurrency (Double value) { 
        this.beginningBalanceInCurrency = value;
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
    @JsonGetter("currencyId")
    public String getCurrencyId ( ) { 
        return this.currencyId;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("currencyId")
    public void setCurrencyId (String value) { 
        this.currencyId = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("financialPeriod")
    public String getFinancialPeriod ( ) { 
        return this.financialPeriod;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("financialPeriod")
    public void setFinancialPeriod (String value) { 
        this.financialPeriod = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("lastModifiedDateTime")
    public Date getLastModifiedDateTime ( ) { 
        return this.lastModifiedDateTime;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("lastModifiedDateTime")
    public void setLastModifiedDateTime (Date value) { 
        this.lastModifiedDateTime = value;
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
    @JsonGetter("periodToDateCredit")
    public Double getPeriodToDateCredit ( ) { 
        return this.periodToDateCredit;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("periodToDateCredit")
    public void setPeriodToDateCredit (Double value) { 
        this.periodToDateCredit = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("periodToDateCreditInCurrency")
    public Double getPeriodToDateCreditInCurrency ( ) { 
        return this.periodToDateCreditInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("periodToDateCreditInCurrency")
    public void setPeriodToDateCreditInCurrency (Double value) { 
        this.periodToDateCreditInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("periodToDateDebit")
    public Double getPeriodToDateDebit ( ) { 
        return this.periodToDateDebit;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("periodToDateDebit")
    public void setPeriodToDateDebit (Double value) { 
        this.periodToDateDebit = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("periodToDateDebitInCurrency")
    public Double getPeriodToDateDebitInCurrency ( ) { 
        return this.periodToDateDebitInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("periodToDateDebitInCurrency")
    public void setPeriodToDateDebitInCurrency (Double value) { 
        this.periodToDateDebitInCurrency = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("subaccountId")
    public Integer getSubaccountId ( ) { 
        return this.subaccountId;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("subaccountId")
    public void setSubaccountId (Integer value) { 
        this.subaccountId = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("yearClosed")
    public Boolean getYearClosed ( ) { 
        return this.yearClosed;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("yearClosed")
    public void setYearClosed (Boolean value) { 
        this.yearClosed = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("yearToDateBalance")
    public Double getYearToDateBalance ( ) { 
        return this.yearToDateBalance;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("yearToDateBalance")
    public void setYearToDateBalance (Double value) { 
        this.yearToDateBalance = value;
    }
 
    /** GETTER
     * TODO: Write general description for this method
     */
    @JsonGetter("yearToDateBalanceInCurrency")
    public Double getYearToDateBalanceInCurrency ( ) { 
        return this.yearToDateBalanceInCurrency;
    }
    
    /** SETTER
     * TODO: Write general description for this method
     */
    @JsonSetter("yearToDateBalanceInCurrency")
    public void setYearToDateBalanceInCurrency (Double value) { 
        this.yearToDateBalanceInCurrency = value;
    }
 
}
 