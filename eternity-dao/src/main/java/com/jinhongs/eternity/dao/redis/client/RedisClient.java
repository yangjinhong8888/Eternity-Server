package com.jinhongs.eternity.dao.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis客户端工具类，提供对Redis各种数据类型的操作方法
 */
@Component
public class RedisClient {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisClient(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ======================== 通用操作 ========================

    /**
     * 为指定的key设置过期时间
     *
     * @param key  键
     * @param time 过期时间(秒)
     * @return 是否设置成功
     */
    public boolean setExpire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取指定key的过期时间
     *
     * @param key 键
     * @return 过期时间(秒)，-1表示永不过期，-2表示key不存在
     */
    public long getExpireTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断指定key是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 移除指定key的过期时间，使其变为永久有效
     *
     * @param key 键
     * @return 是否移除成功
     */
    public boolean removeExpire(String key) {
        return Boolean.TRUE.equals(redisTemplate.boundValueOps(key).persist());
    }

    // ======================== String类型操作 ========================

    /**
     * 根据key获取String类型的值
     *
     * @param key 键
     * @return 值，如果key不存在返回null
     */
    public Object getStringValue(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置String类型的键值对（永久有效）
     *
     * @param key   键
     * @param value 值
     */
    public void setStringValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置String类型的键值对并指定过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间(秒)，-1表示永久有效
     */
    public void setStringValueWithExpire(String key, String value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 批量设置String类型的键值对（重复的键会覆盖原有值）
     *
     * @param keyAndValue 键值对Map
     */
    public void batchSetStringValues(Map<String, String> keyAndValue) {
        redisTemplate.opsForValue().multiSet(keyAndValue);
    }

    /**
     * 批量设置String类型的键值对（仅当所有键都不存在时才设置）
     *
     * @param keyAndValue 键值对Map
     */
    public void batchSetStringValuesIfAbsent(Map<String, String> keyAndValue) {
        redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);
    }

    // ======================== Set类型操作 ========================

    /**
     * 向Set集合中添加元素
     *
     * @param key   键
     * @param value 要添加的元素
     */
    public void addToSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取Set集合中的所有元素
     *
     * @param key 键
     * @return 元素集合
     */
    public Set<Object> getSetMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取Set集合中指定数量的元素（不移除元素）
     *
     * @param key   键
     * @param count 获取元素数量
     * @return 元素列表
     */
    public List<Object> getRandomSetMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取Set集合中的一个元素（不移除元素）
     *
     * @param key 键
     * @return 元素
     */
    public Object getRandomSetMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 弹出Set集合中的一个随机元素（会移除元素）
     *
     * @param key 键
     * @return 元素
     */
    public Object popFromSet(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 获取Set集合中元素的数量
     *
     * @param key 键
     * @return 元素数量
     */
    public long getSetSize(String key) {
        Long size = redisTemplate.opsForSet().size(key);
        return size != null ? size : 0L;
    }

    /**
     * 判断元素是否存在于Set集合中
     *
     * @param key   键
     * @param value 要检查的元素
     * @return true 存在，false 不存在
     */
    public boolean isMemberOfSet(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * 检查给定元素是否在Set集合中
     *
     * @param key 键
     * @param obj 要检查的元素对象
     * @return 是否存在
     */
    public boolean isSetMember(String key, Object obj) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, obj));
    }

    /**
     * 将元素从源Set集合移动到目标Set集合
     *
     * @param key     源Set键
     * @param value   要移动的元素
     * @param destKey 目标Set键
     * @return 是否移动成功
     */
    public boolean moveSetMember(String key, String value, String destKey) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().move(key, value, destKey));
    }

    /**
     * 批量移除Set集合中的元素
     *
     * @param key    键
     * @param values 要移除的元素数组
     */
    public void removeSetMembers(String key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 获取两个Set集合的差集
     *
     * @param key     源Set键
     * @param destKey 比较Set键
     * @return 差值集合
     */
    public Set<Object> getSetDifference(String key, String destKey) {
        return redisTemplate.opsForSet().difference(key, destKey);
    }

    // ======================== Hash类型操作 ========================

    /**
     * 向Hash中批量添加键值对
     *
     * @param key 键
     * @param map 键值对Map
     */
    public void putHashValues(String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取Hash中所有的键值对
     *
     * @param key 键
     * @return 键值对Map
     */
    public Map<Object, Object> getHashEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 判断Hash中是否存在指定的hashKey
     *
     * @param key     键
     * @param hashKey hash键
     * @return 是否存在
     */
    public boolean hasHashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取Hash中指定hashKey的String值
     *
     * @param key  键
     * @param key2 hash键
     * @return 值
     */
    public String getHashStringValue(String key, String key2) {
        Object obj = redisTemplate.opsForHash().get(key, key2);
        return obj == null ? null : obj.toString();
    }

    /**
     * 获取Hash中指定hashKey的Integer值
     *
     * @param key  键
     * @param key2 hash键
     * @return 值
     */
    public Integer getHashIntValue(String key, String key2) {
        Object obj = redisTemplate.opsForHash().get(key, key2);
        return obj == null ? null : Integer.valueOf(obj.toString());
    }

    /**
     * 删除Hash中的指定hashKey
     *
     * @param key      键
     * @param hashKeys 要删除的hash键数组
     * @return 删除成功的数量
     */
    public Long deleteHashKeys(String key, String... hashKeys) {
        return redisTemplate.opsForHash().delete(key, (Object[]) hashKeys);
    }

    /**
     * 获取Hash中所有的hashKey
     *
     * @param key 键
     * @return hashKey集合
     */
    public Set<Object> getHashKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取Hash中键值对的数量
     *
     * @param key 键
     * @return 数量
     */
    public Long getHashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    // ======================== List类型操作 ========================

    /**
     * 向List左侧（头部）添加元素
     *
     * @param key   键
     * @param value 值
     */
    public void pushToLeftOfList(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取List指定索引位置的元素
     *
     * @param key   键
     * @param index 索引（从0开始）
     * @return 元素
     */
    public Object getListElementAtIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取List指定范围的元素
     *
     * @param key   键
     * @param start 开始索引（包含）
     * @param end   结束索引（包含）
     * @return 元素列表
     */
    public List<Object> getListRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 在List中指定元素前插入新元素
     *
     * @param key   键
     * @param pivot 参照元素
     * @param value 要插入的元素
     */
    public void pushToLeftOfListBeforeValue(String key, String pivot, String value) {
        redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 向List左侧（头部）批量添加元素
     *
     * @param key    键
     * @param values 值数组
     */
    public void pushAllToLeftOfList(String key, String... values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 向List右侧（尾部）添加元素
     *
     * @param key   键
     * @param value 值
     */
    public void pushToRightOfList(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向List右侧（尾部）批量添加元素
     *
     * @param key    键
     * @param values 值数组
     */
    public void pushAllToRightOfList(String key, String... values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 向已存在的List右侧（尾部）添加元素
     *
     * @param key   键
     * @param value 值
     */
    public void pushToRightOfListIfExists(String key, Object value) {
        redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 获取List的长度
     *
     * @param key 键
     * @return 长度
     */
    public long getListLength(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return size != null ? size : 0L;
    }

    /**
     * 从List左侧（头部）弹出元素
     *
     * @param key 键
     * @return 元素
     */
    public Object popFromLeftOfList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 从List左侧（头部）弹出元素（带超时等待）
     *
     * @param key     键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return 元素
     */
    public Object popFromLeftOfListWithTimeout(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 从List右侧（尾部）弹出元素
     *
     * @param key 键
     * @return 元素
     */
    public Object popFromRightOfList(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 从List右侧（尾部）弹出元素（带超时等待）
     *
     * @param key     键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return 元素
     */
    public Object popFromRightOfListWithTimeout(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }
}
