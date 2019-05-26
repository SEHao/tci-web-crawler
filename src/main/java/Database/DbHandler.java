package Database;

import Database.Interfaces.IDbHandler;
import Database.Models.Action;

public class DbHandler implements IDbHandler {

    private final String connectionString;

    public DbHandler(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * Writes an action into the database
     * @param action The action that has to be stored
     * @return Returns true if the action is tored successfuly.
     * Returns false if the operation failed.
     */
    @Override
    public Boolean WriteAction(Action action) {
        return null;
    }

    /**
     * Returns the last action that was stored in the database.
     * @return Returns the last action stored in the database.
     * Returns null if the databse is empty or the connection failed.
     */
    @Override
    public Action ReadLastAction() {
        return null;
    }
}
