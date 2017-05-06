package ericwush.integration;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Objects;

@JsonRootName(value = "test")
public class TestObject {
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TestObject that = (TestObject) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
