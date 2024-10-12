package Services;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CriptografiaService {

    private SecretKey chaveSecreta;

    // Comprimento adequado 16 bytes / 128 bits
    private static final String CHAVE_FIXA = "bWluaGFzZWdyYW5kZXZzdXBlcnNlZ3VyaXR5";

    public CriptografiaService() {
        this.chaveSecreta = gerarChaveFixa();
    }

    private SecretKey gerarChaveFixa() {
        byte[] decodedKey = Base64.getDecoder().decode(CHAVE_FIXA);

        byte[] aesKey = new byte[16];  // AES-128
        System.arraycopy(decodedKey, 0, aesKey, 0, Math.min(decodedKey.length, aesKey.length));

        return new SecretKeySpec(aesKey, "AES");
    }

    // Gerar IV
    private byte[] gerarIV() {
        byte[] iv = new byte[12];
        new java.security.SecureRandom().nextBytes(iv);
        return iv;
    }

    public String criptografarTexto(String texto) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = gerarIV();  // IV único
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, chaveSecreta, spec);

        byte[] textoCriptografado = cipher.doFinal(texto.getBytes());

        // Combina o IV com o texto criptografado 
        byte[] ivETextoCriptografado = new byte[iv.length + textoCriptografado.length];
        System.arraycopy(iv, 0, ivETextoCriptografado, 0, iv.length);
        System.arraycopy(textoCriptografado, 0, ivETextoCriptografado, iv.length, textoCriptografado.length);

        return Base64.getEncoder().encodeToString(ivETextoCriptografado);
    }

    public String decifrarTexto(String textoCifrado) throws Exception {
        Cipher decipher = Cipher.getInstance("AES/GCM/NoPadding");

        // Recuperar os bytes do texto cifrado
        byte[] ivETextoCriptografado = Base64.getDecoder().decode(textoCifrado);

        // Extrair o IV dos primeiros 12 bytes
        byte[] iv = new byte[12];
        System.arraycopy(ivETextoCriptografado, 0, iv, 0, iv.length);

        // Extrair o texto
        byte[] textoCriptografado = new byte[ivETextoCriptografado.length - iv.length];
        System.arraycopy(ivETextoCriptografado, iv.length, textoCriptografado, 0, textoCriptografado.length);

        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        decipher.init(Cipher.DECRYPT_MODE, chaveSecreta, spec);

        byte[] textoDescriptografado = decipher.doFinal(textoCriptografado);

        return new String(textoDescriptografado);
    }
}
