package com.example.demo.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data // tu dong tao ra getter setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RedisHash("RedisHas")
public class RedisToken {
    @Id
    String id;

    @TimeToLive(unit = TimeUnit.DAYS)
    Date expiryTime; // thoi gian het han
}
