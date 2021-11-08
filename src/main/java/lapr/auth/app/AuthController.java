package lapr.auth.app;


import lapr.auth.mappers.dto.UserRoleDTO;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class AuthController {

    private App app;

    public AuthController() throws IOException {
        this.app = App.getInstance();
    }

    public boolean doLogin(String email, String pwd)
    {
        try {
            return this.app.doLogin(email, pwd);
        } catch(IllegalArgumentException ex)
        {
            return false;
        }
    }

    public List<UserRoleDTO> getUserRoles()
    {
        if (this.app.getCurrentUserSession().isLoggedIn())
        {
            return this.app.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    public void doLogout()
    {
        this.app.doLogout();
    }
}
