package cn.ibestcode.easiness.auth.model;

import cn.ibestcode.easiness.core.base.model.BaseJpaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(
  name = "easiness_role",
  indexes = {
    @Index(columnList = "code", name = "easiness_role_code", unique = true),
    @Index(columnList = "name", name = "easiness_role_name", unique = true)
  }
)
@ApiModel("角色实体")
@Setter
@Getter
@EqualsAndHashCode
@ToString(callSuper = true)
public class EasinessRole extends BaseJpaModel  {

  @ApiModelProperty("角色名")
  @Column(length = 50)
  private String name;

  @ApiModelProperty("角色说明")
  @Column(length = 200)
  private String intro;

  @ApiModelProperty("角色代码")
  @Column(length = 50)
  private String code;

}
