package io.dtchain.dao;

import io.dtchain.entity.WorkTimeBean;
import io.dtchain.utils.Result;
import org.apache.ibatis.annotations.Mapper;

/**
 * created by on 2021/10/20
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-10-20-20:27
 */
@Mapper
public interface WorkTimeDao {
    public int addPunchInfo(WorkTimeBean workTime);
}
