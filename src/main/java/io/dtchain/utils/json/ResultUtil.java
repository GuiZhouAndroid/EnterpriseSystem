package io.dtchain.utils.json;

/**
 * created by on 2021/10/10
 * 描述：响应结果返回封装
 *
 * @author ZSAndroid
 * @create 2021-10-10-18:02
 */
public class ResultUtil {

    // 返回 200 + success
    public static JsonResult successSate(){
        return new JsonResult().setJsonResult(ResultCode.SUCCESS);
    }

    // 返回 200 + success + date
    public static JsonResult successSate(Object date){
        return new JsonResult().setJsonResult(ResultCode.SUCCESS,date);
    }

    // 自定义成功返回参数
    public static JsonResult successSate(ResultCode resultCode,Object date){
        return new JsonResult().setJsonResult(resultCode,date);
    }

    // 自定义失败返回参数，无数据返回
    public static JsonResult failure(ResultCode resultCode) {
        return new JsonResult().setJsonResult(resultCode);
    }

    // 自定义失败返回参数，有数据返回
    public static JsonResult failure(ResultCode resultCode, Object data) {
        return new JsonResult().setJsonResult(resultCode, data);
    }
}
