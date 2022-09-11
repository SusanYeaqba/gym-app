package org.legion.model.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import org.legion.model.baseEntity.UserAccountBase;
import java.util.List;
import java.util.Set;
public class UserAccount extends UserAccountBase  {

    public boolean isStaff(){
        return super.getRole().equals("Admin") || super.getRole().equals("Manager");
    }

    public boolean isAdmin(){
        return super.getRole().equals("Admin");
    }

    public boolean isManager(){
        return super.getRole().equals("Manager");
    }

    public boolean isClient(){
        return super.getRole().equals("Client");
    }

    public boolean isTrainer(){
        return super.getRole().equals("Trainer");
    }

}
