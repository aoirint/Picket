package com.kanomiya.picket.util;

import java.util.Map;

public interface IDataSerializer<T>
{
    Map<String, Object> serialize(T obj);

    T deserialize(Map<String, Object> map);


}
