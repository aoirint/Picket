package com.kanomiya.picket.data;

import java.util.Map;

public interface IDataSerializer<T>
{
    Map<String, Object> serialize(T obj);

    T deserialize(Map<String, Object> map);


}
