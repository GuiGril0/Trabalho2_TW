package tw.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import tw.backend.config.ConnectionDB;
import tw.backend.model.User;
import tw.backend.rowmapper.UserRowMapper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public User getUser(final String username) {
        return jdbcTemplate.queryForObject(
                "select u.user_name user_name, u.user_pass user_pass, user_email, ur.user_role user_role from clients u, user_role ur where u.user_name = ? and u.user_name = ur.user_name",
                new String[]{username}, new UserRowMapper());
    }
    private static ConnectionDB setConnectionToDB() throws Exception{
        String[] props = new String[4];
        InputStream is = new FileInputStream("src/main/resources/application.properties");
        Properties p = new Properties();
        p.load(is);
        props[0] = p.getProperty("host");
        props[1] = p.getProperty("db");
        props[2] = p.getProperty("user");
        props[3] = p.getProperty("password");

        return new ConnectionDB(props[0], props[1], props[2], props[3]);
    }
/*
    public User getUser(String username) throws Exception{
        ConnectionDB db = setConnectionToDB();
        db.connectDb();
        Statement stmt = db.getStatement();

        ResultSet rs = stmt.executeQuery("select * from clients WHERE user_name LIKE '" + username + "'");
        rs.next();
        User u = new User();
        u.setUsername(rs.getString("user_name"));
        u.setPassword(rs.getString("user_pass"));
        u.setEmail(rs.getString("user_email"));

        rs = stmt.executeQuery("select user_role from user_role WHERE user_name LIKE '" + username + "'");
        rs.next();
        u.setRole(rs.getString("user_role"));

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(u.getRole());

        return u;
    }
*/
    public void saveUser(final User u) {
        String sql = "INSERT INTO user VALUES ('"
                + u.getUsername() + "','"
                + u.getPassword() + "','"
                + u.getEmail() + "',0)";   // 0 == not enabled
        jdbcTemplate.execute(sql);
        System.out.println("UserDao - saved\n" + sql + "\n");
    }

    public List<String> getUsernameList() {
        return jdbcTemplate.queryForList("select user_name FROM user", String.class);
    }

}
