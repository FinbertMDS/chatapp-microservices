package com.finbertmds.microservice.message.entity;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(value = Schema.USER_LOGIN_INFO_UDT)
public class LightUserModel {

    @NotEmpty
    @Size(min = 3, max = 20)
    @Column
    protected String login;

    @NotEmpty
    @Size(max = 100)
    @Column
    protected String firstname;

    @NotEmpty
    @Size(max = 100)
    @Column
    protected String lastname;


    public LightUserModel(String login, String firstname, String lastname) {
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFormattedName() {
        return this.firstname+" "+this.lastname;
    }

    public LightUserModel() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LightUserModel that = (LightUserModel) o;

        return Objects.equals(this.login,that.login);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.login);
    }
}
