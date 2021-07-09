package com.fdm.server.project.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fdm.server.project.server.user.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.persistence.*;


@Entity(name = "ApplicationUser")
@Table(
        name = "application_user"
)
public class ApplicationUser implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "application_user_sequence",
            sequenceName = "application_user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "application_user_sequence"
    )
    @Column(
            name = "application_user_id",
            nullable = false,
            unique = true,
            columnDefinition = "bigint"
    )
    private Long applicationUserId;

    @Column(
            name = "username",
            columnDefinition = "text",
            nullable = false
    )
    private String username;

    @Column(
            name = "password",
            columnDefinition = "text",
            nullable = false
    )
    private String password;

    @Column(
            name = "email",
            columnDefinition = "text"
    )
    private String email;

    @Column(
            name = "first_name",
            columnDefinition = "text"
    )
    private String firstName;

    @Column(
            name = "last_name",
            columnDefinition = "text"
    )
    private String lastName;

//    @Column(
//            name = "birth_date",
//            columnDefinition = "timestamp"
//    )
//    private LocalDate birthDate;

    @Column(
            name = "available_funds",
            columnDefinition = "double precision"
    )
    private Double availableFunds;

    @Column(
            name = "role",
            nullable = false
    )
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(
            name = "locked"
    )
    private Boolean locked = false;

    @Column(
            name = "enabled"
    )
    private Boolean enabled = false;
//    private List<Trade> trades;

    @Column(
            name = "verified"
    )
    private Boolean verified = false;

    @Transient
    @JsonIgnore
    private Collection<GrantedAuthority> authorities;

    @Transient
    @JsonIgnore
    private boolean accountNonExpired;

    @Transient
    @JsonIgnore
    private boolean accountNonLocked;

    @Transient
    @JsonIgnore
    private boolean credentialsNonExpired;



    public ApplicationUser() {
    }

    public ApplicationUser(String username,
                           String password,
                           Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public ApplicationUser(String username,
                           String password,
                           String email,
                           Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public ApplicationUser(String username,
                           String password,
                           String email,
                           String firstName,
                           String lastName,
//                           LocalDate birthDate,
                           Double availableFunds,
                           Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
//        this.birthDate = birthDate;
        this.availableFunds = availableFunds;
        this.role = role;
    }

    public ApplicationUser(Long applicationUserId,
                           String username,
                           String password,
                           String email,
                           String firstName,
                           String lastName,
                           LocalDate birthDate,
                           Double availableFunds,
                           Role role) {
        this.applicationUserId = applicationUserId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
//        this.birthDate = birthDate;
        this.availableFunds = availableFunds;
        this.role = role;
    }

    public Long getApplicationUserId() {
        return applicationUserId;
    }

    public void setApplicationUserId(Long id) {
        this.applicationUserId = id;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public LocalDate getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(LocalDate birthdate) {
//        this.birthDate = birthdate;
//    }

    public Double getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(Double availableFunds) {
        this.availableFunds = availableFunds;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "applicationUserId=" + applicationUserId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", availableFunds=" + availableFunds +
                ", role=" + role +
                ", locked=" + locked +
                ", enabled=" + enabled +
                ", verified=" + verified +
                ", authorities=" + authorities +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                '}';
    }
}
