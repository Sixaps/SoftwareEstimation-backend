package estimation.service;

import estimation.DAO.VAFDao;
import estimation.bean.VAF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuawai on 16/06/2017.
 */
@Service
public class VAFService {

    @Autowired
    private VAFDao vafDAO;

    public void add(String id, VAF vaf) {
        this.vafDAO.add(id, vaf);
    }
    
    public void change(String id,VAF vaf) {
    	this.vafDAO.change(id, vaf);
    }

}
