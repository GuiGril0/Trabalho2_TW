package tw.backend.dao;

import org.springframework.stereotype.Repository;
import tw.backend.config.ConnectionDB;
import tw.backend.model.Message;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class MessageDao {
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

    public List<Message> getAllMessagesFromAd (int aid) throws Exception{
        ConnectionDB db = setConnectionToDB();
        db.connectDb();
        Statement stmt = db.getStatement();

        ResultSet rs = stmt.executeQuery("select * from messages WHERE ad_aid=" + aid + " ORDER BY date DESC");

        List<Message> msgs = new ArrayList<Message>();
        while(rs.next()) {
            Message msg = new Message(0);
            msg.setSender(rs.getString("sender"));
            msg.setContent(rs.getString("content"));
            msg.setDate(rs.getDate("date"));

            msgs.add(msg);
        }
        db.closeConnection();
        return msgs;
    }

    public void insertMessage(Message msg) throws Exception{
        ConnectionDB db = setConnectionToDB();
        db.connectDb();
        Statement stmt = db.getStatement();

        stmt.execute("insert into messages values ('" + msg.getSender() +
                "', '" + msg.getContent() +
                "', '" + msg.getDate() +
                "', " + msg.getAd_aid() + ");");
        db.closeConnection();
    }
}
