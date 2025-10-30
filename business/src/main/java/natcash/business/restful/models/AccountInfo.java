package natcash.business.restful.models;

import natcash.business.utils.CurrencyCode;

public class AccountInfo {
	private String accountId;

	private String accountNumber;

	private String accountName;

	private String accountCurrency = CurrencyCode.HTG.code();

	private String pan;

	private Integer accountStatus;

	private String carriedAccountId;

	private String accountCode;

	private String roleId;

	public AccountInfo() {
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountId() {
		return this.accountId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountCurrency() {
		return this.accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	public String getPan() {
		return this.pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public Integer getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getCarriedAccountId() {
		return this.carriedAccountId;
	}

	public void setCarriedAccountId(String carriedAccountId) {
		this.carriedAccountId = carriedAccountId;
	}

	@Override
	public String toString() {
		return "AccountInfo [accountId=" + accountId + ", accountNumber=" + accountNumber + ", accountName="
				+ accountName + ", accountCurrency=" + accountCurrency + ", pan=" + pan + ", accountStatus="
				+ accountStatus + ", carriedAccountId=" + carriedAccountId + ", accountCode=" + accountCode
				+ ", roleId=" + roleId + "]";
	}
	
}
