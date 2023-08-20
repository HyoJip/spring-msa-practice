package me.bbbic.common.connect;

import lombok.Builder;

import java.util.List;
import java.util.Objects;


public record Schema(
  String type,
  List<Field> fields,
  boolean optional,
  String name
) {

  @Builder
  public Schema(String type, List<Field> fields, boolean optional, String name) {
    this.type = Objects.toString(type, "struct");
    this.fields = fields;
    this.optional = optional;
    this.name = name;
  }
}
