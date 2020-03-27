package services;

import domains.Tema;
import repository.XMLFileRepository;
import validators.ValidareTema;

public class ServiceTema {

    private XMLFileRepository<Long, Tema> repo;
    private ValidareTema validator;

    public ServiceTema(XMLFileRepository<Long, Tema> repo, ValidareTema validator) {
        this.repo = repo;
        this.validator = validator;
    }

    public Iterable<Tema> findAll()
    {
        return this.repo.findAll();
    }
}
