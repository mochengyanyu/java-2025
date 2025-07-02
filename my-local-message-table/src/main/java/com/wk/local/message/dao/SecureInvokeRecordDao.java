package com.wk.local.message.dao;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wk.local.message.domain.entity.SecureInvokeRecord;
import com.wk.local.message.mapper.SecureInvokeRecordMapper;
import com.wk.local.message.service.SecureInvokeService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-08-06
 */
@Component
public class SecureInvokeRecordDao extends ServiceImpl<SecureInvokeRecordMapper, SecureInvokeRecord> {

    public List<SecureInvokeRecord> getWaitRetryRecords() {
        // Date now = new Date();
        LocalDateTime now = LocalDateTime.now();
        //查2分钟前的失败数据。避免刚入库的数据被查出来
        // DateTime afterTime = DateUtil.offsetMinute(now, -(int) SecureInvokeService.RETRY_INTERVAL_MINUTES);
        // 查2分钟前的失败数据。避免刚入库的数据被查出来
        LocalDateTime afterTime = now.minusMinutes(SecureInvokeService.RETRY_INTERVAL_MINUTES_INT);
        return lambdaQuery()
                .eq(SecureInvokeRecord::getStatus, SecureInvokeRecord.STATUS_WAIT)
                .lt(SecureInvokeRecord::getNextRetryTime, LocalDateTime.now())
                .lt(SecureInvokeRecord::getCreateTime, afterTime)
                .list();
    }
}
