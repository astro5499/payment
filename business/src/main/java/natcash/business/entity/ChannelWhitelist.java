package natcash.business.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "channel_whitelist")
public class ChannelWhitelist {

    @Id
    @Column(name = "account", nullable = false, length = 255)
    private String account;

    @Column(name = "private_key", nullable = false, length = 255)
    private String privateKey;

    @Column(name = "ip", length = 255)
    private String ip;
}

