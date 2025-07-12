package controller;

public class SignInController {
    public boolean iniciarSesion(String usuario, String contrasena) {
        // Lógica real de autenticación (base de datos, etc.)
        return usuario.equals("admin") && contrasena.equals("1234");
    }
}


