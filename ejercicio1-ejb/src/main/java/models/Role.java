/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Marek Piechut
 */
@Entity(name = "ROLES")
public class Role implements Serializable {

    public static enum ROLE {
        ADMINISTRATOR, USER
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ROLE_NAME")
    @Enumerated(EnumType.STRING)
    private ROLE role;
    @Id
    @OneToOne
    @JoinColumn(name = "USER_NAME")
    private User user;

    protected Role() {
    }

    protected Role(ROLE role, User user) {
        this.role = role;
        this.user = user;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (this.role != other.role) {
            return false;
        }
        if (this.user != other.user && (this.user == null || 
                    !this.user.equals(other.user))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.role != null ? this.role.hashCode() : 0);
        hash = 89 * hash + (this.user != null ? this.user.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Role{" + "role=" + role + ", user=" + user + '}';
    }

    
}
