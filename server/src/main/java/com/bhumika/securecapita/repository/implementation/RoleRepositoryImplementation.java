package com.bhumika.securecapita.repository.implementation;

import static com.bhumika.securecapita.enums.RoleType.USER;
import static com.bhumika.securecapita.query.RoleQuery.INSERT_ROLE_TO_USER_QUERY;
import static com.bhumika.securecapita.query.RoleQuery.SELECT_ROLE_BY_NAME_QUERY;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bhumika.securecapita.entity.Role;
import com.bhumika.securecapita.exception.APIException;
import com.bhumika.securecapita.repository.RoleRepository;
import com.bhumika.securecapita.rowmapper.RoleRowMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImplementation implements RoleRepository<Role> {

    @Autowired
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role get(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to user id: {}", roleName, userId);
        try {
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, of("roleName", roleName),
                    new RoleRowMapper());
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, of("userId", userId, "roleId", requireNonNull(role.getId())));
        } catch (EmptyResultDataAccessException e) {
            throw new APIException(" No role exists by the name: " + USER.name());
        } catch (Exception e) {
            throw new APIException("An error occurred. Please try again!");
        }
    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {
    }
}