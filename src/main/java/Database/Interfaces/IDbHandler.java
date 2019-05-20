package Database.Interfaces;
import Database.Models.Action;

public interface IDbHandler {

    Boolean WriteAction(Action action);

    Action ReadLastAction();
}
