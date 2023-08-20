package me.bbbic.common.connect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class KafkaConnectDto {
  private final Schema schema;
  private final Payload payload;

}
