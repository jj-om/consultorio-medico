package Mapper;

import DTO.MedicoViejoDTO;
import Entidades.Medico;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public class MedicoMapper {
    
    /**
     * Convierte una entidad Medico a un MedicoViejoDTO (incluyendo ID)
     */
    public MedicoViejoDTO toViejoDTO(Medico medico) {
        if (medico == null) {
            return null;
        }
        return new MedicoViejoDTO(
            String.valueOf(medico.getId_medico()), // Convertimos el ID a String
            medico.getCedulaProfesional(),
            medico.getNombres(),
            medico.getApellidoPaterno(),
            medico.getApellidoMaterno(),
            medico.getEspecialidad(),
            medico.getEstado()
        );
    }
    
    /**
     * Convierte una lista de entidades Medico a una lista de DTOs MedicoViejoDTO
     */
    public List<MedicoViejoDTO> toViejoDTOList(List<Medico> listaMedicos) {
        if (listaMedicos == null || listaMedicos.isEmpty()) {
            return new ArrayList<>();
        }

        List<MedicoViejoDTO> listaDTO = new ArrayList<>();
        for (Medico medico : listaMedicos) {
            listaDTO.add(toViejoDTO(medico));
        }
        return listaDTO;
    }
    
}
