package kr.co.galloping.football_manager.global.error;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	INVALID_INPUT_VALUE(400, "Invalid Input Value"),
	ENTITY_NOT_FOUND(400, "Entity Not Found"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	HANDLE_ACCESS_DENIED(403, "Access Denied"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

	/* JWT */
	ACCESS_TOKEN_NOT_FOUND(404, "Access Token Not Found"),
	TOKEN_EXPIRED(400, "Token Expired"),
	INVALID_TOKEN(400, "Invalid Token"),
	TOKEN_SIGNATURE_INVALID(400, "Token Signature Invalid"),
	TOKEN_MALFORMED(400, "Token Malformed"),
	TOKEN_UNSUPPORTED(400, "Token Unsupported"),
	TOKEN_ILLEGAL_ARGUMENT(400, "Token Illegal Argument"),

	/* USER */
	USER_NOT_FOUND(404, "User Not Found"),
	EMAIL_EXIST(409, "Email Exists"),
	EMAIL_NOT_VERIFIED(401, "Email Not Verified"),
	PASSWORD_MISMATCH(400, "Password Mismatch"),
	NICKNAME_EXISTS(409, "Nickname Exists"),
	PASSWORD_CANNOT_CHANGE(403, "Cannot Use The Same Password"),
	UNAUTHORIZED(401, "Unauthorized"), // 인증이 필요한 상태
	FORBIDDEN(403, "Forbidden"), // 인증은 되었으나 권한이 없는 상태
	DUPLICATE_EMAIL_PROVIDER(400, "Duplicate Email Provider");

	private int status;
	private String message;

	ExceptionCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}

