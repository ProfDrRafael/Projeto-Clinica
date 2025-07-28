package Regradenegocio;

import Persistencia.Dao.AdministradorDAO;
import Persistencia.Dao.EstagiarioDAO;
import Persistencia.Dao.JPAUtil;
import Persistencia.Dao.OrientadorDAO;
import Persistencia.Dao.PermissoesDAO;
import Persistencia.Dao.PesquisadorDAO;
import Persistencia.Dao.SecretariaDAO;
import Persistencia.Entity.Administrador;
import Persistencia.Entity.Estagiario;
import Persistencia.Entity.Orientador;
import Persistencia.Entity.Permissao;
import Persistencia.Entity.Pesquisador;
import Persistencia.Entity.Secretaria;
import VO.UsuarioVO;
import VO.EstagiarioVO;
import VO.OrientadorVO;
import VO.SecretariaVO;
import Services.SenhaService;
import VO.AdministradorVO;
import VO.PesquisadorVO;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import java.util.List;
import raven.toast.Notifications;

public class UsuarioRN {

    private SenhaService senhaService;

    public UsuarioRN() {
        this.senhaService = new SenhaService();
    }

    public UsuarioVO converterEntidadeParaVO(Object entidade) {
        if (entidade == null) {
            return null;
        }

        if (entidade instanceof Estagiario estagiario) {
            return new EstagiarioVO(
                    estagiario.getId(),
                    estagiario.getNome(),
                    estagiario.getEmail(),
                    estagiario.getSenha(),
                    null
            );
        } else if (entidade instanceof Orientador orientador) {
            return new OrientadorVO(
                    orientador.getId(),
                    orientador.getNome(),
                    orientador.getEmail(),
                    orientador.getSenha()
            );
        } else if (entidade instanceof Secretaria secretaria) {
            return new SecretariaVO(
                    secretaria.getId(),
                    secretaria.getNome(),
                    secretaria.getEmail(),
                    secretaria.getSenha()
            );
        } else if (entidade instanceof Pesquisador pesquisador) {
            return new PesquisadorVO(
                    pesquisador.getId(),
                    pesquisador.getNome(),
                    pesquisador.getEmail(),
                    pesquisador.getSenha()
            );
        } else if (entidade instanceof Administrador administrador) {
            return new AdministradorVO(
                    administrador.getId(),
                    administrador.getNome(),
                    administrador.getEmail(),
                    administrador.getSenha()
            );
        } else {
            throw new IllegalArgumentException("Tipo de entidade não suportado: " + entidade.getClass().getName());
        }
    }

    public UsuarioVO salvarUsuario(UsuarioVO usuarioVO) {
        if (!validarDadosUsuario(usuarioVO)) {
            return null;
        }

        try {
            if (usuarioVO instanceof EstagiarioVO estagiarioVO) {
                EstagiarioDAO estagiarioDAO = new EstagiarioDAO();
                return converterEntidadeParaVO(estagiarioDAO.salvarERetornar(converterVOParaEstagiario(estagiarioVO)));
            } else if (usuarioVO instanceof OrientadorVO orientadorVO) {
                OrientadorDAO orientadorDAO = new OrientadorDAO();
                return converterEntidadeParaVO(orientadorDAO.salvarERetornar(converterVOParaOrientador(orientadorVO)));
            } else if (usuarioVO instanceof SecretariaVO secretariaVO) {
                SecretariaDAO secretariaDAO = new SecretariaDAO();
                return converterEntidadeParaVO(secretariaDAO.salvarERetornar(converterVOParaSecretaria(secretariaVO)));
            } else if (usuarioVO instanceof PesquisadorVO pesquisadorVO) {
                PesquisadorDAO pesquisadorDAO = new PesquisadorDAO();
                return converterEntidadeParaVO(pesquisadorDAO.salvarERetornar(converterVOParaPesquisador(pesquisadorVO)));
            } else {
                throw new IllegalArgumentException("Tipo de usuário inválido.");
            }
        } catch (Exception e) {
            return null;
        }
    }

