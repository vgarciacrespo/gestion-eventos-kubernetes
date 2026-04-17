package es.upsa.tfg.gestioneventos.wscatalogo.infraestructure.persistence;

import es.upsa.tfg.gestioneventos.domain.entities.Estado;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventoNotFoundException;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.exceptions.RecintoNotFoundException;
import es.upsa.tfg.gestioneventos.domain.exceptions.SQLEventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.adapters.output.persistence.Dao;
import es.upsa.tfg.gestioneventos.wscatalogo.domain.exceptions.FieldRequiredSQLException;
import es.upsa.tfg.gestioneventos.wscatalogo.domain.exceptions.InvalidEstadoSQLException;
import es.upsa.tfg.gestioneventos.wscatalogo.domain.exceptions.RecintoRelacionadoSQLException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DaoImpl implements Dao {
    @Inject
    DataSource dataSource;

    //eventos
    @Override
    public List<Evento> findEventos() throws EventosAppException
    {

        final String SQL = """
                 SELECT e.id_evento, e.nombre, e.descripcion, e.disciplina, e.id_recinto, e.fecha_inicio, e.fecha_fin, e.precio, e.entradas_disponibles, e.estado
                 FROM eventos e
                """;
        List<Evento> eventos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()) {
                Evento evento = toEvento(resultSet);
                eventos.add(evento);
            }
            return eventos;


        } catch (SQLException sqlException) {
            throw toEventosAppException(sqlException);
        }
    }

    @Override
    public Optional<Evento> findEventoById(String id) throws EventosAppException
    {
        final String SQL = """
                 SELECT e.id_evento, e.nombre, e.descripcion, e.disciplina, e.id_recinto, e.fecha_inicio, e.fecha_fin, e.precio, e.entradas_disponibles, e.estado
                 FROM eventos e
                 WHERE e.id_evento = ?;
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(toEvento(resultSet)) : Optional.empty();
            }

        } catch (SQLException sqlException) {
            throw toEventosAppException(sqlException);
        }
    }

    @Override
    public Evento insertEvento(Evento evento) throws EventosAppException
    {
        final String SQL = """
                 INSERT INTO eventos( id_evento            , nombre, descripcion, disciplina, id_recinto, fecha_inicio, fecha_fin, precio, entradas_disponibles, estado)
                              VALUES(nextval('seq_eventos'), ?     ,      ?     ,      ?    ,     ?     ,     ?       ,     ?    ,    ?  ,       ?      ,    ?  )
                """;
        final String[] fields = {"id_evento"};
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, fields)) {
            preparedStatement.setString(1, evento.getNombre());
            preparedStatement.setString(2, evento.getDescripcion());
            preparedStatement.setString(3, evento.getDisciplina());
            preparedStatement.setString(4, evento.getRecinto());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(evento.getFecha_inicio()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(evento.getFecha_final()));
            preparedStatement.setDouble(7, evento.getPrecio());
            preparedStatement.setInt(8, evento.getEntradas_disponibles());
            preparedStatement.setString(9, String.valueOf(evento.getEstado()));
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                String id = resultSet.getString(1);
                return evento.withId_evento(id);
            }
        } catch (SQLException sqlException) {
            throw toEventosAppException(sqlException);
        }
    }

    @Override
    public Optional<Evento> updateEvento(Evento evento) throws EventosAppException
    {
        final String SQL = """
                 UPDATE eventos
                 SET nombre = ?, descripcion = ?, disciplina = ?,
                     id_recinto = ?, fecha_inicio = ?, fecha_fin = ?,
                     precio = ?, entradas_disponibles = ?, estado = ?
                 WHERE id_evento = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, evento.getNombre());
            preparedStatement.setString(2, evento.getDescripcion());
            preparedStatement.setString(3, evento.getDisciplina());
            preparedStatement.setString(4, evento.getRecinto());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(evento.getFecha_inicio()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(evento.getFecha_final()));
            preparedStatement.setDouble(7, evento.getPrecio());
            preparedStatement.setInt(8, evento.getEntradas_disponibles());
            preparedStatement.setString(9, String.valueOf(evento.getEstado()));
            preparedStatement.setString(10, evento.getId_evento());

            int count = preparedStatement.executeUpdate();
            return (count == 0) ? Optional.empty() : Optional.of(evento);

        } catch (SQLException sqlException) {
            throw toEventosAppException(sqlException);
        }
    }
    @Override
    public void deleteEvento(String id) throws EventosAppException
    {
        final String SQL = """
                DELETE
                FROM eventos
                WHERE id_evento = ?
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setString(1, id);
            int count = preparedStatement.executeUpdate();
            if (count == 0) throw new EventoNotFoundException();


        } catch (SQLException sqlException) {
            throw toEventosAppException(sqlException);
        }
    }

    // recintos

    @Override
    public List<Recinto> findRecintos() throws EventosAppException {
        final String SQL = """
                 SELECT r.id_recinto, r.nombre, r.ubicacion, r.capacidad_total
                 FROM recintos r
                """;
        List<Recinto> recintos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()) {
                Recinto recinto = toRecinto(resultSet);
                recintos.add(recinto);
            }
            return recintos;


        } catch (SQLException sqlException) {
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
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(toRecinto(resultSet)) : Optional.empty();
            }
        } catch (SQLException sqlException) {
            throw toEventosAppException(sqlException);
        }
    }

    @Override
    public Recinto insertRecinto(Recinto recinto) throws EventosAppException {
        final String SQL = """
                             INSERT INTO recintos( id_recinto            , nombre, ubicacion, capacidad_total)
                                           VALUES(nextval('seq_recintos'), ?     ,      ?   ,      ?         )
                            """;
        final String[] fields = {"id_recinto"};
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, fields)) {
            preparedStatement.setString(1, recinto.getNombre());
            preparedStatement.setString(2, recinto.getUbicacion());
            preparedStatement.setInt(3, recinto.getCapacidad_total());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                String id = resultSet.getString(1);
                return recinto.withId_recinto(id);
            }
        } catch (SQLException sqlException) {
            throw toEventosAppException(sqlException);
        }
    }

    @Override
    public Optional<Recinto> updateRecinto(Recinto recinto) throws EventosAppException {
        final String SQL = """
                             UPDATE recintos
                             SET id_recinto = ?, nombre = ?, ubicacion = ?,
                                 capacidad_total = ?
                            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, recinto.getNombre());
            preparedStatement.setString(2, recinto.getUbicacion());
            preparedStatement.setInt(3, recinto.getCapacidad_total());
            preparedStatement.executeUpdate();

            int count = preparedStatement.executeUpdate();
            return (count == 0) ? Optional.empty() : Optional.of(recinto);

        } catch (SQLException sqlException) {
            throw toEventosAppException(sqlException);
        }
    }

    @Override
    public void deleteRecinto(String id) throws EventosAppException {
        final String SQL = """
                            DELETE
                            FROM recintos
                            WHERE id_recinto = ?
                           """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setString(1, id);
            int count = preparedStatement.executeUpdate();
            if (count == 0) throw new RecintoNotFoundException();


        } catch (SQLException sqlException) {
            throw toEventosAppException(sqlException);
        }
    }


    private Evento toEvento(ResultSet resultSet) throws SQLException {
        return Evento.builder()
                .withId_evento(resultSet.getString("id_evento"))
                .withNombre(resultSet.getString("nombre"))
                .withDescripcion(resultSet.getString("descripcion"))
                .withDisciplina(resultSet.getString("disciplina"))
                .withRecinto(resultSet.getString("id_recinto"))
                .withFecha_inicio(resultSet.getObject("fecha_inicio", LocalDateTime.class))
                .withFecha_final(resultSet.getObject("fecha_fin", LocalDateTime.class))
                .withPrecio(resultSet.getDouble("precio"))
                .withEntradas_disponibles(resultSet.getInt("entradas_disponibles"))
                .withEstado(Estado.valueOf(resultSet.getString("estado")))
                .build();
    }

    private Recinto toRecinto(ResultSet resultSet) throws SQLException {
        return Recinto.builder()
                .withId_recinto(resultSet.getString("id_recinto"))
                .withNombre(resultSet.getString("nombre"))
                .withUbicacion(resultSet.getString("ubicacion"))
                .withCapacidad_total(resultSet.getInt("capacidad_total"))
                .build();
    }

    private EventosAppException toEventosAppException(SQLException sqlException) {
        String message = sqlException.getMessage();

// recintos
        if (message.contains("NN_RECINTOS_NOMBRE")) return new FieldRequiredSQLException("nombre");
        else if (message.contains("NN_RECINTOS_UBICACION")) return new FieldRequiredSQLException("ubicacion");
        else if (message.contains("NN_RECINTOS_CAPACIDAD")) return new FieldRequiredSQLException("capacidad_total");

//eventos
        else if (message.contains("NN_EVENTOS_NOMBRE")) return new FieldRequiredSQLException("nombre");
        else if (message.contains("NN_EVENTOS_DISCIPLINA")) return new FieldRequiredSQLException("disciplina");
        else if (message.contains("NN_EVENTOS_RECINTO")) return new FieldRequiredSQLException("id_recinto");
        else if (message.contains("NN_EVENTOS_FECHA_INICIO")) return new FieldRequiredSQLException("fecha_inicio");
        else if (message.contains("NN_EVENTOS_FECHA_FIN")) return new FieldRequiredSQLException("fecha_fin");
        else if (message.contains("NN_EVENTOS_ENTRADAS_DISPONIBLES")) return new FieldRequiredSQLException("entradas_disponibles");

        else if (message.contains("CK_EVENTOS_ESTADO")) return new InvalidEstadoSQLException();
        else if (message.contains("FK_EVENTOS_RECINTO")) return new RecintoRelacionadoSQLException();

        return new SQLEventosAppException(sqlException);
    }
}
