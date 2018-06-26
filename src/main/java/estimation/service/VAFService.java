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

    public VAFService(){ }

    public VAFService(VAFDao vafDao){
        this.vafDAO = vafDao;
    }

    public boolean add(String id, VAF vaf) {
        return this.vafDAO.add(id, vaf);
    }
    
    public boolean change(String id,VAF vaf) {
    	return this.vafDAO.change(id, vaf);
    }

}
