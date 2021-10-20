package io.dtchain.service;

import io.dtchain.entity.RecordTable;
import io.dtchain.utils.Result;

/**
 * created by on 2021/10/19
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-10-19-23:23
 */

public interface RecordService {
    /**
     * 增加打卡数据
     * @return
     */
    public Result<RecordTable> addPunchInfo(RecordTable recordTable);
}
