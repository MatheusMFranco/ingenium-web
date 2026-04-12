package br.com.ingenium.application.service;

import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.spec.EncryptablePasswordSpec;
import org.wildfly.security.password.spec.IteratedSaltedPasswordAlgorithmSpec;
import org.wildfly.security.password.util.ModularCrypt;

import jakarta.enterprise.context.ApplicationScoped;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@ApplicationScoped
public class PasswordHasher {

    private static final String ALGORITHM = BCryptPassword.ALGORITHM_BCRYPT;

    public String hash(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Senha não pode ser nula");
        }
        try {
            return generateHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Erro ao gerar hash da senha", e);
        }
    }

    // Método extraído para facilitar mock de exceções se necessário, 
    // mas aqui usaremos para garantir que o fluxo principal é testável.
    protected String generateHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PasswordFactory passwordFactory = getPasswordFactory();
        IteratedSaltedPasswordAlgorithmSpec algorithmSpec = new IteratedSaltedPasswordAlgorithmSpec(10, new byte[16]);
        EncryptablePasswordSpec encryptableSpec = new EncryptablePasswordSpec(password.toCharArray(), algorithmSpec);
        BCryptPassword bcryptPassword = (BCryptPassword) passwordFactory.generatePassword(encryptableSpec);
        
        return new String(ModularCrypt.encode(bcryptPassword));
    }

    protected PasswordFactory getPasswordFactory() throws NoSuchAlgorithmException {
        return PasswordFactory.getInstance(ALGORITHM);
    }

    public boolean verify(String password, String storedHash) {
        if (password == null || storedHash == null || storedHash.isBlank()) {
            return false;
        }
        try {
            BCryptPassword bcryptPassword = (BCryptPassword) ModularCrypt.decode(storedHash);
            PasswordFactory passwordFactory = getPasswordFactory();
            return passwordFactory.verify(bcryptPassword, password.toCharArray());
        } catch (Exception e) {
            return false;
        }
    }
}
