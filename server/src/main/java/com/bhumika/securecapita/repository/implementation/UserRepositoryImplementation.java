package com.bhumika.securecapita.repository.implementation;

import static com.bhumika.securecapita.enums.RoleType.USER;
import static com.bhumika.securecapita.enums.VerificationType.ACCOUNT;
import static com.bhumika.securecapita.query.UserQuery.COUNT_USER_EMAIL_QUERY;
import static com.bhumika.securecapita.query.UserQuery.INSERT_ACCOUNT_VERIFICATION_URL_QUERY;
import static com.bhumika.securecapita.query.UserQuery.INSERT_USER_QUERY;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bhumika.securecapita.entity.Role;
import com.bhumika.securecapita.entity.User;
import com.bhumika.securecapita.exception.APIException;
import com.bhumika.securecapita.repository.RoleRepository;
import com.bhumika.securecapita.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImplementation implements UserRepository<User> {

    @Autowired
    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    private final RoleRepository<Role> roleRepository;

    @Autowired
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0) {
            throw new APIException("Email already in use. Please login!");
        }

        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey().longValue()));

            roleRepository.addRoleToUser(user.getId(), USER.name());

            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("userId", user.getId(), "url", verificationUrl));
            // emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(),
            // verificationUrl, ACCOUNT);
            user.setEnabled(false);
            user.setNotLocked(true);

            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new APIException("Role does not exist by name: " + USER.name());
        } catch (Exception e) {
            throw new APIException("An error occurred while creating the user!");
        }
    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource().addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName()).addValue("email ", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify" + type + "/" + key)
                .toUriString();
    }
}