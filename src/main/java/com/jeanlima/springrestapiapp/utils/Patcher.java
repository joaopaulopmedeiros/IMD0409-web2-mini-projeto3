package com.jeanlima.springrestapiapp.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class Patcher<T> 
{
    public void patch(T object, Map<String, Object> fields) 
    {
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(object.getClass(), key);
            if (field != null) 
            {
                field.setAccessible(true);
                Object finalValue = convertValue(field.getType(), value);
                ReflectionUtils.setField(field, object, finalValue);
            }
        });
    }

    private Object convertValue(Class<?> targetType, Object value) {
        if (targetType == BigDecimal.class && value instanceof Double) 
        {
            return BigDecimal.valueOf((Double) value);
        }
        return value;
    }
}
