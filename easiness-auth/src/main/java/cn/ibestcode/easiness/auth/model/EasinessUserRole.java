package cn.ibestcode.easiness.auth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "easiness_user_role",
  indexes = {
    @Index(columnList = "userId", name = "easiness_user_role_userId"),
    @Index(columnList = "roleCode", name = "easiness_user_role_roleCode")
  }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EasinessUserRole.class)
@ApiModel(description = "用户-角色")
public class EasinessUserRole implements Serializable {

  @Id
  @ApiModelProperty("用户的ID")
  private long userId;

  @Id
  @ApiModelProperty("角色的ID")
  @Column(length = 50)
  private String roleCode;
}
