package io.dtchain.service;

import io.dtchain.entity.WorkTimeBean;
import io.dtchain.utils.Result;

/**
 * created by on 2021/10/20
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-10-20-20:24
 */

public interface WorkTimeService {
    /**
     * 增加打卡数据
     * @return
     */
    public int addPunchInfo(WorkTimeBean workTime);
}
