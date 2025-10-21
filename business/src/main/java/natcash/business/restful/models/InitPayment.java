package natcash.business.restful.models;

public class InitPayment {
	private String requestId;
	private String username;
	private Double amount;
	private Long timestamp;
	private String signature;
	private String callbackUrl;
	private String orderId;

	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "InitPayment [requestId=" + requestId + ", username=" + username + ", amount="
				+ amount + ", timestamp=" + timestamp + ", signature=" + signature + ", callbackUrl=" + callbackUrl
				+ ", orderId=" + orderId + "]";
	}
	
}
