package io.dtchain.serviceImpl;

import io.dtchain.dao.WorkTimeDao;
import io.dtchain.entity.WorkTimeBean;
import io.dtchain.service.WorkTimeService;
import io.dtchain.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by on 2021/10/20
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-10-20-20:25
 */
@Service("WorkTimeService")
public class WorkTimeServiceImpl implements WorkTimeService {

    @Autowired
    WorkTimeDao workTimeDao;
    @Override
    public int addPunchInfo(WorkTimeBean workTime) {
        return workTimeDao.addPunchInfo(workTime);
    }
}
