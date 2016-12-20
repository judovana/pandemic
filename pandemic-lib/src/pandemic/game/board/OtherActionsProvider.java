package pandemic.game.board;

import pandemic.game.board.parts.Deck;
import pandemic.game.roles.Roles;

/**
 *
 * @author jvanek
 */
public interface OtherActionsProvider {
    
    public void provide(Roles r, Deck d);
}
