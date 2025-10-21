package natcash.business.service;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;

import natcash.business.restful.models.Transaction;

@Service
public class InfoCacheService {
    private final Cache cache;

    public InfoCacheService(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("msisdnCache");
    }

    // Lưu hoặc ghi đè
    public <T> void saveInfo(String key, T data) {
        cache.put(key, data);
    }

    // Lấy ra theo key (trả về đúng kiểu mình muốn)
    public <T> T getInfo(String key, Class<T> clazz) {
        return cache.get(key, clazz);
    }
}