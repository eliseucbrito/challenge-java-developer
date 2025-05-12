package br.com.neurotech.challenge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Standard error payload")
public class ErrorResponse {

  @Schema(example = "Resource not found")
  private String message;

  @Schema(example = "NOT_FOUND")
  private String code;

  @Schema(example = "404")
  private int status;

  @Schema(example = "2025-05-12T10:15:30")
  private String timestamp;
}