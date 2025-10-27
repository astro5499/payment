package natcash.business.service.impl;

import lombok.RequiredArgsConstructor;
import natcash.business.entity.ChannelWhitelist;
import natcash.business.repository.ChannelWhitelistRepository;
import natcash.business.service.ChannelWhitelistService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelWhitelistServiceImpl implements ChannelWhitelistService {

    private final ChannelWhitelistRepository repository;

    @Override
    public boolean isValidIpAddress(String account, String ipAddress) {
        Optional<ChannelWhitelist> accountWhitelistOpt = repository.findByAccount(account);

        if (accountWhitelistOpt.isPresent()) {
            ChannelWhitelist channelWhitelist = accountWhitelistOpt.get();
            List<String> ipAddresses = Arrays.asList(channelWhitelist.getIp().split(";"));
            return ipAddresses.contains(ipAddress);
        }

        return false;
    }
}
