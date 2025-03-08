package backend.MoodBuddy.domain.repository;

import backend.MoodBuddy.domain.dto.getprofile.LoginInfo;
import backend.MoodBuddy.domain.dto.getprofile.Profile;
import backend.MoodBuddy.domain.dto.profile.Credentials;
import backend.MoodBuddy.external.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    /**
     * Query to find out if the given username exists.
     * @param username
     * @return
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM UserCredentials u " +
            "WHERE u.username = :username")
    boolean existsByUsername(
            @Param("username") String username
    );

    /**
     * Query to get the username and the password
     * @param username
     * @param password
     * @return
     */
    @Query ("SELECT uc " +
            "FROM UserCredentials uc " +
            "WHERE (uc.username = :username) " +
            "AND (uc.password = :password) ")
    Optional<UserCredentials> findByUsernameAndPassword(
            @Param("username") String username,
            @Param("password") String password
    );

    /**
     * Query to get the credentials
     * @param userId
     * @return
     */
    @Query("SELECT uc " +
            "FROM UserCredentials uc " +
            "WHERE uc.userId.userId = :userId")
    Optional<UserCredentials> findById(
            @Param("userId") Long userId
    );

    /**
     * Query to get the login info of the user
     * @param userId
     * @param date
     * @return
     */
    @Query("SELECT new backend.MoodBuddy.domain.dto.getprofile.LoginInfo " +
            "(uc.username, uc.password) " +
            "FROM UserCredentials uc " +
            "WHERE (uc.userId.userId = :userId) " +
            "AND (uc.recordExpiryDate IS NULL OR uc.recordExpiryDate >= :date) ")
    Optional<LoginInfo> getLoginInfoById(
            @Param("userId") Long userId,
            @Param("date") LocalDateTime date
    );
}
