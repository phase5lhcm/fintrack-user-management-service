package com.fintrackusermanagement.usermanagement.model;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

    @Entity
    @Table(name = "roles")
    public class Role {
        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Set<User> getUsers() {
            return users;
        }

        public void setUsers(Set<User> users) {
            this.users = users;
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long roleId;

        @Column(unique = true, nullable = false)
        private String roleName;

        private String description;

        @ManyToMany(mappedBy = "roles")
        private Set<User> users = new HashSet<>();
}
