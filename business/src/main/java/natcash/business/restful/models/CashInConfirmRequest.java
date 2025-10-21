package natcash.business.restful.models;

import org.openide.util.Exceptions;

import com.google.gson.Gson;

public class CashInConfirmRequest {
	private String txId;
	private String isConfirm;
	private String verifyCode;
	private Long timestamp;
	private String signature;
	private String requestId;
	private String fromAccountNumber;
	
	
	public String getFromAccountNumber() {
		return fromAccountNumber;
	}

	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Override
	public String toString() {
		return "CashInConfirmRequest [txId=" + txId + ", isConfirm=" + isConfirm + ", verifyCode=" + verifyCode
				+ ", timestamp=" + timestamp + ", signature=" + signature + ", requestId=" + requestId + "]";
	}

	public String toLogString() {
		try {
			Gson gson = new Gson();
			String req = gson.toJson(this);
			if (req != null)
				req = req.replaceAll("(\"verifyCode\"\\s*:\\s*\")[^\"]+\"", "$1******\"");
			return req;
		} catch (Exception ex) {
			Exceptions.printStackTrace(ex);
			return "";
		}
	}
}
