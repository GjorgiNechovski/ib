package mk.ukim.finki.ib.lab.models.exceptions;

public class AdminCantChangeAdminException extends Exception{
    public AdminCantChangeAdminException() {
        super("Only a super admin can edit or delete an admin");
    }
}
