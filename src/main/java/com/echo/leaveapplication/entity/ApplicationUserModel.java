package com.echo.leaveapplication.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor @Getter @Setter @AllArgsConstructor
public class ApplicationUserModel {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "username", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @JsonIgnore
    @Column(name = "password", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.REFRESH,CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "users_roles",

            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"),

            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id")
    )
    private Set<ApplicationAuthorityModel> authorities = new HashSet<>();
}
