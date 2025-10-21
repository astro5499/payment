package natcash.business.restful.models;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
	private String resultCode;
	private String resultMessage;
	private T result;
	private Long rowCount;
	private List<T> results;
	
	public Response() {
		super();
	}
	public Response(T result) {
		super();
		this.result = result;
	}
	
	public Response(String resultCode, String resultMessage, List<T> results) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.results = results;
	}
	public Response(String resultCode, String resultMessage, T result) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.result = result;
	}

	public Response(String resultCode, String resultMessage) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}
	public Response(Long rowCount, List<T> results) {
		super();
		this.rowCount = rowCount;
		this.results = results;
	}

	public Response(String resultCode, String resultMessage, Long rowCount, List<T> results) {
		super();
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.rowCount = rowCount;
		this.results = results;
	}

	public String getResultCode() {
		return resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public T getResult() {
		return result;
	}
	public Long getRowCount() {
		return rowCount;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
}

