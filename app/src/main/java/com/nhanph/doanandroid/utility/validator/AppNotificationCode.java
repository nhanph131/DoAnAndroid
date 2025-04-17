package com.nhanph.doanandroid.utility.validator;

public enum AppNotificationCode {
    EMPTY_USERNAME(0, "Tên đăng nhập không được để trống"),
    EMPTY_PASSWORD(1, "Mật khẩu không được để trống"),
    LOGIN_SUCCESS(2, "Đăng nhập thành công"),
    LOGIN_FAILED(3, "Đăng nhập thất bại"),
    USER_NOT_FOUND(4, "Người dùng không tồn tại"),
    WRONG_PASSWORD(5, "Sai mật khẩu"),
    SERVER_ERROR(6, "Lỗi server"),
    INVALID_USERNAME(7, "Tên đăng nhập phải lớn hơn 6 ký tự và không có dấu cách"),
    INVALID_PASSWORD(8, "Mật khẩu phải lớn hơn 6 ký tự và không có dấu cách"),
    INVALID_NAME(9, "Tên không hợp lệ"),
    EMPTY_NAME(10, "Tên không được để trống"),
    EMPTY_CONFIRM_PASSWORD(11, "Xác nhận mật khẩu không được để trống"),
    PASSWORD_NOT_MATCH(12, "Mật khẩu không khớp"),
    USER_EXISTED(13, "Người dùng đã tồn tại"),

    REGISTER_SUCCESS(1000, "Đăng ký thành công"),
    REGISTER_FAILED(1001, "Đăng ký thất bại"),

    _SUCCESS(9998, "Thành công"),
    UNKNOWN_ERROR(9999, "Lỗi không xác định");

    private final int code;
    private final String message;

    AppNotificationCode(int code, String message) {
        this.code = code;
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
    public int getCode() {
        return code;
    }
}
