package cn.ibestcode.easiness.core.base.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UuidBaseJpaModel extends BaseJpaModel implements UuidModel {

  @ApiModelProperty("逻辑主键")
  @Column(length = 32, updatable = false, nullable = false)
  private String uuid;
}
