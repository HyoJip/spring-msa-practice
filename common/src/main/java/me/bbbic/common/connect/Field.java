package me.bbbic.common.connect;

import lombok.AllArgsConstructor;

public record Field(
  String type,
  boolean optional,
  String field
  ) {

  public static Field of(Type type, boolean optional, String field) {
    return new Field(type.type, optional, field);
  }

  public static Field of(Type type, String field) {
    return Field.of(type, true, field);
  }

  @AllArgsConstructor
  public enum Type {
    STRING("string"),
    INT("int32");

    private String type;
  }
}
