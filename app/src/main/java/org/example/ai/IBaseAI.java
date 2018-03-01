package org.example.ai;

import org.example.entities.AbstractPlayer;
import org.example.entities.Board;

/**
 * Interface to use for implementing different AI algorithms
 * Created by Jens Christian Rasch on 24-02-2018.
 */

public interface IBaseAI {

    Move ply(Board board, AbstractPlayer player);

}