    private boolean validarDadosUsuario(UsuarioVO usuarioVO) {
        if (usuarioVO.getNomeCompleto() == null || usuarioVO.getNomeCompleto().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (usuarioVO.getEmail() == null || usuarioVO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }
        if (usuarioVO.getSenha() == null || usuarioVO.getSenha().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        return true;
    }

    private Estagiario converterVOParaEstagiario(EstagiarioVO vo) {
        Estagiario estagiario = new Estagiario();
        estagiario.setNome(vo.getNomeCompleto());
        estagiario.setEmail(vo.getEmail());
        estagiario.setSenha(senhaService.criptografarSenha(vo.getSenha())); // Criptografar a senha se necessário
        estagiario.setAno(vo.getAno());
        estagiario.setAtivo(vo.getAtivo());

        return estagiario;
    }

    private Orientador converterVOParaOrientador(OrientadorVO vo) {
        Orientador orientador = new Orientador();
        orientador.setNome(vo.getNomeCompleto());
        orientador.setEmail(vo.getEmail());
        orientador.setSenha(senhaService.criptografarSenha(vo.getSenha())); // Criptografar a senha se necessário
        // Outros atributos específicos de Orientador
        return orientador;
    }

    private Secretaria converterVOParaSecretaria(SecretariaVO vo) {
        Secretaria secretaria = new Secretaria();
        secretaria.setNome(vo.getNomeCompleto());
        secretaria.setEmail(vo.getEmail());
        secretaria.setSenha(senhaService.criptografarSenha(vo.getSenha())); // Criptografar a senha se necessário
        // Outros atributos específicos de Secretaria
        return secretaria;
    }

    private Pesquisador converterVOParaPesquisador(PesquisadorVO vo) {
        Pesquisador pesquisador = new Pesquisador();
        pesquisador.setNome(vo.getNomeCompleto());
        pesquisador.setEmail(vo.getEmail());
        pesquisador.setSenha(senhaService.criptografarSenha(vo.getSenha())); // Criptografar a senha se necessário
        pesquisador.setAtivo(vo.getAtivo());
        // Outros atributos específicos de Pesquisador
        return pesquisador;
    }

    // Método para buscar um estagiário por ID
    public UsuarioVO buscarUsuarioPorId(int id, String tipoUsuario) {
        Class<?> tipoUsuarioClass = null;

        switch (tipoUsuario) {
            case "Usuário":
                tipoUsuarioClass = Estagiario.class;
                break;
            case "Orientador":
                tipoUsuarioClass = Orientador.class;
                break;
            case "Secretária":
                tipoUsuarioClass = Secretaria.class;
                break;
            case "Pesquisador":
                tipoUsuarioClass = Pesquisador.class;
                break;
            case "Administrador":
                tipoUsuarioClass = Administrador.class;
                break;
            default:
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tipo de usuário inválido!");
                return null;
        }

        try {
            if (tipoUsuarioClass.equals(Estagiario.class)) {
                EstagiarioDAO estagiarioDAO = new EstagiarioDAO();
                Estagiario estagiario = estagiarioDAO.buscarPorId(id);
                return converterEntidadeParaVO(estagiario);
                
            } else if (tipoUsuarioClass.equals(Orientador.class)) {
                OrientadorDAO orientadorDAO = new OrientadorDAO();
                Orientador orientador = orientadorDAO.buscarPorId(id);
                return converterEntidadeParaVO(orientador);
                
            } else if (tipoUsuarioClass.equals(Secretaria.class)) {
                SecretariaDAO secretariaDAO = new SecretariaDAO();
                Secretaria secretaria = secretariaDAO.buscarPorId(id);
                return converterEntidadeParaVO(secretaria);
                
            } else if (tipoUsuarioClass.equals(Pesquisador.class)) {
                PesquisadorDAO pesquisadorDAO = new PesquisadorDAO();
                Pesquisador pesquisador = pesquisadorDAO.buscarPorId(id);
                return converterEntidadeParaVO(pesquisador);
                
            } else if (tipoUsuarioClass.equals(Administrador.class)) {
                AdministradorDAO administradorDAO = new AdministradorDAO();
                Administrador administrador = administradorDAO.buscarPorId(id);
                return converterEntidadeParaVO(administrador);

            } else {
                throw new IllegalArgumentException("Tipo de usuário inválido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Método para buscar um estagiário por ID
    public UsuarioVO buscarUsuarioPorEmail(String email, String tipoUsuario) {
        Class<?> tipoUsuarioClass = null;
        
        switch (tipoUsuario) {
            // case "Usuário":
            //     tipoUsuarioClass = Estagiario.class;
            //     break;
            case "Estagiário":
                tipoUsuarioClass = Estagiario.class;
                break;
            case "Orientador":
                tipoUsuarioClass = Orientador.class;
                break;
            case "Secretária":
                tipoUsuarioClass = Secretaria.class;
                break;
            case "Pesquisador":
                tipoUsuarioClass = Pesquisador.class;
                break;
            case "Administrador":
                tipoUsuarioClass = Administrador.class;
                break;
            default:
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Tipo de usuário inválido!");
                return null;
        }
                
        try {
            if (tipoUsuarioClass.equals(Estagiario.class)) {
                EstagiarioDAO estagiarioDAO = new EstagiarioDAO();
                Estagiario estagiario = estagiarioDAO.buscarPorEmailComOrientador(email);
                return converterEntidadeParaVO(estagiario);
                
            } else if (tipoUsuarioClass.equals(Orientador.class)) {
                OrientadorDAO orientadorDAO = new OrientadorDAO();
                Orientador orientador = orientadorDAO.buscarPorEmail(email);
                return converterEntidadeParaVO(orientador);
                
            } else if (tipoUsuarioClass.equals(Secretaria.class)) {
                SecretariaDAO secretariaDAO = new SecretariaDAO();
                Secretaria secretaria = secretariaDAO.buscarPorEmail(email);
                return converterEntidadeParaVO(secretaria);
                
            } else if (tipoUsuarioClass.equals(Pesquisador.class)) {
                PesquisadorDAO pesquisadorDAO = new PesquisadorDAO();
                Pesquisador pesquisador = pesquisadorDAO.buscarPorEmail(email);
                
                System.out.println("pesquisador resultado " +pesquisador);
                return converterEntidadeParaVO(pesquisador);
                
            } else if (tipoUsuarioClass.equals(Administrador.class)) {
                AdministradorDAO administradorDAO = new AdministradorDAO();
                Administrador administrador = administradorDAO.buscarPorEmail(email);
                return converterEntidadeParaVO(administrador);

            } else {
                throw new IllegalArgumentException("Tipo de usuário inválido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Permissao> buscarRecursosPermitidos(Pesquisador pesquisador) {
        EntityManager em = JPAUtil.getEntityManager(); 
        PermissoesDAO permissoesDAO = new PermissoesDAO(em);
        List<Permissao> permissoes = permissoesDAO.buscarPermissoes(pesquisador);
        em.close(); 
        
        return permissoes;
    }

}
