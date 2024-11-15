package VO;

import Persistencia.Entity.Orientador;

/**
 * Classe que representa um Orientador como VO.
 */
public class OrientadorVO extends UsuarioVO {
    private Boolean ativo;
    private String linhaTeorica;

    // Construtor com atributos básicos
    public OrientadorVO(int id, String nomeCompleto, String email, String senha) {
        super(id, nomeCompleto, email, senha);
        this.ativo = true; // Orientador ativo por padrão
    }

    // Construtor com todos os atributos
    public OrientadorVO(int id, String nomeCompleto, String email, String senha, Boolean ativo, String linhaTeorica) {
        super(id, nomeCompleto, email, senha);
        this.ativo = ativo;
        this.linhaTeorica = linhaTeorica;
    }

    @Override
    public String getTipo() {
        return "Orientador";
    }

    // Getters e Setters
    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getLinhaTeorica() {
        return linhaTeorica;
    }

    public void setLinhaTeorica(String linhaTeorica) {
        this.linhaTeorica = linhaTeorica;
    }

    // Método de validação
    public boolean isValid() {
        return this.getNomeCompleto() != null && !this.getNomeCompleto().isEmpty() &&
                this.getEmail() != null && !this.getEmail().isEmpty() &&
                (this.getLinhaTeorica() == null || !this.getLinhaTeorica().isEmpty());
    }

    // Método de conversão de Entity para VO
    public static OrientadorVO fromEntity(Orientador orientador) {
        return new OrientadorVO(
                orientador.getId(),
                orientador.getNome(),
                orientador.getEmail(),
                null, // Senha não é transferida
                orientador.getAtivo(),
                orientador.getLinhaTeorica()
        );
    }

    // Método de conversão de VO para Entity
    public Orientador toEntity() {
        Orientador orientador = new Orientador();
        orientador.setId(this.getId());
        orientador.setNome(this.getNomeCompleto());
        orientador.setEmail(this.getEmail());
        orientador.setSenha(this.getSenha());
        orientador.setAtivo(this.getAtivo());
        orientador.setLinhaTeorica(this.getLinhaTeorica());
        return orientador;
    }

    // Método para atualizar uma entidade existente
    public void updateEntity(Orientador orientador) {
        orientador.setNome(this.getNomeCompleto());
        orientador.setEmail(this.getEmail());
        orientador.setAtivo(this.getAtivo());
        orientador.setLinhaTeorica(this.getLinhaTeorica());
    }
}
