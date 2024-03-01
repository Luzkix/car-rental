package org.luzar.carrental.globalexceptionhandling.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDto {
  private String status;
  private int httpStatus;
  private String message;

  public ErrorDto(int httpStatus,String message) {
    this.status = "error";
    this.httpStatus = httpStatus;
    this.message = message;
  }
}
