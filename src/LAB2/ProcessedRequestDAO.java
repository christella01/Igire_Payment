package LAB2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProcessedRequestDAO {

    // Connection to the PostgreSQL database.
    private Connection connection;

    // Receive the connection from Main.java.
    public ProcessedRequestDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Save a new processed reference ID.
    // Return true if new, or false if it already exists.
    public boolean addProcessedRequest(String referenceId)
            throws SQLException {

        validateReferenceId(referenceId);

        String sql = "INSERT INTO processed_requests (reference_id) "
                + "VALUES (?) ON CONFLICT (reference_id) DO NOTHING";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, referenceId.trim());

            // A new row means the reference was registered.
            // Zero rows means the reference already existed.
            return statement.executeUpdate() == 1;
        }
    }

    // READ: Check whether a reference ID was already processed.
    public boolean hasBeenProcessed(String referenceId)
            throws SQLException {

        validateReferenceId(referenceId);

        String sql = "SELECT id FROM processed_requests "
                + "WHERE reference_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, referenceId.trim());

            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    // READ: Get all processed reference IDs.
    public List<String> getAllReferenceIds() throws SQLException {

        List<String> referenceIds = new ArrayList<>();

        String sql = "SELECT reference_id "
                + "FROM processed_requests ORDER BY id";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                referenceIds.add(result.getString("reference_id"));
            }
        }

        return referenceIds;
    }

    // UPDATE: Update the processing time for a reference.
    // Intended for authorized corrections, not normal payments.
    public boolean updateProcessedAt(int id, LocalDateTime processedAt)
            throws SQLException {

        if (processedAt == null) {
            throw new IllegalArgumentException(
                    "Processing time is required.");
        }

        String sql = "UPDATE processed_requests "
                + "SET processed_at = ? WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setTimestamp(1, Timestamp.valueOf(processedAt));
            statement.setInt(2, id);

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE: Remove a reference by its database ID.
    // Use only for authorized test cleanup or administration.
    // Never use this to make a completed payment reusable.
    public boolean deleteProcessedRequest(int id) throws SQLException {

        String sql = "DELETE FROM processed_requests WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        }
    }

    // CUSTOM QUERY: Find when a reference was processed.
    public LocalDateTime getProcessedAt(String referenceId)
            throws SQLException {

        validateReferenceId(referenceId);

        String sql = "SELECT processed_at FROM processed_requests "
                + "WHERE reference_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, referenceId.trim());

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    Timestamp timestamp =
                            result.getTimestamp("processed_at");

                    if (timestamp != null) {
                        return timestamp.toLocalDateTime();
                    }
                }
            }
        }

        return null;
    }

    // Validate a reference ID before using it.
    private void validateReferenceId(String referenceId) {

        if (referenceId == null) {
            throw new IllegalArgumentException(
                    "Reference ID cannot be null.");
        }

        String cleanedReference = referenceId.trim();

        if (cleanedReference.isEmpty()
                || cleanedReference.length() > 100) {
            throw new IllegalArgumentException(
                    "Reference ID must contain 1 to 100 characters.");
        }
    }
}