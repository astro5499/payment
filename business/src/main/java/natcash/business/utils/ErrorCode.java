package natcash.business.utils;

import java.util.HashMap;
import java.util.Map;

public enum ErrorCode {
	SUCCESS(0, "MSG_SUCCESS", "Successfully"),
	  ERR_COMMON(1, "ERR_COMMON", "System error, please try again!"),
	  ERR_OLD_APP(1, "ERR_OLD_APP", "To continue using the service, please uninstall the app, then download and reinstall it from the App Store/Google Play"),
	  ERR_MISSING_PARAMETERS(2, "ERR_MISSING_PARAMETERS", "Missing parameters"),
	  ERR_PARAMETERS_INVALID(2, "ERR_PARAMETERS_INVALID", "Invalid data"),
	  ERR_TRANSACTION_EXPIRED(2, "ERR_TRANSACTION_EXPIRED", "Transaction expired"),
	  ERR_RESEND_OTP_OVER_LIMITED(5, "ERR_RESEND_OTP_OVER_LIMITED", "Cannot resend OTP, over limited"),
	  ERR_PIN_INVALID(2, "ERR_PIN_INVALID", "Pin invalid"),
	  ERR_OTP_INVALID(2, "ERR_OTP_INVALID", "OTP invalid"),
	  ERROR_CONFIG_FEE(2, "ERROR_CONFIG_FEE", "Transaction failed. Please try again!"),
	  CORE_TRANSACTION_INVALID(2, "CORE_TRANSACTION_INVALID", "Core transaction invalid! Please try again later!"),
	  CORE_WRONG_PIN(2, " CORE_WRONG_PIN", "PIN is not correct. Please check again."),
	  CORE_WRONG_PIN_SECOND(2, "CORE_WRONG_PIN_SECOND", "Your account is locked if you enter the wrong PIN again"),
	  ERR_OTP_ID_INVALID(3, "ERR_OTP_ID_INVALID", "OTP ID invalid"),
	  ERR_AMOUNT_INVALID(5, "ERR_AMOUNT_INVALID", "Amount is invalid"),
	  ERR_ACCOUNT_EXISTS(5, "ERR_ACCOUNT_EXISTS", "Account exists"),
	  ERR_LANGUAGE_NOT_EXISTS(5, "ERR_LANGUAGE_NOT_EXISTS", "Language is not exist"),
	  ERR_DEBIT_AMOUNT(5, "ERR_DEBIT_AMOUNT", "Amount can not be greater than debit amount"),
	  ERR_ACCOUNT_NOT_END_USER(2, "ERR_ACCOUNT_NOT_END_USER", "Your account is not End User, please check and try again."),
	  ERR_ACCOUNT_NOT_AGENT(2, "ERR_ACCOUNT_NOT_AGENT", "Your account is not Agent, please check and try again."),
	  ERR_OLDER_VERSION(2, "ERR_OLDER_VERSION", "Please update to the latest version"),
	  ERR_PAPER_NUMBER(1, "ERR_PAPER_NUMBER", "ID number is incorrect. Please try again!"),
	  ERR_RECEIVER_ACCOUNT_INVALID(2, "ERR_RECEIVER_ACCOUNT_INVALID", "Receiver's account number invalid"),
	  ERR_ACCOUNT_INVALID(2, "ERR_ACCOUNT_INVALID", "Account number invalid"),
	  ERR_ACCOUNT_FULL_WALLET(2, "ERR_ACCOUNT_FULL_WALLET", "Account has been ekyc successfully!"),
	  ERR_LOCATION_INVALID(2, "ERR_LOCATION_INVALID", "You and the agent are not in the same location. Please try again later."),
	  ERR_BTS_NOT_FOUND(-1, "ERR_BTS_NOT_FOUND", "The customer's BTS could not be found. Please try again later."),
	  ERR_BTS_USER_LOGIN_NOT_FOUND(-1, "ERR_BTS_USER_LOGIN_NOT_FOUND", "Your BTS could not be found. Please try again later."),
	  ERR_USER_LOCKED(2, "ERR_USER_LOCKED", "App User locked, not allow to use app"),
	  ERR_DEVICE_INVALID(5, "ERR_DEVICE_INVALID", "Device is invalid"),
	  ERR_ACCOUNT_NOT_EXISTS(2, "ERR_ACCOUNT_NOT_EXISTS", "Account not exists"),
	  ERR_LOGIN_INVALID_INFORMATION(2, "ERR_LOGIN_INVALID_INFORMATION", "Login information invalid, please check and try again! Your account will be locked after entering the wrong password 5 times."),
	  ERR_LOGIN_INVALID_NUMBER(2, "ERR_LOGIN_INVALID_INFORMATION_ACCOUNT", "Login information invalid, please check and try again!"),
	  ERR_ACCOUNT_CANCELLED(2, "ERR_ACCOUNT_CANCELLED", "Account cancelled"),
	  ERR_ACCOUNT_BLOCKED(2, "ERR_ACCOUNT_BLOCKED", "Account has been blocked"),
	  ERR_ACCOUNT_NOTAGENT(2, "ERR_ACCOUNT_NOTAGENT", "The phone number of the receiving agent is not valid!"),
	  ERR_CORE_TRANSACTION_TIMEOUT(2, "ERR_CORE_TRANSACTION_TIMEOUT", "Core transaction timeout"),
	  CORE_ACCOUNT_BLOCK_RESET_PIN(2, "CORE_ACCOUNT_BLOCK_RESET_PIN", "Core account block reset pin"),
	  CORE_ACCOUNT_BLOCK_CHANGE_SIM(2, "CORE_ACCOUNT_BLOCK_CHANGE_SIM", "Core account block reset pin"),
	  CORE_ACCOUNT_BLOCK_INVALID_PIN(2, "CORE_ACCOUNT_BLOCK_INVALID_PIN", "Core account block reset pin"),
	  CORE_ACCOUNT_NOT_ACTIVATED(2, "CORE_ACCOUNT_NOT_ACTIVATED", "Account not active"),
	  TYPE_NOT_FOUND(2, "TYPE_NOT_FOUND", "Type not found"),
	  NOT_ENOUGH_AGE(5, "NOT_ENOUGH_AGE", "Not enough age"),
	  ERR_QRCODE_INVALID(2, "ERR_QRCODE_INVALID", "Qr Code invalid"),
	  ERR_PHONE_NUMBER_USE_THIS_DATA_PLAN(5, "ERR_PHONE_NUMBER_USE_THIS_DATA_PLAN", "Sorry! This phone number is using this data package. Please check again!"),
	  ERR_TRANSACTION_NOT_CONFIG_FEE(5, "ERR_TRANSACTION_NOT_CONFIG_FEE", "Transaction is not configured fee! Please try again later!"),
	  ERR_DUPLICATE_PHONE_NUMBER(5, "ERR_DUPLICATE_PHONE_NUMBER", "The transferred account cannot match your phone number"),
	  ERR_GET_EXCHANGE_RATE_NOT_SUCCESS(5, "ERR_GET_EXCHANGE_RATE_NOT_SUCCESS", "Get exchange rate fail! Please try again later!"),
	  SUCCESS_EKYC(5, "SUCCESS_EKYC", "EKYC successful"),
	  ERROR_EKYC_INIT_DATA(8, "ERROR_EKYC_INIT_DATA", "Sorry, the server is busy. Please try again later"),
	  ERROR_AUTHENTICATION_ID_CARD(6, "ERROR_AUTHENTICATION_ID_CARD", "Image processing ERROR"),
	  ERROR_AUTHENTICATION_ID_CARD_WRONG_SIDE(7, "ERROR_AUTHENTICATION_ID_CARD_WRONG_SIDE", "Wrong side upload!"),
	  ERR_EKYC_AUTHENTICATION(9, "ERR_EKYC_AUTHENTICATION", "Authentication fail"),
	  ERROR_NW_VTCC(8, "ERROR_NW_VTCC", "Something went wrong, please try again in a few minutes!"),
	  ERROR_AUTHENTICATION_ID_CARD_SMALL(10, "ERROR_AUTHENTICATION_ID_CARD_SMALL", "Id card Image is too SMALL!"),
	  ERROR_AUTHENTICATION_ID_CARD_BLUR(11, "ERROR_AUTHENTICATION_ID_CARD_BLUR", "Id card Image is too BLUR!"),
	  ERROR_AUTHENTICATION_ID_CARD_BRIGHT(12, "ERROR_AUTHENTICATION_ID_CARD_BRIGHT", "Id card Image is too BRIGHT!"),
	  ERROR_AUTHENTICATION_ID_CARD_DARK(13, "ERROR_AUTHENTICATION_ID_CARD_DARK", "Id card Image is too DARK!"),
	  ERROR_AUTHENTICATION_ID_CARD_NO_ID(14, "ERROR_AUTHENTICATION_ID_CARD_NO_ID", "There is NOT ID Card in image!"),
	  ERROR_AUTHENTICATION_ID_CARD_TOO_MANY_ID(15, "ERROR_AUTHENTICATION_ID_CARD_TOO_MANY_ID", "There are TOO MANY ID Cards in image!"),
	  ERROR_AUTHENTICATION_WRONG_TYPE(16, "ERROR_AUTHENTICATION_WRONG_TYPE", "Wrong type of document upload!"),
	  ERROR_AUTHENTICATION_WRONG_TWO_DOCUMENT(16, "ERROR_AUTHENTICATION_WRONG_TWO_DOCUMENT", "The two photos are not of the same document!"),
	  ERROR_AUTHENTICATION_ID_CARD_OTHER_DEVICE(17, "ERROR_AUTHENTICATION_ID_CARD_OTHER_DEVICE", "The photo is invalid. Please take photo again from your device"),
	  ERROR_AUTHENTICATION_ID_CARD_IS_PRINT(18, "ERROR_AUTHENTICATION_ID_CARD_IS_PRINT", "Black and white photo of identity document!"),
	  ERROR_AUTHENTICATION_SELFIE_NOT_MATCH(19, "ERROR_AUTHENTICATION_SELFIE_NOT_MATCH", "The face image does not match the face image on the ID!"),
	  ERROR_AUTHENTICATION_ID_CARD_NOT_CLEAR(20, "ERROR_AUTHENTICATION_ID_CARD_NOT_CLEAR", "The image is not clear. Please try again!"),
	  ERROR_IMAGE_LIVE_NESS(21, "ERROR_IMAGE_LIVE_NESS", "Image liveliness verification failed"),
	  ERROR_IMAGE_LIVE_NESS_BLUR(24, "ERROR_IMAGE_LIVE_NESS_BLUR", "Image liveliness is too BLUR!"),
	  ERROR_IMAGE_LIVE_NESS_BRIGHT(25, "ERROR_IMAGE_LIVE_NESS_BRIGHT", "Image liveliness is too BRIGHT!"),
	  ERROR_IMAGE_LIVE_NESS_DARK(26, "ERROR_IMAGE_LIVE_NESS_DARK", "Image liveliness is too DARK!"),
	  ERROR_IMAGE_LIVE_NESS_NOT_IMAGE(27, "ERROR_IMAGE_LIVE_NESS_NOT_IMAGE", "Image liveliness is no face!"),
	  ERROR_IMAGE_LIVE_NESS_MANY_FACE(28, "ERROR_IMAGE_LIVE_NESS_MANY_FACE", "Image liveliness with many face!"),
	  ERROR_IMAGE_LIVE_NESS_SMALL(29, "ERROR_IMAGE_LIVE_NESS_SMALL", "Image liveliness smaller than specified size!"),
	  ERROR_IMAGE_LIVE_NOT_MAP(30, "ERROR_IMAGE_LIVE_NOT_MAP", "Image liveliness not map!"),
	  ERROR_IMAGE_LIVE_NOT_LIVE(30, "ERROR_IMAGE_LIVE_NOT_LIVE", "Image liveliness not selfie!"),
	  ERROR_IMAGE_DOCUMENT(22, "ERROR_IMAGE_DOCUMENT", "Document Image verification failed"),
	  ERROR_DUPLICATE_DOCUMENT(23, "ERROR_DUPLICATE_DOCUMENT", "The document you are doing to upgrade has been registered in another wallet account, please re-register the account with a different document"),
	  SUCCESS_GET_EKYC_TOKEN(1, "TOKEN_EKYC_SUCCESS", "Get eKYC token successfully"),
	  SUCCESS_AUTHENTICATION_FRONT_ID_CARD(2, "SUCCESS_AUTHENTICATION_FRONT_ID_CARD", "Id card front verification successful"),
	  SUCCESS_AUTHENTICATION_BACK_ID_CARD(3, "SUCCESS_AUTHENTICATION_BACK_ID_CARD", "Id card back verification successful"),
	  SUCCESS_AUTHENTICATION_ID_CARD(4, "SUCCESS_AUTHENTICATION_ID_CARD", "Id card verification successful"),
	  ERROR_OCR_D001_BIRTHDAY(34, "ERROR_OCR_D001_BIRTHDAY", "An error occurred during processing (Code: OCRD001-BIRTH_DAY). Please try again "),
	  ERROR_OCR_D002_EXPIRED_DATE(35, "ERROR_OCR_D002_EXPIRED_DATE", "An error occurred during processing (Code: OCRD002-EXPIRED_DATE). Please try again "),
	  ERROR_OCR_D003_ISSUE_DATE(36, "ERROR_OCR_D003_ISSUE_DATE", "An error occurred during processing (Code: OCRD003-ISSUE_DATE). Please try again "),
	  ERR_INVITED_PHONE_GREATER(5, "ERR_INVITED_PHONE_GREATER", "The number of invited phone numbers is greater than 10."),
	  ERR_INVITED_WALLET_NUMBER(5, "ERR_INVITED_WALLET_NUMBER", "This phone number is using wallet"),
	  ERR_REVERT_TRANSACTION(5, "ERR_REVERT_TRANSACTION", "Transaction failed. Refund successful"),
	  CAN_NOT_DELETE_FILE(2, "CAN_NOT_DELETE_FILE", "Can not delete file image"),
	  FUNCTION_LOCK(5, "FUNCTION_LOCK", "The function is temporarily locked. Please come back later!"),
	  ERR_WRONG_INFORMATION_INPUT(1, "ERR_WRONG_INFORMATION_INPUT", "Your private information is incorrect. Please try again."),
	  ERR_ACCOUNT_NOT_EXITS(2, "ERR_ACCOUNT_NOT_EXITS", "The phone number does not register Ewallet service."),
	  ERR_WRONG_PHONE(3, "ERR_WRONG_PHONE", "Invalid phone number"),
	  ERR_WRONG_PHONE_NOT_ACTIVE(4, "ERR_WRONG_PHONE_NOT_ACTIVE", "The phone number is not active. Please active to use Ewallet service."),
	  ERR_WRONG_PHONE_NOT_EKYC(4, "ERR_WRONG_PHONE_NOT_EKYC", "The phone number is not ekyc. Please try again later."),
	  ERR_PHONE_NOT_HAVE_IMAGE(4, "ERR_PHONE_NOT_HAVE_IMAGE", "The phone number is not Ekyc. Please Ekyc to use Ewallet service."),
	  ERR_LOGIN_ACCOUNT_RESET_PIN(2, "ERR_LOGIN_ACCOUNT_RESET_PIN", "Please dial *202# to setup the new PIN."),
	  ERR_PHONE_ALREADY_CANCELING(2, "ERR_PHONE_ALREADY_CANCELING", "Your request is being processed"),
	  ERR_PARTNER_REQUEST_ORDER_ALREADY_EXIST(2, "ERR_PARTNER_REQUEST_ORDER_ALREADY_EXIST", "Request id or order number is already existed"),
	  ERR_PARTNER_IP_NOT_VALID(2, "ERR_PARTNER_IP_NOT_VALID", "Your ip is not valid"),
	  ERR_SYSTEM_BUSY(2, "ERR_SYSTEM_BUSY", "System is busy, please contact admin"),
	  ERR_PAYMENT_NOT_FOUND(2, "ERR_PAYMENT_NOT_FOUND", "Cannot find any payment with your order id you provided"),
	ERR_PAYMENT_EXPIRED(2, "ERR_PAYMENT_EXPIRED", "Payment expired");

	  private final int status;
	  
	  private final String code;
	  
	  private final String message;
	  
	  private static final Map<Integer, ErrorCode> mStatusValues;
	  
	  private static final Map<String, ErrorCode> mCodeValues;
	  
	  static {
	    mStatusValues = new HashMap<>();
	    mCodeValues = new HashMap<>();
	    for (ErrorCode ec : values()) {
	      mStatusValues.put(Integer.valueOf(ec.status()), ec);
	      mCodeValues.put(ec.code(), ec);
	    } 
	  }
	  
	  ErrorCode(int status, String code, String message) {
	    this.status = status;
	    this.code = code;
	    this.message = message;
	  }
	  
	  public int status() {
	    return this.status;
	  }
	  
	  public String code() {
	    return this.code;
	  }
	  
	  public String message() {
	    return this.message;
	  }
	  
	  public boolean is(int status) {
	    return (this.status == status);
	  }
	  
	  public static ErrorCode get(int status) {
	    return mStatusValues.get(Integer.valueOf(status));
	  }
	  
	  public static ErrorCode get(String code) {
	    return mCodeValues.get(code);
	  }
	}

