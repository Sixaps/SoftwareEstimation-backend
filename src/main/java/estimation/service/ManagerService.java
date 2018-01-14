package estimation.service;

import estimation.DAO.ManagerDAO;
import estimation.bean.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author : MianHong Li
 * @date : 2018/1/14
 */
@Service
public class ManagerService {
    @Autowired
    ManagerDAO managerDAO;

    public Boolean judgeIdentity(String username){
        List<Manager> managers = managerDAO.getAll();
        for(Manager manager : managers){
            if(manager.getUsername().equals(username)){
                return true;
            }
        }

        return false;
    }

    public Boolean add(String username){
        String id = String.valueOf(System.currentTimeMillis());
        Manager manager = new Manager();
        manager.setId(id);
        manager.setUsername(username);
        try {
            managerDAO.add(manager);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Boolean delete(String username){
        try {
            managerDAO.delete(username);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<Manager> getAll(){
        try {
            return managerDAO.getAll();
        }
        catch (Exception e){
            return null;
        }
    }
}
