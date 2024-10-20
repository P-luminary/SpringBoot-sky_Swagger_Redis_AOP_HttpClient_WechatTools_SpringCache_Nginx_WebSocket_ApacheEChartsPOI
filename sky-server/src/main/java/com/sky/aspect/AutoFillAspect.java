package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充处理逻辑
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    // 拦截类 + 注解的东西
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    // 前置通知，在通知中进行公共字段的赋值
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始公共字段自动填充...");

        // 获取当前被拦截的方法上的数据库操作类型(Update/Insert)
        MethodSignature signature = (MethodSignature)joinPoint.getSignature(); //方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); //获得方法上的注解对象
        OperationType operationType = autoFill.value();//获得数据库操作类型

        // 获取当当前被拦截的方法的参数--实体对象 (Employee employee)
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) { //没有参数不执行
            return;
        }
        Object entity = args[0]; //获得第一个

        //准备赋值
        LocalDateTime now = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();
        Object obj = args[0];
        try {
            //如果当前mapper执行新增操作
            if(operationType == OperationType.INSERT){
                //公共字段都插入：更新人，更新时间，创建人，创建时间
                Method setCreateTimeMethod = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                Method setCreateUserMethod = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setUpdateTimeMethod = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setUpdateUserMethod = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                setCreateTimeMethod.invoke(obj,now);
                setCreateUserMethod.invoke(obj,id);
                setUpdateTimeMethod.invoke(obj,now);
                setUpdateUserMethod.invoke(obj,id);
            }else {
                //公共字段：更新人，更新时间
                Method setUpdateTimeMethod = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method setUpdateUserMethod = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);
                setUpdateTimeMethod.invoke(obj,now);
                setUpdateUserMethod.invoke(obj,BaseContext.getCurrentId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
