package cn.ibestcode.easiness.auth.model;

import cn.ibestcode.easiness.core.base.model.BaseJpaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(
  name = "easiness_permission",
  indexes = {
    @Index(columnList = "code", name = "easiness_permission_code", unique = true),
    @Index(columnList = "name", name = "easiness_permission_name", unique = true)
  }
)
@ApiModel(description = "权限实体")
@Getter
@Setter
@ToString(callSuper = true)
public class EasinessPermission extends BaseJpaModel  {

  @ApiModelProperty("权限名")
  @Column(length = 100)
  private String name;

  @ApiModelProperty("权限说明")
  @Column(length = 200)
  private String intro;

  @ApiModelProperty("权限代码")
  @Column(length = 100)
  private String code;
}
