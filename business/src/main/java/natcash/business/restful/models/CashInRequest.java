package natcash.business.restful.models;

import org.openide.util.Exceptions;

import com.google.gson.Gson;

public class CashInRequest {
	private String requestId;

	private String fromAccountNumber;

	private String toAccountNumber;

	private Double amount;

	private String agentCode;

	private String content;

	private Long timestamp;

	private String signature;

	private int favorite;

	public String getFromAccountNumber() {
		return fromAccountNumber;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public String getContent() {
		return content;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAgentCode() {
		return this.agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getToAccountNumber() {
		return this.toAccountNumber;
	}

	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public int getFavorite() {
		return this.favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	@Override
	public String toString() {
		return "CashInRequest [requestId=" + requestId + ", fromAccountNumber=" + fromAccountNumber
				+ ", toAccountNumber=" + toAccountNumber + ", amount=" + amount + ", agentCode=" + agentCode
				+ ", content=" + content + ", timestamp=" + timestamp + ", signature=" + signature + ", favorite="
				+ favorite + "]";
	}

	public String toLogString() {
		try {
			Gson gson = new Gson();
			String req = gson.toJson(this);
			if (req != null)
				req = req.replaceAll("(\"pin\":\"[0-9]{6}\")", "\"pin\":\"******\"");
			return req;
		} catch (Exception ex) {
			Exceptions.printStackTrace(ex);
			return "";
		}
	}
}
