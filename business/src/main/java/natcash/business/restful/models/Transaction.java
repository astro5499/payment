package natcash.business.restful.models;

public class Transaction {
	private String requestId;
	private String txid;
	private AccountInfo sender;
	private AccountInfo receiver;
	private String content;
	private Double amount;
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public AccountInfo getSender() {
		return sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSender(AccountInfo sender) {
		this.sender = sender;
	}

	public AccountInfo getReceiver() {
		return receiver;
	}

	public void setReceiver(AccountInfo receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "Transaction [requestId=" + requestId + ", txid=" + txid + ", sender=" + sender + ", receiver="
				+ receiver + ", content=" + content + ", amount=" + amount + "]";
	}

	

}
