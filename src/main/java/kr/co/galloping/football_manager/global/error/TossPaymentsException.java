package kr.co.galloping.football_manager.global.error;

public class TossPaymentsException extends RuntimeException {
	private final String code;

	public TossPaymentsException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
