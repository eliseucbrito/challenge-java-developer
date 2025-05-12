package br.com.neurotech.challenge.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@Schema(name = "Auditable", description = "Timestamps for record creation, update, and soft-deletion")
public abstract class Auditable {

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  @Schema(description = "Date and time when the record was created", example = "2025-05-12T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  @Schema(description = "Date and time when the record was last updated", example = "2025-05-13T09:45:00", accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at", nullable = true)
  @Schema(description = "Date and time when the record was soft-deleted (null if active)", example = "null", nullable = true, accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDateTime deletedAt;

  public boolean isDeleted() {
    return deletedAt != null;
  }

  public void markAsDeleted() {
    this.deletedAt = LocalDateTime.now();
  }
}
