package com.echo.leaveapplication.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor @Getter @Setter @AllArgsConstructor
public class ApplicationAuthorityModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String authorityName;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH,CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "users_roles",

            joinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"),

            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id")
    )
    private Set<ApplicationUserModel>  users = new HashSet<>();

}
