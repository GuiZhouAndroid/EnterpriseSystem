package io.dtchain.utils.json;

import lombok.Getter;

/**
 * created by on 2021/10/10
 * 描述：响应码枚举，对应HTTP状态码
 *
 * @author ZSAndroid
 * @create 2021-10-10-18:17
 */
@Getter
public enum ResultCode {
    //用户登录
    LOGIN_SUCCESS(200,"登录成功"),
    LOGIN_ERROR(200,"登录失败"),
    LOGIN_ERROR_NOT_ACCOUNT(200,"登录失败，账户不存在"),
    LOGIN_ERROR_USERNAME_PASSWORD_MISMATCH(200,"，登录失败，用户名和密码不匹配"),
    LOGIN_ERROR_TEL_NUMBER_MISMATCH(200,"，登录失败，手机号和密码不匹配"),
    LOGIN_ERROR_STU_NO_MISMATCH(200,"，登录失败，学号和密码不匹配"),
    LOGIN_ERROR_ID_CARD_MISMATCH(200,"，登录失败，身份证号和密码不匹配"),
    LOGIN_ERROR_QQ_EMAIL_MISMATCH(200,"，登录失败，邮箱和密码不匹配"),
    //用户注册
    REGISTER_SUCCESS(200,"注册成功"),
    REGISTER_SUCCESS_EMAIL_SEND(200,"注册成功，邮箱已发送"),
    REGISTER_ERROR(200,"注册失败"),
    REGISTER_ERROR_USERNAME_ALREADY_EXIST(200,"注册失败，此用户名已被注册"),
    REGISTER_ERROR_ID_CARD_ALREADY_EXIST(200,"注册失败，此身份证已被注册"),
    REGISTER_ERROR_STU_NO_ALREADY_EXIST(200,"注册失败，此学号已被注册"),
    REGISTER_ERROR_TEL_ALREADY_EXIST(200,"注册失败，此手机号已被注册"),
    REGISTER_ERROR_EMAIL_ALREADY_EXIST(200,"注册失败，此邮箱已被注册"),
    //管理员添加角色
    ROLE_INSERT_SUCCESS(200,"角色添加成功"),
    ROLE_INSERT_ERROR(200,"角色添加失败"),
    ROLE_INSERT_ERROR_ALREADY_EXIST(200,"角色添加失败，此角色已被注册"),
    //管理员添加用户角色
    USER_ROLE_INSERT_SUCCESS(200,"用户角色添加成功"),
    USER_ROLE_INSERT_ERROR(200,"用户角色添加失败"),
    //管理员更新用户角色
    USER_ROLE_UPDATE_SUCCESS(200,"用户角色更新成功"),
    USER_ROLE_UPDATE_ERROR(200,"用户角色更新失败"),
    //权限注册
    PERMISSION_INSERT_SUCCESS(200,"权限添加成功"),
    PERMISSION_INSERT_ERROR(200,"权限添加失败"),
    PERMISSION_INSERT_ERROR_ALREADY_EXIST(200,"权限添加失败，此权限已被注册"),
    //管理员踢人下线
    KICKOFF_LINE_SUCCESS(200,"踢人下线成功"),
    KICKOFF_LINE_ERROR(200,"踢人下线失败，此用户名不存在"),
    SUPER_ADMIN_ACCOUNT_BANNED_SUCCESS(200,"账户封禁成功"),
    THIS_ACCOUNT_ALREADY_BANNED(200,"此账户已封禁"),
    THIS_ACCOUNT_NOT_ALREADY_BANNED(200,"此账户未封禁"),
    QUERY_ACCOUNT_BANNED_SUCCESS(200,"此账户处于封禁状态"),
    THIS_ACCOUNT_ALREADY_UNTIE_BANNED(200,"此账户已解除封禁"),
    //登录状态
    NOW_ACCOUNT_ALREADY_LOGIN(200,"当前账户已登录"),
    NOW_ALREADY_LOGIN_ACCOUNT_DO_LOGOUT_SUCCESS(200,"当前登录账户注销成功"),
    NOW_ALREADY_LOGIN_ACCOUNT_DO_LOGOUT_ERROR(200,"注销失败，未登录"),
    NOW_ALREADY_LOGIN_ACCOUNT_QUERY__ERROR(200,"查询失败，未登录"),
    //FAIL(400, "失败")
    BAD_REQUEST(400, "错误请求，禁止访问"),
    UNAUTHORIZED(401, "验证失败，禁止访问"),//未认证
    ACCOUNT_EXCEPTION(401, "账户异常，禁止访问"),//未认证
    ROLE_NONENTITY (403, "角色不存在，禁止访问"),
    PERMISSION_NONENTITY (403, "权限不存在，禁止访问"),
    NOT_FOUND(404, "接口不存在，禁止访问"),//接口不存在
    INTERNAL_SERVER_ERROR(500, "系统繁忙"),//服务器内部错误
    METHOD_NOT_ALLOWED(405,"方法不被允许"),
    /** 标准成功 */
    SUCCESS(200, "success"),
    /** 标准失败 */
    ERROR(400, "error"),
    /*参数错误:1001-1999*/
    PARAMS_IS_INVALID(1001, "参数无效"),
    PARAMS_IS_BLANK(1002, "参数为空");
    /*用户错误2001-2999*/

    private Integer code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
