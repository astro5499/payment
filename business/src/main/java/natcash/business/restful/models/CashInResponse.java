package natcash.business.restful.models;

import natcash.business.utils.ErrorCode;

public class CashInResponse {
	private String amount;
	  
	  private String fee;
	  
	  private String totalAmount;
	  
	  private AccountInfo receiver;
	  
	  private String transferFrom;
	  
	  private String receiverBank;
	  
	  private String discount;
	  
	  private String bonus1;
	  
	  private String bonus2;
	  
	  private int favorite;
	  
	  private String commission;
	  
	  private String txId;
	  
	  
	  
	  public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getCommission() {
	    return this.commission;
	  }
	  
	  public void setCommission(String commission) {
	    this.commission = commission;
	  }
	  
	  public String getBonus1() {
	    return this.bonus1;
	  }
	  
	  public void setBonus1(String bonus1) {
	    this.bonus1 = bonus1;
	  }
	  
	  public String getBonus2() {
	    return this.bonus2;
	  }
	  
	  public void setBonus2(String bonus2) {
	    this.bonus2 = bonus2;
	  }
	  
	  public String getAmount() {
	    return this.amount;
	  }
	  
	  public void setAmount(String amount) {
	    this.amount = amount;
	  }
	  
	  public String getFee() {
	    return this.fee;
	  }
	  
	  public void setFee(String fee) {
	    this.fee = fee;
	  }
	  
	  public String getTotalAmount() {
	    return this.totalAmount;
	  }
	  
	  public void setTotalAmount(String totalAmount) {
	    this.totalAmount = totalAmount;
	  }
	  
	  public AccountInfo getReceiver() {
	    return this.receiver;
	  }
	  
	  public void setReceiver(AccountInfo receiver) {
	    this.receiver = receiver;
	  }
	  
	  public String getTransferFrom() {
	    return this.transferFrom;
	  }
	  
	  public void setTransferFrom(String transferFrom) {
	    this.transferFrom = transferFrom;
	  }
	  
	  public String getReceiverBank() {
	    return this.receiverBank;
	  }
	  
	  public void setReceiverBank(String receiverBank) {
	    this.receiverBank = receiverBank;
	  }
	  
	  public String getDiscount() {
	    return this.discount;
	  }
	  
	  public void setDiscount(String discount) {
	    this.discount = discount;
	  }
	  
	  public int getFavorite() {
	    return this.favorite;
	  }
	  
	  public void setFavorite(int favorite) {
	    this.favorite = favorite;
	  }
}
