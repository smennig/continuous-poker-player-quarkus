package org.continuouspoker.player.logic;

import java.util.List;

import org.continuouspoker.player.model.Bet;
import org.continuouspoker.player.model.Card;
import org.continuouspoker.player.model.Table;

public class Strategy {

   public Bet decide(final Table table) {
      System.out.println(table);

      var cards = getCards(table);
      System.out.println(cards);

      return new Bet().bet(table.getMinimumBet());
   }

   private  List<Card> getCards(final Table table) {
      return table.getPlayers()
              .stream()
              .filter(player1 -> player1.getName().equals("die-planlosen-bluffer"))
              .map(player1 -> player1.getCards())
              .findFirst()
              .get();
   }

}
