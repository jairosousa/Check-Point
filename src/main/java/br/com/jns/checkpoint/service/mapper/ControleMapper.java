package br.com.jns.checkpoint.service.mapper;

import br.com.jns.checkpoint.domain.*;
import br.com.jns.checkpoint.service.dto.ControleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Controle and its DTO ControleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ControleMapper extends EntityMapper<ControleDTO, Controle> {



    default Controle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Controle controle = new Controle();
        controle.setId(id);
        return controle;
    }
}
