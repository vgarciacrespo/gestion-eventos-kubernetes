package es.upsa.tfg.gestioneventos.wscatalogo.infraestructure.persistence;

import es.upsa.tfg.gestioneventos.domain.entities.Estado;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.exceptions.SQLEventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.adapters.output.persistence.Dao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class DaoImpl implements Dao
{
    @Inject
    DataSource dataSource;


    @Override
    public List<Evento> findEventos() throws EventosAppException {
        return List.of();
    }



    private Evento toEvento(ResultSet resultSet) throws SQLException
    {
        return Evento.builder()
                .withId_evento(resultSet.getString(1))
                .withNombre(resultSet.getString(2))
                .withDescripcion(resultSet.getString(3))
                .withDisciplina(resultSet.getString(4))
                .withRecinto(resultSet.getString(5))
                .withFecha_inicio(LocalDateTime.parse(resultSet.getString(6)))
                .withFecha_final(LocalDateTime.parse(resultSet.getString(7)))
                .withPrecio(resultSet.getDouble(8))
                .withCapacidad_max(resultSet.getInt(9))
                .withEstado(Estado.valueOf(resultSet.getString(10)))
                .build();
    }

    private SQLEventosAppException toEventosAppException(SQLException sqlException) {
        String message = sqlException.getMessage();
        return new SQLEventosAppException(sqlException);

    }
}
