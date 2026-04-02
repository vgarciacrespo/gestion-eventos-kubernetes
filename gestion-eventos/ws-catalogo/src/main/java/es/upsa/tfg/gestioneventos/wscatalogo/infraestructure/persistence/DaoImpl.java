package es.upsa.tfg.gestioneventos.wscatalogo.infraestructure.persistence;

import es.upsa.tfg.gestioneventos.domain.entities.Estado;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.exceptions.SQLEventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.adapters.output.persistence.Dao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DaoImpl implements Dao
{
    @Inject
    DataSource dataSource;


    @Override
    public List<Evento> findEventos() throws EventosAppException{

        final String SQL = """
                            SELECT e.id_evento, e.nombre, e.descripcion, e.disciplina, e.id_recinto, e.fecha_inicio, e.fecha_fin, e.precio, e.capacidad_max, e.estado
                            FROM eventos e
                           """;
        List<Evento> eventos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL))
        {
            while (resultSet.next()) {
                Evento evento = toEvento(resultSet);
                eventos.add(evento);
            }
            return eventos;


        }catch (SQLException sqlException){
            throw toEventosAppException(sqlException);
        }
    }

    @Override
    public Optional<Evento> findEventoById(String id) throws EventosAppException
    {
        final String SQL = """
                            SELECT e.id_evento, e.nombre, e.descripcion, e.disciplina, e.id_recinto, e.fecha_inicio, e.fecha_fin, e.precio, e.capacidad_max, e.estado
                            FROM eventos e
                            WHERE e.id_evento = ?;
                           """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        )
        {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                return resultSet.next() ? Optional.of(toEvento(resultSet)) : Optional.empty();
            }

        }catch (SQLException sqlException)
        {
            throw toEventosAppException(sqlException);
        }
    }

    @Override
    public Optional<Recinto> findRecintoById(String id) throws EventosAppException {
        final String SQL = """
                            SELECT r.id_recinto, r.nombre, r.ubicacion, r.capacidad_total
                            FROM recintos r
                            WHERE r.id_recinto = ?;
                           """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ){
            preparedStatement.setString(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery())
            {
                return resultSet.next() ? Optional.of(toRecinto(resultSet)) : Optional.empty();
            }
        }catch (SQLException sqlException)
        {
            throw toEventosAppException(sqlException);
        }
    }


    private Evento toEvento(ResultSet resultSet) throws SQLException
    {
        return Evento.builder()
                     .withId_evento(resultSet.getString("id_evento"))
                     .withNombre(resultSet.getString("nombre"))
                     .withDescripcion(resultSet.getString("descripcion"))
                     .withDisciplina(resultSet.getString("disciplina"))
                     .withRecinto(resultSet.getString("id_recinto"))
                     .withFecha_inicio(resultSet.getObject("fecha_inicio", LocalDateTime.class))
                     .withFecha_final(resultSet.getObject("fecha_fin", LocalDateTime.class))
                     .withPrecio(resultSet.getDouble("precio"))
                     .withCapacidad_max(resultSet.getInt("capacidad_max"))
                     .withEstado(Estado.valueOf(resultSet.getString("estado")))
                     .build();
    }
    private Recinto toRecinto(ResultSet resultSet) throws SQLException
    {
        return Recinto.builder()
                .withId_recinto(resultSet.getString("id_recinto"))
                .withNombre(resultSet.getString("nombre"))
                .withUbicacion(resultSet.getString("ubicacion"))
                .withCapacidad_total(resultSet.getInt("capacidad_total"))
                .build();
    }

    private EventosAppException toEventosAppException(SQLException sqlException)
    {
        String message = sqlException.getMessage();
        return new SQLEventosAppException(sqlException);

    }
}
