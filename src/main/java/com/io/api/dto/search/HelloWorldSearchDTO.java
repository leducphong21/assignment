package com.io.api.dto.search;

import java.time.LocalDateTime;

public class HelloWorldSearchDTO {

  private String name;
  private Integer age;
  private String address;
  private LocalDateTime createOn;
  private LocalDateTime modifyOn;

  public HelloWorldSearchDTO() {}

  public HelloWorldSearchDTO(String name, Integer age, String address) {
    this.name = name;
    this.age = age;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public LocalDateTime getCreateOn() {
    return createOn;
  }

  public void setCreateOn(LocalDateTime createOn) {
    this.createOn = createOn;
  }

  public LocalDateTime getModifyOn() {
    return modifyOn;
  }

  public void setModifyOn(LocalDateTime modifyOn) {
    this.modifyOn = modifyOn;
  }

}
