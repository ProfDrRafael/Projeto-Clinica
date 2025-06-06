package Persistencia.Entity;

import Persistencia.Dao.JPAUtil;
import static com.mysql.cj.conf.PropertyKey.logger;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "pais")
public class Pais {
    private static final Logger logger = LoggerFactory.getLogger(Pais.class);
    
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "sigla", length = 2)
    private String sigla;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    public Pais buscarPorNome(String nome) {
        EntityManager em = JPAUtil.getEntityManager();
        Pais pais = null;
        try {
            TypedQuery<Pais> query = em.createQuery(
                    "SELECT o FROM Pais o WHERE o.nome = :nome", Pais.class);
            query.setParameter("nome", nome);
            pais = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("País não encontrado com o nome: {}", nome);
        } catch (Exception e) {
            logger.error("Erro ao buscar País por nome: ", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return pais;
    }

}