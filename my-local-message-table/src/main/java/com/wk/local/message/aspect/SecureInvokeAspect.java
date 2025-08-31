package com.wk.local.message.aspect;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.wk.local.message.annotation.LocalMessageTable;
//import com.wk.local.message.annotation.SecureInvoke;
import com.wk.local.message.domain.dto.SecureInvokeDTO;
import com.wk.local.message.domain.entity.SecureInvokeRecord;
import com.wk.local.message.service.SecureInvokeHolder;
import com.wk.local.message.service.SecureInvokeService;
import com.wk.local.message.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description: 安全执行切面
 * Author: <a href="https://github.com/zongzibinbin">abin</a>
 * Date: 2023-04-20
 */
@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)//确保最先执行
@Component
public class SecureInvokeAspect {
    @Autowired
    private SecureInvokeService secureInvokeService;

    @Around("@annotation(localMessageTable)")
    public Object around(ProceedingJoinPoint joinPoint, LocalMessageTable localMessageTable) throws Throwable {
        boolean async = localMessageTable.async();
        boolean inTransaction = TransactionSynchronizationManager.isActualTransactionActive();
        //非事务状态，直接执行，不做任何保证。
        if (SecureInvokeHolder.isInvoking() || !inTransaction) {
            return joinPoint.proceed();
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        List<String> parameters = Stream.of(method.getParameterTypes()).map(Class::getName).collect(Collectors.toList());
        SecureInvokeDTO dto = SecureInvokeDTO.builder()
                .args(JsonUtils.toStr(joinPoint.getArgs()))
                .className(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(JsonUtils.toStr(parameters))
                .build();
        // DateTime dateTime = DateUtil.offsetMinute(new Date(), (int) SecureInvokeService.RETRY_INTERVAL_MINUTES);
        LocalDateTime dataTime = LocalDateTime.now();
        dataTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //往后一定两分钟
        Duration duration = Duration.ofMinutes(2);
        LocalDateTime newDataTime = dataTime.plus(duration);
        // .nextRetryTime(DateUtil.offsetMinute(new Date(), (int) SecureInvokeService.RETRY_INTERVAL_MINUTES))
        SecureInvokeRecord record = SecureInvokeRecord.builder()
                .secureInvokeDTO(dto)
                .maxRetryTimes(localMessageTable.maxRetryTimes())
                .nextRetryTime(newDataTime)
                .build();
        secureInvokeService.invoke(record, async);
        //继续执行
        return null;
    }
}
