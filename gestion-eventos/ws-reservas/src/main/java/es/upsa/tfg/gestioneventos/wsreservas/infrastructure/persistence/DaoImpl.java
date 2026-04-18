package es.upsa.tfg.gestioneventos.wsreservas.infrastructure.persistence;

import es.upsa.tfg.gestioneventos.domain.entities.EstadoReserva;
import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.exceptions.ReservaNotFoundException;
import es.upsa.tfg.gestioneventos.domain.exceptions.SQLEventosAppException;
import es.upsa.tfg.gestioneventos.wsreservas.adapters.output.persistence.Dao;
import es.upsa.tfg.gestioneventos.wsreservas.domain.exception.FieldRequiredSQLException;
import es.upsa.tfg.gestioneventos.wsreservas.domain.exception.InvalidCantidadSQLException;
import es.upsa.tfg.gestioneventos.wsreservas.domain.exception.InvalidEstadoSQLException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DaoImpl implements Dao
{
    @Inject
    DataSource dataSource;

    @Override
    public List<Reserva> findReservas() throws EventosAppException {
        final String SQL = """
                             SELECT r.id_reserva, r.id_evento, r.nombre_cliente, r.fecha_reserva, r.cantidad_entradas, r.precio_total, r.estado
                             FROM reservas r
                            """;
        List<Reserva> reservas = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {

            while (resultSet.next()) {
                Reserva reserva = toReserva(resultSet);
                reservas.add(reserva);
            }
            return reservas;

        } catch (SQLException sqlException) {
            throw toReservasAppException(sqlException);
        }
    }

    @Override
    public Optional<Reserva> findReservaById(String id) throws EventosAppException
    {
        final String SQL = """
                             SELECT r.id_reserva, r.id_evento, r.nombre_cliente, r.fecha_reserva, r.cantidad_entradas, r.precio_total, r.estado
                             FROM reservas r
                             WHERE r.id_reserva = ?
                           """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)
        ) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(toReserva(resultSet)) : Optional.empty();
            }

        } catch (SQLException sqlException) {
            throw toReservasAppException(sqlException);
        }
    }

    @Override
    public Reserva insertReserva(Reserva reserva) throws EventosAppException {
        final String SQL = """
                           INSERT INTO reservas (id_reserva,              id_evento, nombre_cliente, fecha_reserva, cantidad_entradas, precio_total, estado)
                                         VALUES (nextval('seq_reservas'), ?,         ?,              ?,             ?,                 ?,            ?)
                           """;
        final String[] fieldsReturned = {"id_reserva"};
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL, fieldsReturned)
        )
        {
            preparedStatement.setString(1, reserva.getId_evento());
            preparedStatement.setString(2, reserva.getNombre_cliente());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(reserva.getFecha_reserva()));
            preparedStatement.setInt(4, reserva.getCantidad_entradas());
            preparedStatement.setDouble(5, reserva.getPrecio_total());
            preparedStatement.setString(6, reserva.getEstadoReserva().name());

            preparedStatement.executeUpdate();

            try ( ResultSet resultSet = preparedStatement.getGeneratedKeys() )
            {
                resultSet.next();
                return reserva.withId_reserva( resultSet.getString(1) );
            }
        } catch (SQLException sqlException)
        {
            throw toReservasAppException(sqlException);
        }
    }

    @Override
    public boolean actualizarEstadoReserva(String idReserva, String nuevoEstado) throws EventosAppException
    {
        final String SQL = """
                             UPDATE reservas
                             SET estado = ?
                             WHERE id_reserva = ?
                           """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL))
        {
            preparedStatement.setString(1, nuevoEstado);
            preparedStatement.setString(2, idReserva);

            int count = preparedStatement.executeUpdate();
            return count > 0;

        } catch (SQLException sqlException) {
            throw toReservasAppException(sqlException);
        }
    }




    private Reserva toReserva(ResultSet resultSet) throws SQLException {
        return Reserva.builder()
                .withId_reserva(resultSet.getString("id_reserva"))
                .withId_evento(resultSet.getString("id_evento"))
                .withNombre_cliente(resultSet.getString("nombre_cliente"))
                .withFecha_reserva(resultSet.getTimestamp("fecha_reserva").toLocalDateTime())
                .withCantidad_entradas(resultSet.getInt("cantidad_entradas"))
                .withPrecio_total(resultSet.getDouble("precio_total"))
                .withEstadoReserva(EstadoReserva.valueOf(resultSet.getString("estado")))
                .build();
    }

    private EventosAppException toReservasAppException(SQLException sqlException) {
        String message = sqlException.getMessage();

        if (message.contains("NN_RESERVAS_EVENTO")) return new FieldRequiredSQLException("id_evento");
        else if (message.contains("NN_RESERVAS_NOMBRE_CLIENTE")) return new FieldRequiredSQLException("nombre_cliente");
        else if (message.contains("NN_RESERVAS_FECHA")) return new FieldRequiredSQLException("fecha_reserva");
        else if (message.contains("NN_RESERVAS_CANTIDAD")) return new FieldRequiredSQLException("cantidad_entradas");
        else if (message.contains("NN_RESERVAS_PRECIO")) return new FieldRequiredSQLException("precio_total");

        else if (message.contains("CK_RESERVAS_CANTIDAD")) return new InvalidCantidadSQLException("La cantidad de entradas debe ser mayor que 0");
        else if (message.contains("CK_RESERVAS_ESTADO")) return new InvalidEstadoSQLException();

        return new SQLEventosAppException(sqlException);
    }





}
