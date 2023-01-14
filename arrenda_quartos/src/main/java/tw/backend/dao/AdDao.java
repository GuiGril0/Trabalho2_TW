package tw.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tw.backend.config.ConnectionDB;
import tw.backend.model.Ad;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class AdDao {
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
    public List<Ad> getThreeRecentAds() throws Exception{
        //jdbcTemplate.queryForList("select * from anuncios;");
        ConnectionDB db = setConnectionToDB();
        db.connectDb();
        Statement stmt = db.getStatement();

        ResultSet rs = stmt.executeQuery("select * from advertisements ORDER BY date DESC LIMIT 3");

        List<Ad> threeAds = new ArrayList<Ad>();
        while(rs.next()) {
            Ad ad = new Ad(1);

            ad.setAdvertiser(rs.getString("advertiser"));
            ad.setType(rs.getString("typead"));
            ad.setState(rs.getString("statead"));
            ad.setPrice(rs.getDouble("price"));
            ad.setGender(rs.getString("gender"));
            ad.setLocal(rs.getString("localad"));
            ad.setTypology(rs.getString("typology"));
            ad.setEmail(rs.getString("email"));
            ad.setDate(rs.getDate("date"));
            ad.setDescription(rs.getString("description"));
            ad.setAid(rs.getInt("aid"));

            threeAds.add(ad);
        }

        db.closeConnection();
        return threeAds;
    }

    public List<Ad> getAdsByFields(String fields) throws Exception{
        ConnectionDB db = setConnectionToDB();
        db.connectDb();
        Statement stmt = db.getStatement();

        String[] aux = fields.split("&");
        String response = "";
        for(String i : aux) {
            String[] values = i.split("=");
            if(values[0].equals("typead"))
                response += values[0] + " LIKE " + values[1];
            else {
                response += values[0] + " LIKE '%";
                for(int j=1; j < values.length; j++)
                    response += values[j];
                response += "%'";
            }
            response += " AND ";
        }
        response = response.substring(0, response.length() - 4);
        response = response.trim();

        ResultSet rs = stmt.executeQuery("select * from advertisements WHERE " + response + " ORDER BY aid ASC");
        List<Ad> ads = new ArrayList<Ad>();
        while(rs.next()) {
            Ad ad = new Ad(1);

            ad.setAdvertiser(rs.getString("advertiser"));
            ad.setType(rs.getString("typead"));
            ad.setState(rs.getString("statead"));
            ad.setPrice(rs.getDouble("price"));
            ad.setGender(rs.getString("gender"));
            ad.setLocal(rs.getString("localad"));
            ad.setTypology(rs.getString("typology"));
            ad.setEmail(rs.getString("email"));
            ad.setDate(rs.getDate("date"));
            ad.setDescription(rs.getString("description"));
            ad.setAid(rs.getInt("aid"));

            ads.add(ad);
        }

        db.closeConnection();
        return ads;
    }
}
