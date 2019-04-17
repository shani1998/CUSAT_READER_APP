package pro.himanshu.cusatreader;

/**
 * Created by Himanshu on 25-03-2018.
 */

public class SaveData {

   String name,college,branch,email;

    public SaveData(String name, String college, String branch, String email) {
        this.name = name;
        this.college = college;
        this.branch = branch;
        this.email= email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

