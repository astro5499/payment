package natcash.business.service;

public interface ChannelWhitelistService {
    boolean isValidIpAddress(String account, String ipAddress);
}
