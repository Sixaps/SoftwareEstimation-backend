package estimation.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author : MianHong Li
 * @date : 2018/1/14
 */
@Document(collection = "Manager")
public class Manager {
    @Id
    private String id;

    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
