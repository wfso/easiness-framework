package cn.ibestcode.easiness.auth.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "easiness_user_permission",
  indexes = {
    @Index(columnList = "userId", name = "easiness_user_permission_userId"),
    @Index(columnList = "permissionCode", name = "easiness_user_permission_permissionCode")
  }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EasinessUserPermission.class)
@ApiModel(description = "用户-权限")
public class EasinessUserPermission implements Serializable {
  @Id
  @ApiModelProperty("用户的ID")
  private long userId;

  @Id
  @ApiModelProperty("权限的Code")
  @Column(length = 100)
  private String permissionCode;
}
