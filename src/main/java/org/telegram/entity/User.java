package org.telegram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.entity.enums.Role;
import org.telegram.entity.enums.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String password;
    private Role role;
    private Status status;
    private Double balance;
}
