package Services;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class SenhaService {

    private final Argon2PasswordEncoder argon2PasswordEncoder;

    public SenhaService() {
        this.argon2PasswordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    public String criptografarSenha(String senha) {
        return argon2PasswordEncoder.encode(senha);
    }

    public boolean verificarSenha(String senhaInserida, String senhaCriptografada) {
        return argon2PasswordEncoder.matches(senhaInserida, senhaCriptografada);
    }
}